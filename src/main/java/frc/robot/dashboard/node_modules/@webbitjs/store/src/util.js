import camelCase from './camel-case';

export const normalizeKey = (key) => {
  return key
    .split('/')
    .map(keyPart => camelCase(keyPart))
    .join('/');
};