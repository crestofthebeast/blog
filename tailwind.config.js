/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.clj"],
  theme: {
    extend: {
      colors: {
      'beige': '#ebe4e1',
      'darkish-grey': '#484647',
      'dark-grey': '#323f46',
      'light-grey': '#c9cdd7',
      'pale-blue': '#639fd1',
      },
      fontFamily: {
        'ibm-plex-sans': ['IBM Plex Sans', 'sans-serif']
      },
      textColor: 'light-grey',
      typography: theme => ({
        DEFAULT: {
          css: {

            a: {
              color: theme('colors.pale-blue')
            },
            'a:hover': {
              color: theme('colors.pale-blue')
            },
            '--tw-prose-body': theme('colors.darkish-grey'),
            '--tw-prose-headings': theme('colors.darkish-grey'),
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
