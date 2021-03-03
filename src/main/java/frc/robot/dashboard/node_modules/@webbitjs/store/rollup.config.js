import babel from 'rollup-plugin-babel';
import uglify from 'rollup-plugin-uglify-es';
import resolve from '@rollup/plugin-node-resolve';

export default [
  {
    input: './src/index.js',
    output: {
      file: './dist/webbit-store.js',
      format: 'umd',
      name: 'webbitStore'
    },
    plugins: [
      resolve(),
      babel()
    ]
  },
  {
    input: './src/index.js',
    output: {
      file: './dist/webbit-store.min.js',
      format: 'umd',
      name: 'webbitStore'
    },
    plugins: [
      resolve(),
      babel(),
      uglify()
    ]
  }
]