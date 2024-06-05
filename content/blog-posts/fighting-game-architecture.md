:page/title Fighting Game Architecture
:blog-post/tags [:blog :programming :gamedev :fgc]
:blog-post/author {:person/id avery}
:open-graph/title Fighting Game Architecture
:page/body

> In which the Girl recounts her infernal machinations.

# The Decisions
## Tangent: Prior Work
I previously implemented some similar ideas in a now-dead project called "witch game", which can probably be considered a sort of predecessor to this project that has informed the decisions made. 
## Engine
After some deliberation, I'll be using Godot for this project. Unreal and Unity were cut out for a lack of Linux support[^1], alongside some workflows that felt quite disruptive (I seriously still have to restart the entirety of Unreal to add a C++ class?). Love2D and fennel was considered, but a lack of solid rollback libraries in lua drew me away. Bevy was another contender, but I'm quite weary of using it in the wake of [posts on its drawbacks](https://loglog.games/blog/leaving-rust-gamedev/), which seem quite disruptive in a game that I'm not certain on the exact shape of yet. Additionally, Bevy seems to generally need more oven-time for serious projects (that UI situation is scary), but I do think coming back to the engine with more lessons and knowledge could produce a very good foundation for a fighting game. Trial and Common Lisp were considered, but I find the language's community to be (often understandably) difficult to interface with, and I am nothing if not a question-asker.

Godot has its own drawbacks: immaturity (albeit less severe than Bevy's) and performance (GDScript is quite a slow language, but it is getting faster all the time). I'm willing to endure these due to the niceties afforded by its smalltalk-esque environment and ease of use, as well as a pretty large amount of implementation flexibility. Proper open source ownership is always lovely -- I'm looking at you, Unity -- and as we'll see later, it also has some lovely community networking plugins that I will very much be needing.
### Why Not MUGEN? Castagne?
I don't have a satisfying answer for you. I am unsure if I'll end up scraping against the limitations of these platforms if I get experimental with story modes, accessibility features, etc, but I confess these are not good enough reasons.

[^1]: This is not because I think engines without Linux support are morally inferior, but because so many of my workflows are deeply tied to Linux at this point and I would be terribly sad to let them go because of an engine choice.
## Networking
Rollback is quite literally necessary here. If you are making a modern fighting game and do not include rollback, you will be laughed out of the room. People will throw tomatoes at you. I won't go over the details of rollback here, but there is an [excellent blog post](https://mas-bandwidth.com/choosing-the-right-network-model-for-your-multiplayer-game/) that will do so better than I ever could.

The long and short of it is that I plan on using Snopek Games' excellent [Rollback Netcode](https://gitlab.com/snopek-games/godot-rollback-netcode) plugin to implement my networking. Utilisation of GGRS, GGPO, and other non-engine-specific solutions has been considered, but Snopek's addon has a great deal of documentation and a lovely community (and developer!) behind it that make working in it a joy (er, insofar as network programming can be a joy.)
## States
For the purposes of this post, I'll define states as any distinct, exclusive action a character in a fighting game can be in. This means "grounded" and "aerial" are not states, but "Standing", "Air Hitstun", and "Light Shoryuken" are. Yes, this nomenclature is confusing. I like to stay consistent with [the conventions of the genre](https://glossary.infil.net/?t=Fuzzy).

The issue of encoding the states a character can be in in a fighting game is one I don't think I'll ever have a satisfying answer for. In my research, I seem to have found that there's a spectrum from data-driven to code-driven approaches, which I'll attempt to expound upon. On the data-driven end of the spectrum, we have systems like [French Bread](https://en.wikipedia.org/wiki/French-Bread)'s Hantei-Chan file format. Their engine reads a file format that contains information about exactly what a character will do each frame. This has a lot of benefits (especially for working with non-programmer designers!), but comes with the drawback that new features can require implementing an entirely new GUI pane for your graphical editor.
![Hyde's standing state being viewed in hanteichan.](/images/hyde_hanteichan.png)
*Taken from https://wiki.gbl.gg/w/User:Pixloen/FPANModding/HA6. Sorry for unpacking your files, Kamone.*

Somewhere in the middle, we have script-driven systems like Arc System Works':
![Jubei's 2B being viewed on boxdox.](/images/jubei_2b.png)
*Taken from http://boxdox-bb.dantarion.com/#/viewer/bbcf_200/jb/NmlAtk2B/23. Sorry for unpacking your files, Daisuke.*

Here, almost everything is implemented in a self-rolled scripting language. This allows for GUI editors and hand-writing, which is always a bonus for fast iteration and feature-adding without needing to rejig a GUI. The difficulty here comes from the upfront cost of implementing an entire scripting language, which is never to be understated. The ASW format seems to be an optimised representation compiled to from some other more legible form (the names for the commands here are reverse-engineer-assigned, as far as I can tell), so who knows what the in-house tooling looks like.

For this project, I'd like to use a combination of a data structure to represent things that are definitely data: hitboxes, hurtboxes, damage, proration; and GDScript code for things that are behaviours/logic: spawning a fireball, picking random numbers. All of these will be managed through a (hopefully!) simple state machine, which will just tick through a frame's data and script logic each processing step.
## Determinism and Ordering
### The Problem
Order of execution is critical in games. A lack of attention to it will lead to a litany of problems, the majority of which require hideous refactoring jobs. For example, let's take a look at a basic scene tree in Godot:
```
- Root
	- Player1
	- Player2
```
It's just two nodes childed to the root node. If both of these were to have the same code:
```gdscript
func _ready() -> void:
	position.x = 0

func _physics_process(delta: float) -> void:
	position.x += 30 # update x position
	print(x)
	print(check_collision()) # returns true if both players' x value is the same.
```
Then running this scene would result in printed output of:
```
P1: 30
P1: false
P2: 30
P2: true
```
Both players moved the same amount on the same frame, but only one of them registered the collision! This is because Player2 only starts moving *after Player1 has finished its tick function*. A problem like this is untenable in a genre based almost entirely around the notion of fair competition.
### The (Proposed) Solution
The aforementioned Snopek Rollback addon has a solution in the form of `_network_preprocess` and `_network_postprocess`, which run before and after `_network_process`, respectively. The problem with *this* solution is that we still lack fine-grained control over specific phases -- we've circled back to execution order problems, they're just going to show up later and be even worse to debug. To alleviate these problems, we can utilise a god-manager parent class that tells the players when to do things. This is obviously a horrible violation of everything you've ever learned about OOP, coupling, and game design; but I promise it works here. I promise. We can get away with these kinds of coupling because in a given fighting game, there is pretty much *no scenario* in which you will have your player entities trying to run in a context other than a match of the game.

The parent class would keep a list of every "stateful" entity in the playing field, then in its own `_network_process`, call functions that correspond to distinct phases of a given frame (input, action, collision, reaction) on all of those entities[^2]. Arguments can be made that a system without this coupling makes fanciness like *One Step From Eden*'s card previews much easier, but a distinct lightweight single-player preview node that still calls all those functions on a single childed player node and handles some state loading would not be terribly scope-widening.

[^2]: I got this from rcmagic's extremely enlightening [Zig Fighting Game](https://github.com/rcmagic/ZigFightingGame/blob/98b84bb20998969fa0e1a4ad7ad3ea91ad634021/src/game_simulation.zig#L76) repository.
# The Phases
I'd like to break up early development into distinct phases so that I can work towards specific goals, tackling challenges and requirements as they come up. All of these phases will be implemented with rollback, so I hopefully won't run into terrible determinism issues that require full rewrites, or have to spend half a year figuring out exactly which parts of my state I need to serialize. I expect to need to break these up into smaller subphases as I go on, but this basic outline should suffice for direction.
## Phase 0.1: Walking Cubes
The simplest, barest MVP of two cubes walking around together in a scene. They should be using a state machine, but not any of the data-logic separation stuff yet. They should be capable of walking and jumping, and have a facing direction that gets updated like in fighting games. This will require implementing the input, physics, and action phases.
## Phase 0.2: Data-Oriented Cubes
The cubes should be capable of having hitboxes and hurtboxes. These won't read from a saved resource for now, and will instead have their contents defined at runtime in ugly, direct calls in their initialisation functions. It should be possible to detect if one cube hits another, but knockback and such aren't necessary to implement yet -- they should just have some indication that a hitbox hit a hurtbox. This will require implementing the collision and reaction phases.
## And Onwards
I won't belabour you with the remaining million-and-a-half phases I envision -- hopefully this brief look is enough to help you understand how I plan on tackling the problems of complexity and scope in development. I anticipate the first push of a solid foundation being the hardest thing to do, so I'd like to keep these phases nice and decomposable to avoid a state of overwhelm.
# The Conclusion
There are several things I did not cover in this post. Some, like input handling, I just didn't think would be terribly interesting. Some (particularly story) I didn't mention in anticipation of being able to have the fun of making future announcements and reveals. Some, like art, I don't want to talk about right now. I'll figure it out later. Probably.

This post was partially for the sake of getting down ideas, but it was also partially for the sake of putting those ideas out for public review. This means I am extremely amicable to comments! Please ask questions! Point out holes/problems! I am pleading for guidance!