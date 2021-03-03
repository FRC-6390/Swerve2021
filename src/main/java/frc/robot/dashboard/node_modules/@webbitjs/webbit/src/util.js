

/**
 * Turns a camelCase string to kebab-case
 * 
 * https://gist.github.com/nblackburn/875e6ff75bc8ce171c758bf75f304707#gistcomment-2993938
 *
 * @param {string} string 
 */
export const camelToKebab = (string) => {
  return string
    .replace(/([a-z0-9])([A-Z])/g, '$1-$2')
    .replace(/([A-Z])([A-Z])(?=[a-z])/g, '$1-$2')
    .toLowerCase();
};