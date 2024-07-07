/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.clj", "./content/**/*.md"],
  theme: {
    extend: {
      colors: {
      'deep-black': '#000000',
      'gentle-black': '#372326',
      'gentle-white': '#ccc7c0',
      'sepia': '#ffead8',
      'off-white': '#d3c6aa',
      'hl-sepia' : '#fad8bf',
      'leaf-green' : '#83c092',
      'fire-red' : '#e67e6f',
      'pale-blue': '#7fbbb3'
      },
      fontFamily: {
        'ibm-plex-serif': ['IBM Plex Serif', 'font-serif'],
        'ibm-plex-sans': ['IBM Plex Sans', 'sans-serif'],
        'teranoptia': ['Teranoptia']
      },
      textColor: 'off-white',
      typography: theme => ({
        DEFAULT: {
          css: {
            maxWidth: '100%',
            "code::before": {
              content: "none",
            },
            "code::after": {
              content: "none",
            },
            a: {
              // color: theme('colors.pale-blue')
            },
            'a:hover': {
              // color: theme('colors.pale-blue')
            },
            '--tw-prose-bold': theme('colors.pale-blue'),
            '--tw-prose-code': theme('colors.pale-blue'),
            '--tw-prose-headings': theme('colors.fire-red'),
            '--tw-prose-links': theme('colors.leaf-green'),
            '--tw-prose-quotes': theme('colors.fire-red'),
            '--tw-prose-quote-borders': theme('colors.off-white'),
            '--tw-prose-body': theme('colors.off-white'),
          }
        },
        invert: {}
      })
    }
  },
  plugins: [
    require('@tailwindcss/typography')
  ]
}
