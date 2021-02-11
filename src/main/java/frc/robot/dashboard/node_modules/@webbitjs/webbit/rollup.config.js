import resolve from '@rollup/plugin-node-resolve';
import uglify from 'rollup-plugin-uglify-es';
import babel from 'rollup-plugin-babel';

export default [
  {
    input: './src/index.js',
    output: {
      file: './dist/webbit.js',
      format: 'umd',
      name: 'webbit',
      globals: {
        '@webbitjs/store': 'webbitStore'
      }
    },
    plugins: [
      babel(),
      resolve({
        only: [
          'lit-element', 
          'lit-html',
          'resize-observer-polyfill'
        ]
      })
    ]
  },
  {
    input: './src/index.js',
    output: {
      file: './dist/webbit.min.js',
      format: 'umd',
      name: 'webbit',
      globals: {
        '@webbitjs/store': 'webbitStore'
      }
    },
    plugins: [
      babel(),
      resolve({
        only: [
          'lit-element', 
          'lit-html',
          'resize-observer-polyfill'     
        ]
      }),
      uglify()
    ]
  }
]