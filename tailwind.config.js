/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.clj", "./content/**/*.md"],
  theme: {
    extend: {
      colors: {
      'gentle-black': '#372326',
      'sepia' : '#ffead8',
      'hl-sepia' : '#fad8bf',
      'leaf-green' : '#00704f',
      'fire-red' : '#8f2f30',
      },
      fontFamily: {
        'ibm-plex-sans': ['IBM Plex Sans', 'sans-serif']
      },
      textColor: 'light-grey',
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
            '--tw-prose-headings': theme('colors.fire-red'),
            '--tw-prose-links': theme('colors.leaf-green'),
            '--tw-prose-quote-borders': theme('colors.hl-sepia'),
            '--tw-prose-body': theme('colors.gentle-black'),
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
