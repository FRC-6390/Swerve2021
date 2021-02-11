(function (global, factory) {
  typeof exports === 'object' && typeof module !== 'undefined' ? factory(exports) :
  typeof define === 'function' && define.amd ? define(['exports'], factory) :
  (global = global || self, factory(global.webbitStore = {}));
}(this, (function (exports) { 'use strict';

  function _defineProperty(obj, key, value) {
    if (key in obj) {
      Object.defineProperty(obj, key, {
        value: value,
        enumerable: true,
        configurable: true,
        writable: true
      });
    } else {
      obj[key] = value;
    }

    return obj;
  }

  function ownKeys(object, enumerableOnly) {
    var keys = Object.keys(object);

    if (Object.getOwnPropertySymbols) {
      var symbols = Object.getOwnPropertySymbols(object);
      if (enumerableOnly) symbols = symbols.filter(function (sym) {
        return Object.getOwnPropertyDescriptor(object, sym).enumerable;
      });
      keys.push.apply(keys, symbols);
    }

    return keys;
  }

  function _objectSpread2(target) {
    for (var i = 1; i < arguments.length; i++) {
      var source = arguments[i] != null ? arguments[i] : {};

      if (i % 2) {
        ownKeys(Object(source), true).forEach(function (key) {
          _defineProperty(target, key, source[key]);
        });
      } else if (Object.getOwnPropertyDescriptors) {
        Object.defineProperties(target, Object.getOwnPropertyDescriptors(source));
      } else {
        ownKeys(Object(source)).forEach(function (key) {
          Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key));
        });
      }
    }

    return target;
  }

  /**
   * lodash (Custom Build) <https://lodash.com/>
   * Build: `lodash modularize exports="npm" -o ./`
   * Copyright jQuery Foundation and other contributors <https://jquery.org/>
   * Released under MIT license <https://lodash.com/license>
   * Based on Underscore.js 1.8.3 <http://underscorejs.org/LICENSE>
   * Copyright Jeremy Ashkenas, DocumentCloud and Investigative Reporters & Editors
   */

  /** Used as references for various `Number` constants. */
  var INFINITY = 1 / 0;
  /** `Object#toString` result references. */

  var symbolTag = '[object Symbol]';
  /** Used to match words composed of alphanumeric characters. */

  var reAsciiWord = /[^\x00-\x2f\x3a-\x40\x5b-\x60\x7b-\x7f]+/g;
  /** Used to match Latin Unicode letters (excluding mathematical operators). */

  var reLatin = /[\xc0-\xd6\xd8-\xf6\xf8-\xff\u0100-\u017f]/g;
  /** Used to compose unicode character classes. */

  var rsAstralRange = '\\ud800-\\udfff',
      rsComboMarksRange = '\\u0300-\\u036f\\ufe20-\\ufe23',
      rsComboSymbolsRange = '\\u20d0-\\u20f0',
      rsDingbatRange = '\\u2700-\\u27bf',
      rsLowerRange = 'a-z\\xdf-\\xf6\\xf8-\\xff',
      rsMathOpRange = '\\xac\\xb1\\xd7\\xf7',
      rsNonCharRange = '\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf',
      rsPunctuationRange = '\\u2000-\\u206f',
      rsSpaceRange = ' \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000',
      rsUpperRange = 'A-Z\\xc0-\\xd6\\xd8-\\xde',
      rsVarRange = '\\ufe0e\\ufe0f',
      rsBreakRange = rsMathOpRange + rsNonCharRange + rsPunctuationRange + rsSpaceRange;
  /** Used to compose unicode capture groups. */

  var rsApos = "['\u2019]",
      rsAstral = '[' + rsAstralRange + ']',
      rsBreak = '[' + rsBreakRange + ']',
      rsCombo = '[' + rsComboMarksRange + rsComboSymbolsRange + ']',
      rsDigits = '\\d+',
      rsDingbat = '[' + rsDingbatRange + ']',
      rsLower = '[' + rsLowerRange + ']',
      rsMisc = '[^' + rsAstralRange + rsBreakRange + rsDigits + rsDingbatRange + rsLowerRange + rsUpperRange + ']',
      rsFitz = '\\ud83c[\\udffb-\\udfff]',
      rsModifier = '(?:' + rsCombo + '|' + rsFitz + ')',
      rsNonAstral = '[^' + rsAstralRange + ']',
      rsRegional = '(?:\\ud83c[\\udde6-\\uddff]){2}',
      rsSurrPair = '[\\ud800-\\udbff][\\udc00-\\udfff]',
      rsUpper = '[' + rsUpperRange + ']',
      rsZWJ = '\\u200d';
  /** Used to compose unicode regexes. */

  var rsLowerMisc = '(?:' + rsLower + '|' + rsMisc + ')',
      rsUpperMisc = '(?:' + rsUpper + '|' + rsMisc + ')',
      rsOptLowerContr = '(?:' + rsApos + '(?:d|ll|m|re|s|t|ve))?',
      rsOptUpperContr = '(?:' + rsApos + '(?:D|LL|M|RE|S|T|VE))?',
      reOptMod = rsModifier + '?',
      rsOptVar = '[' + rsVarRange + ']?',
      rsOptJoin = '(?:' + rsZWJ + '(?:' + [rsNonAstral, rsRegional, rsSurrPair].join('|') + ')' + rsOptVar + reOptMod + ')*',
      rsSeq = rsOptVar + reOptMod + rsOptJoin,
      rsEmoji = '(?:' + [rsDingbat, rsRegional, rsSurrPair].join('|') + ')' + rsSeq,
      rsSymbol = '(?:' + [rsNonAstral + rsCombo + '?', rsCombo, rsRegional, rsSurrPair, rsAstral].join('|') + ')';
  /** Used to match apostrophes. */

  var reApos = RegExp(rsApos, 'g');
  /**
   * Used to match [combining diacritical marks](https://en.wikipedia.org/wiki/Combining_Diacritical_Marks) and
   * [combining diacritical marks for symbols](https://en.wikipedia.org/wiki/Combining_Diacritical_Marks_for_Symbols).
   */

  var reComboMark = RegExp(rsCombo, 'g');
  /** Used to match [string symbols](https://mathiasbynens.be/notes/javascript-unicode). */

  var reUnicode = RegExp(rsFitz + '(?=' + rsFitz + ')|' + rsSymbol + rsSeq, 'g');
  /** Used to match complex or compound words. */

  var reUnicodeWord = RegExp([rsUpper + '?' + rsLower + '+' + rsOptLowerContr + '(?=' + [rsBreak, rsUpper, '$'].join('|') + ')', rsUpperMisc + '+' + rsOptUpperContr + '(?=' + [rsBreak, rsUpper + rsLowerMisc, '$'].join('|') + ')', rsUpper + '?' + rsLowerMisc + '+' + rsOptLowerContr, rsUpper + '+' + rsOptUpperContr, rsDigits, rsEmoji].join('|'), 'g');
  /** Used to detect strings with [zero-width joiners or code points from the astral planes](http://eev.ee/blog/2015/09/12/dark-corners-of-unicode/). */

  var reHasUnicode = RegExp('[' + rsZWJ + rsAstralRange + rsComboMarksRange + rsComboSymbolsRange + rsVarRange + ']');
  /** Used to detect strings that need a more robust regexp to match words. */

  var reHasUnicodeWord = /[a-z][A-Z]|[A-Z]{2,}[a-z]|[0-9][a-zA-Z]|[a-zA-Z][0-9]|[^a-zA-Z0-9 ]/;
  /** Used to map Latin Unicode letters to basic Latin letters. */

  var deburredLetters = {
    // Latin-1 Supplement block.
    '\xc0': 'A',
    '\xc1': 'A',
    '\xc2': 'A',
    '\xc3': 'A',
    '\xc4': 'A',
    '\xc5': 'A',
    '\xe0': 'a',
    '\xe1': 'a',
    '\xe2': 'a',
    '\xe3': 'a',
    '\xe4': 'a',
    '\xe5': 'a',
    '\xc7': 'C',
    '\xe7': 'c',
    '\xd0': 'D',
    '\xf0': 'd',
    '\xc8': 'E',
    '\xc9': 'E',
    '\xca': 'E',
    '\xcb': 'E',
    '\xe8': 'e',
    '\xe9': 'e',
    '\xea': 'e',
    '\xeb': 'e',
    '\xcc': 'I',
    '\xcd': 'I',
    '\xce': 'I',
    '\xcf': 'I',
    '\xec': 'i',
    '\xed': 'i',
    '\xee': 'i',
    '\xef': 'i',
    '\xd1': 'N',
    '\xf1': 'n',
    '\xd2': 'O',
    '\xd3': 'O',
    '\xd4': 'O',
    '\xd5': 'O',
    '\xd6': 'O',
    '\xd8': 'O',
    '\xf2': 'o',
    '\xf3': 'o',
    '\xf4': 'o',
    '\xf5': 'o',
    '\xf6': 'o',
    '\xf8': 'o',
    '\xd9': 'U',
    '\xda': 'U',
    '\xdb': 'U',
    '\xdc': 'U',
    '\xf9': 'u',
    '\xfa': 'u',
    '\xfb': 'u',
    '\xfc': 'u',
    '\xdd': 'Y',
    '\xfd': 'y',
    '\xff': 'y',
    '\xc6': 'Ae',
    '\xe6': 'ae',
    '\xde': 'Th',
    '\xfe': 'th',
    '\xdf': 'ss',
    // Latin Extended-A block.
    '\u0100': 'A',
    '\u0102': 'A',
    '\u0104': 'A',
    '\u0101': 'a',
    '\u0103': 'a',
    '\u0105': 'a',
    '\u0106': 'C',
    '\u0108': 'C',
    '\u010a': 'C',
    '\u010c': 'C',
    '\u0107': 'c',
    '\u0109': 'c',
    '\u010b': 'c',
    '\u010d': 'c',
    '\u010e': 'D',
    '\u0110': 'D',
    '\u010f': 'd',
    '\u0111': 'd',
    '\u0112': 'E',
    '\u0114': 'E',
    '\u0116': 'E',
    '\u0118': 'E',
    '\u011a': 'E',
    '\u0113': 'e',
    '\u0115': 'e',
    '\u0117': 'e',
    '\u0119': 'e',
    '\u011b': 'e',
    '\u011c': 'G',
    '\u011e': 'G',
    '\u0120': 'G',
    '\u0122': 'G',
    '\u011d': 'g',
    '\u011f': 'g',
    '\u0121': 'g',
    '\u0123': 'g',
    '\u0124': 'H',
    '\u0126': 'H',
    '\u0125': 'h',
    '\u0127': 'h',
    '\u0128': 'I',
    '\u012a': 'I',
    '\u012c': 'I',
    '\u012e': 'I',
    '\u0130': 'I',
    '\u0129': 'i',
    '\u012b': 'i',
    '\u012d': 'i',
    '\u012f': 'i',
    '\u0131': 'i',
    '\u0134': 'J',
    '\u0135': 'j',
    '\u0136': 'K',
    '\u0137': 'k',
    '\u0138': 'k',
    '\u0139': 'L',
    '\u013b': 'L',
    '\u013d': 'L',
    '\u013f': 'L',
    '\u0141': 'L',
    '\u013a': 'l',
    '\u013c': 'l',
    '\u013e': 'l',
    '\u0140': 'l',
    '\u0142': 'l',
    '\u0143': 'N',
    '\u0145': 'N',
    '\u0147': 'N',
    '\u014a': 'N',
    '\u0144': 'n',
    '\u0146': 'n',
    '\u0148': 'n',
    '\u014b': 'n',
    '\u014c': 'O',
    '\u014e': 'O',
    '\u0150': 'O',
    '\u014d': 'o',
    '\u014f': 'o',
    '\u0151': 'o',
    '\u0154': 'R',
    '\u0156': 'R',
    '\u0158': 'R',
    '\u0155': 'r',
    '\u0157': 'r',
    '\u0159': 'r',
    '\u015a': 'S',
    '\u015c': 'S',
    '\u015e': 'S',
    '\u0160': 'S',
    '\u015b': 's',
    '\u015d': 's',
    '\u015f': 's',
    '\u0161': 's',
    '\u0162': 'T',
    '\u0164': 'T',
    '\u0166': 'T',
    '\u0163': 't',
    '\u0165': 't',
    '\u0167': 't',
    '\u0168': 'U',
    '\u016a': 'U',
    '\u016c': 'U',
    '\u016e': 'U',
    '\u0170': 'U',
    '\u0172': 'U',
    '\u0169': 'u',
    '\u016b': 'u',
    '\u016d': 'u',
    '\u016f': 'u',
    '\u0171': 'u',
    '\u0173': 'u',
    '\u0174': 'W',
    '\u0175': 'w',
    '\u0176': 'Y',
    '\u0177': 'y',
    '\u0178': 'Y',
    '\u0179': 'Z',
    '\u017b': 'Z',
    '\u017d': 'Z',
    '\u017a': 'z',
    '\u017c': 'z',
    '\u017e': 'z',
    '\u0132': 'IJ',
    '\u0133': 'ij',
    '\u0152': 'Oe',
    '\u0153': 'oe',
    '\u0149': "'n",
    '\u017f': 'ss'
  };
  /** Detect free variable `global` from Node.js. */

  var freeGlobal = typeof global == 'object' && global && global.Object === Object && global;
  /** Detect free variable `self`. */

  var freeSelf = typeof self == 'object' && self && self.Object === Object && self;
  /** Used as a reference to the global object. */

  var root = freeGlobal || freeSelf || Function('return this')();
  /**
   * A specialized version of `_.reduce` for arrays without support for
   * iteratee shorthands.
   *
   * @private
   * @param {Array} [array] The array to iterate over.
   * @param {Function} iteratee The function invoked per iteration.
   * @param {*} [accumulator] The initial value.
   * @param {boolean} [initAccum] Specify using the first element of `array` as
   *  the initial value.
   * @returns {*} Returns the accumulated value.
   */

  function arrayReduce(array, iteratee, accumulator, initAccum) {
    var index = -1,
        length = array ? array.length : 0;

    if (initAccum && length) {
      accumulator = array[++index];
    }

    while (++index < length) {
      accumulator = iteratee(accumulator, array[index], index, array);
    }

    return accumulator;
  }
  /**
   * Converts an ASCII `string` to an array.
   *
   * @private
   * @param {string} string The string to convert.
   * @returns {Array} Returns the converted array.
   */


  function asciiToArray(string) {
    return string.split('');
  }
  /**
   * Splits an ASCII `string` into an array of its words.
   *
   * @private
   * @param {string} The string to inspect.
   * @returns {Array} Returns the words of `string`.
   */


  function asciiWords(string) {
    return string.match(reAsciiWord) || [];
  }
  /**
   * The base implementation of `_.propertyOf` without support for deep paths.
   *
   * @private
   * @param {Object} object The object to query.
   * @returns {Function} Returns the new accessor function.
   */


  function basePropertyOf(object) {
    return function (key) {
      return object == null ? undefined : object[key];
    };
  }
  /**
   * Used by `_.deburr` to convert Latin-1 Supplement and Latin Extended-A
   * letters to basic Latin letters.
   *
   * @private
   * @param {string} letter The matched letter to deburr.
   * @returns {string} Returns the deburred letter.
   */


  var deburrLetter = basePropertyOf(deburredLetters);
  /**
   * Checks if `string` contains Unicode symbols.
   *
   * @private
   * @param {string} string The string to inspect.
   * @returns {boolean} Returns `true` if a symbol is found, else `false`.
   */

  function hasUnicode(string) {
    return reHasUnicode.test(string);
  }
  /**
   * Checks if `string` contains a word composed of Unicode symbols.
   *
   * @private
   * @param {string} string The string to inspect.
   * @returns {boolean} Returns `true` if a word is found, else `false`.
   */


  function hasUnicodeWord(string) {
    return reHasUnicodeWord.test(string);
  }
  /**
   * Converts `string` to an array.
   *
   * @private
   * @param {string} string The string to convert.
   * @returns {Array} Returns the converted array.
   */


  function stringToArray(string) {
    return hasUnicode(string) ? unicodeToArray(string) : asciiToArray(string);
  }
  /**
   * Converts a Unicode `string` to an array.
   *
   * @private
   * @param {string} string The string to convert.
   * @returns {Array} Returns the converted array.
   */


  function unicodeToArray(string) {
    return string.match(reUnicode) || [];
  }
  /**
   * Splits a Unicode `string` into an array of its words.
   *
   * @private
   * @param {string} The string to inspect.
   * @returns {Array} Returns the words of `string`.
   */


  function unicodeWords(string) {
    return string.match(reUnicodeWord) || [];
  }
  /** Used for built-in method references. */


  var objectProto = Object.prototype;
  /**
   * Used to resolve the
   * [`toStringTag`](http://ecma-international.org/ecma-262/7.0/#sec-object.prototype.tostring)
   * of values.
   */

  var objectToString = objectProto.toString;
  /** Built-in value references. */

  var Symbol = root.Symbol;
  /** Used to convert symbols to primitives and strings. */

  var symbolProto = Symbol ? Symbol.prototype : undefined,
      symbolToString = symbolProto ? symbolProto.toString : undefined;
  /**
   * The base implementation of `_.slice` without an iteratee call guard.
   *
   * @private
   * @param {Array} array The array to slice.
   * @param {number} [start=0] The start position.
   * @param {number} [end=array.length] The end position.
   * @returns {Array} Returns the slice of `array`.
   */

  function baseSlice(array, start, end) {
    var index = -1,
        length = array.length;

    if (start < 0) {
      start = -start > length ? 0 : length + start;
    }

    end = end > length ? length : end;

    if (end < 0) {
      end += length;
    }

    length = start > end ? 0 : end - start >>> 0;
    start >>>= 0;
    var result = Array(length);

    while (++index < length) {
      result[index] = array[index + start];
    }

    return result;
  }
  /**
   * The base implementation of `_.toString` which doesn't convert nullish
   * values to empty strings.
   *
   * @private
   * @param {*} value The value to process.
   * @returns {string} Returns the string.
   */


  function baseToString(value) {
    // Exit early for strings to avoid a performance hit in some environments.
    if (typeof value == 'string') {
      return value;
    }

    if (isSymbol(value)) {
      return symbolToString ? symbolToString.call(value) : '';
    }

    var result = value + '';
    return result == '0' && 1 / value == -INFINITY ? '-0' : result;
  }
  /**
   * Casts `array` to a slice if it's needed.
   *
   * @private
   * @param {Array} array The array to inspect.
   * @param {number} start The start position.
   * @param {number} [end=array.length] The end position.
   * @returns {Array} Returns the cast slice.
   */


  function castSlice(array, start, end) {
    var length = array.length;
    end = end === undefined ? length : end;
    return !start && end >= length ? array : baseSlice(array, start, end);
  }
  /**
   * Creates a function like `_.lowerFirst`.
   *
   * @private
   * @param {string} methodName The name of the `String` case method to use.
   * @returns {Function} Returns the new case function.
   */


  function createCaseFirst(methodName) {
    return function (string) {
      string = toString(string);
      var strSymbols = hasUnicode(string) ? stringToArray(string) : undefined;
      var chr = strSymbols ? strSymbols[0] : string.charAt(0);
      var trailing = strSymbols ? castSlice(strSymbols, 1).join('') : string.slice(1);
      return chr[methodName]() + trailing;
    };
  }
  /**
   * Creates a function like `_.camelCase`.
   *
   * @private
   * @param {Function} callback The function to combine each word.
   * @returns {Function} Returns the new compounder function.
   */


  function createCompounder(callback) {
    return function (string) {
      return arrayReduce(words(deburr(string).replace(reApos, '')), callback, '');
    };
  }
  /**
   * Checks if `value` is object-like. A value is object-like if it's not `null`
   * and has a `typeof` result of "object".
   *
   * @static
   * @memberOf _
   * @since 4.0.0
   * @category Lang
   * @param {*} value The value to check.
   * @returns {boolean} Returns `true` if `value` is object-like, else `false`.
   * @example
   *
   * _.isObjectLike({});
   * // => true
   *
   * _.isObjectLike([1, 2, 3]);
   * // => true
   *
   * _.isObjectLike(_.noop);
   * // => false
   *
   * _.isObjectLike(null);
   * // => false
   */


  function isObjectLike(value) {
    return !!value && typeof value == 'object';
  }
  /**
   * Checks if `value` is classified as a `Symbol` primitive or object.
   *
   * @static
   * @memberOf _
   * @since 4.0.0
   * @category Lang
   * @param {*} value The value to check.
   * @returns {boolean} Returns `true` if `value` is a symbol, else `false`.
   * @example
   *
   * _.isSymbol(Symbol.iterator);
   * // => true
   *
   * _.isSymbol('abc');
   * // => false
   */


  function isSymbol(value) {
    return typeof value == 'symbol' || isObjectLike(value) && objectToString.call(value) == symbolTag;
  }
  /**
   * Converts `value` to a string. An empty string is returned for `null`
   * and `undefined` values. The sign of `-0` is preserved.
   *
   * @static
   * @memberOf _
   * @since 4.0.0
   * @category Lang
   * @param {*} value The value to process.
   * @returns {string} Returns the string.
   * @example
   *
   * _.toString(null);
   * // => ''
   *
   * _.toString(-0);
   * // => '-0'
   *
   * _.toString([1, 2, 3]);
   * // => '1,2,3'
   */


  function toString(value) {
    return value == null ? '' : baseToString(value);
  }
  /**
   * Converts `string` to [camel case](https://en.wikipedia.org/wiki/CamelCase).
   *
   * @static
   * @memberOf _
   * @since 3.0.0
   * @category String
   * @param {string} [string=''] The string to convert.
   * @returns {string} Returns the camel cased string.
   * @example
   *
   * _.camelCase('Foo Bar');
   * // => 'fooBar'
   *
   * _.camelCase('--foo-bar--');
   * // => 'fooBar'
   *
   * _.camelCase('__FOO_BAR__');
   * // => 'fooBar'
   */


  var camelCase = createCompounder(function (result, word, index) {
    word = word.toLowerCase();
    return result + (index ? capitalize(word) : word);
  });
  /**
   * Converts the first character of `string` to upper case and the remaining
   * to lower case.
   *
   * @static
   * @memberOf _
   * @since 3.0.0
   * @category String
   * @param {string} [string=''] The string to capitalize.
   * @returns {string} Returns the capitalized string.
   * @example
   *
   * _.capitalize('FRED');
   * // => 'Fred'
   */

  function capitalize(string) {
    return upperFirst(toString(string).toLowerCase());
  }
  /**
   * Deburrs `string` by converting
   * [Latin-1 Supplement](https://en.wikipedia.org/wiki/Latin-1_Supplement_(Unicode_block)#Character_table)
   * and [Latin Extended-A](https://en.wikipedia.org/wiki/Latin_Extended-A)
   * letters to basic Latin letters and removing
   * [combining diacritical marks](https://en.wikipedia.org/wiki/Combining_Diacritical_Marks).
   *
   * @static
   * @memberOf _
   * @since 3.0.0
   * @category String
   * @param {string} [string=''] The string to deburr.
   * @returns {string} Returns the deburred string.
   * @example
   *
   * _.deburr('déjà vu');
   * // => 'deja vu'
   */


  function deburr(string) {
    string = toString(string);
    return string && string.replace(reLatin, deburrLetter).replace(reComboMark, '');
  }
  /**
   * Converts the first character of `string` to upper case.
   *
   * @static
   * @memberOf _
   * @since 4.0.0
   * @category String
   * @param {string} [string=''] The string to convert.
   * @returns {string} Returns the converted string.
   * @example
   *
   * _.upperFirst('fred');
   * // => 'Fred'
   *
   * _.upperFirst('FRED');
   * // => 'FRED'
   */


  var upperFirst = createCaseFirst('toUpperCase');
  /**
   * Splits `string` into an array of its words.
   *
   * @static
   * @memberOf _
   * @since 3.0.0
   * @category String
   * @param {string} [string=''] The string to inspect.
   * @param {RegExp|string} [pattern] The pattern to match words.
   * @param- {Object} [guard] Enables use as an iteratee for methods like `_.map`.
   * @returns {Array} Returns the words of `string`.
   * @example
   *
   * _.words('fred, barney, & pebbles');
   * // => ['fred', 'barney', 'pebbles']
   *
   * _.words('fred, barney, & pebbles', /[^, ]+/g);
   * // => ['fred', 'barney', '&', 'pebbles']
   */

  function words(string, pattern, guard) {
    string = toString(string);
    pattern = guard ? undefined : pattern;

    if (pattern === undefined) {
      return hasUnicodeWord(string) ? unicodeWords(string) : asciiWords(string);
    }

    return string.match(pattern) || [];
  }

  var normalizeKey = key => {
    return key.split('/').map(keyPart => camelCase(keyPart)).join('/');
  };

  /**
   @module @webbitjs/store
  */
  var providerTypes = {};
  var providers = {};
  var defaultSourceProvider = null;
  var sourceProviderListeners = [];
  var defaultSourceProviderListeners = [];
  /**
   * Adds a provider type.
   * 
   * @function
   * @example
   * import { SourceProvider, addSourceProviderType } from "@webbitjs/store";
   * 
   * class MyProvider extends SourceProvider {
   *   // class body
   * }
   * 
   * addSourceProviderType(MyProvider);
   * 
   * @param {SourceProvider} constructor - The source provider class
   */

  var addSourceProviderType = constructor => {
    var {
      typeName
    } = constructor;

    if (typeof typeName !== 'string') {
      throw new Error('A typeName for your source provider type must be set.');
    }

    if (hasSourceProviderType(typeName)) {
      throw new Error('A source provider type with the same name has already been added.');
    }

    if (constructor.__WEBBIT_CLASSNAME__ === 'SourceProvider') {
      providerTypes[typeName] = constructor;
    }
  };
  var hasSourceProviderType = typeName => {
    return typeName in providerTypes;
  };
  var addSourceProvider = (providerType, providerName, settings) => {
    settings = settings || {};

    if (typeof providerName !== 'string') {
      providerName = providerType;
    }

    if (!hasSourceProviderType(providerType)) {
      throw new Error("A source provider type with that name hasn't been added.");
    }

    if (hasSourceProvider(providerName)) {
      throw new Error('A source provider with that name has already been added.');
    }

    var SourceProvider = providerTypes[providerType];
    providers[providerName] = new SourceProvider(providerName, _objectSpread2({}, SourceProvider.settingsDefaults, {}, settings));
    sourceProviderListeners.forEach(listener => {
      listener(providerName);
    });
    return providers[providerName];
  };
  var sourceProviderAdded = listener => {
    if (typeof listener !== 'function') {
      throw new Error('listener is not a function');
    }

    sourceProviderListeners.push(listener);
  };
  var removeSourceProvider = providerName => {
    if (!hasSourceProvider(providerName)) {
      return;
    }

    var provider = providers[providerName];

    provider._disconnect();

    delete providers[providerName];
  };
  var getSourceProvider = providerName => {
    return providers[providerName];
  };
  var getSourceProviderTypeNames = () => {
    return Object.keys(providerTypes);
  };
  var getSourceProviderNames = () => {
    return Object.keys(providers);
  };
  var hasSourceProvider = providerName => {
    return providerName in providers;
  };
  var setDefaultSourceProvider = providerName => {
    defaultSourceProvider = providerName;
    defaultSourceProviderListeners.forEach(listener => {
      listener(defaultSourceProvider);
    });
  };
  var getDefaultSourceProvider = () => {
    return defaultSourceProvider;
  };
  var defaultSourceProviderSet = listener => {
    if (typeof listener !== 'function') {
      throw new Error('listener is not a function');
    }

    defaultSourceProviderListeners.push(listener);
  };

  class Source {
    static get __WEBBIT_CLASSNAME__() {
      return 'Source';
    }

  }

  var rawSources = {};
  var sources = {};
  var sourceObjectRefs = {};
  var subscribers = {};
  var subscribersAll = {};
  var nextSubscriberId = 0;

  var createRawSource = () => {
    return {
      __normalizedKey__: undefined,
      __fromProvider__: false,
      __key__: undefined,
      __value__: undefined,
      __sources__: {}
    };
  };

  var createSource = () => {
    return {
      getterValues: {},
      setters: {},
      sources: {}
    };
  };

  var getSourceObject = (providerName, key) => {
    key = normalizeKey(key);

    if (typeof sourceObjectRefs[providerName] === 'undefined') {
      sourceObjectRefs[providerName] = {};
    }

    if (typeof sourceObjectRefs[providerName][key] === 'undefined') {
      sourceObjectRefs[providerName][key] = new Source();
    }

    return sourceObjectRefs[providerName][key];
  };

  var setSourceObjectProps = (providerName, key, rawSource) => {
    var value = getSourceObject(providerName, key);
    Object.getOwnPropertyNames(value).forEach(prop => {
      if (prop in rawSource.__sources__) {
        return;
      }

      delete value[prop];
    });

    var _loop = function _loop(_key) {
      var normalizedKey = normalizeKey(_key);

      if (normalizedKey in value) {
        return "continue";
      }

      var rawSubSource = rawSource.__sources__[_key];
      Object.defineProperty(value, normalizedKey, {
        configurable: true,

        set(value) {
          var providerSources = sources[providerName];

          if (typeof providerSources === 'undefined') {
            return;
          }

          var setter = providerSources.setters[rawSubSource.__normalizedKey__];

          if (typeof setter === 'undefined') {
            return;
          }

          setter(value);
        },

        get() {
          if (typeof sources[providerName] === 'undefined') {
            return undefined;
          }

          return sources[providerName].getterValues[rawSubSource.__normalizedKey__];
        }

      });
    };

    for (var _key in rawSource.__sources__) {
      var _ret = _loop(_key);

      if (_ret === "continue") continue;
    }
  };

  var notifySubscribers = (providerName, key) => {
    var keyParts = normalizeKey(key).split('/');

    if (providerName in subscribers) {
      keyParts.forEach((keyPart, index) => {
        var sourceKey = keyParts.slice(0, index + 1).join('/');

        for (var id in subscribers[providerName][sourceKey] || {}) {
          var subscriber = subscribers[providerName][sourceKey][id];
          var source = getSource(providerName, sourceKey);
          subscriber(source, sourceKey, normalizeKey(key));
        }
      });
    }

    if (providerName in subscribersAll) {
      for (var id in subscribersAll[providerName]) {
        var subscriber = subscribersAll[providerName][id];
        var source = getSource(providerName, key);
        subscriber(source, normalizeKey(key));
      }
    }
  };

  var notifySubscribersRemoved = (providerName, keys, keysFomProviders) => {
    if (providerName in subscribers) {
      for (var key of keys) {
        key = normalizeKey(key);

        for (var id in subscribers[providerName][key]) {
          var subscriber = subscribers[providerName][key][id];
          subscriber(undefined, key, key);
        }
      }
    }

    if (providerName in subscribersAll) {
      for (var _key2 of keysFomProviders || keys) {
        for (var _id in subscribersAll[providerName]) {
          var _subscriber = subscribersAll[providerName][_id];

          _subscriber(undefined, _key2);
        }
      }
    }
  };

  var isSourceType = value => {
    return value instanceof Object && value.constructor.__WEBBIT_CLASSNAME__ === 'Source';
  };

  var cleanSource = (providerName, rawSources, normalizedKeyParts) => {
    if (normalizedKeyParts.length === 0) {
      return false;
    }

    var keyPart = normalizedKeyParts[0];
    var rawSource = rawSources[keyPart];

    if (typeof rawSource === 'undefined') {
      return false;
    }

    if (normalizedKeyParts.length > 1) {
      cleanSource(providerName, rawSource.__sources__, normalizedKeyParts.slice(1));
    }

    if (Object.keys(rawSource.__sources__).length === 0 && !rawSource.__fromProvider__) {
      delete rawSources[keyPart];
      delete sources[providerName].sources[rawSource.__normalizedKey__];
      delete sources[providerName].getterValues[rawSource.__normalizedKey__];
      delete sources[providerName].setters[rawSource.__normalizedKey__];
      notifySubscribersRemoved(providerName, [rawSource.__normalizedKey__]);
    } else {
      var providerSources = sources[providerName];
      setSourceObjectProps(providerName, rawSource.__normalizedKey__, rawSource);

      if (Object.keys(rawSource.__sources__).length === 0) {
        providerSources.getterValues[rawSource.__normalizedKey__] = rawSource.__value__;
      }
    }

    return true;
  };

  var getRawSources = providerName => {
    return rawSources[providerName];
  };
  var getRawSource = (providerName, key) => {
    var sourcesRoot = getRawSources(providerName);

    if (typeof sourcesRoot === 'undefined') {
      return undefined;
    }

    if (typeof key !== 'string') {
      return sourcesRoot;
    }

    var keyParts = normalizeKey(key).split('/');
    var sources = sourcesRoot.__sources__;

    for (var index in keyParts) {
      var keyPart = keyParts[index];

      if (keyParts.length - 1 === parseInt(index)) {
        return keyPart in sources ? sources[keyPart] : undefined;
      }

      if (keyPart in sources) {
        sources = sources[keyPart].__sources__;
      } else {
        return undefined;
      }
    }

    return undefined;
  };
  var getSources = providerName => {
    if (providerName in sources) {
      return sources[providerName].sources;
    }

    return undefined;
  };
  var getSource = (providerName, key) => {
    var sources = getSources(providerName);

    if (sources) {
      return sources[normalizeKey(key)];
    }

    return undefined;
  };
  var subscribe = (providerName, key, callback, callImmediately) => {
    if (typeof callback !== 'function') {
      throw new Error('Callback is not a function');
    }

    var normalizedKey = normalizeKey(key);

    if (subscribers[providerName] === undefined) {
      subscribers[providerName] = {};
    }

    if (subscribers[providerName][normalizedKey] === undefined) {
      subscribers[providerName][normalizedKey] = {};
    }

    var id = nextSubscriberId;
    nextSubscriberId++;
    subscribers[providerName][normalizedKey][id] = callback;

    if (callImmediately) {
      var source = getSource(providerName, normalizedKey);

      if (source !== undefined) {
        callback(source, key, key);
      }
    }

    var unsubscribe = () => {
      delete subscribers[providerName][normalizedKey][id];
    };

    return unsubscribe;
  };
  var subscribeAll = (providerName, callback, callImmediately) => {
    if (typeof callback !== 'function') {
      throw new Error('Callback is not a function');
    }

    if (subscribersAll[providerName] === undefined) {
      subscribersAll[providerName] = {};
    }

    var id = nextSubscriberId;
    nextSubscriberId++;
    subscribersAll[providerName][id] = callback;

    if (callImmediately) {
      var _sources = getSources(providerName);

      Object.getOwnPropertyNames(_sources || {}).forEach(key => {
        var rawSource = getRawSource(providerName, key);

        if (rawSource.__fromProvider__) {
          var source = _sources[key];
          callback(source, key);
        }
      });
    }

    var unsubscribe = () => {
      delete subscribersAll[providerName][id];
    };

    return unsubscribe;
  };
  var clearSources = providerName => {
    var hasSources = providerName in rawSources;

    if (!hasSources) {
      return;
    }

    var sourceKeys = Object.getOwnPropertyNames(getSources(providerName) || {});
    var keysFomProviders = sourceKeys.filter(key => {
      return getRawSource(providerName, key).__fromProvider__;
    });

    var _loop2 = function _loop2(key) {
      var getterValue = sources[providerName].getterValues[key];

      if (isSourceType(getterValue)) {
        Object.getOwnPropertyNames(getterValue).forEach(prop => {
          delete getterValue[prop];
        });
      }
    };

    for (var key in sources[providerName].getterValues) {
      _loop2(key);
    }

    rawSources[providerName] = createRawSource();
    sources[providerName] = createSource();
    notifySubscribersRemoved(providerName, sourceKeys, keysFomProviders);
  };
  var sourcesRemoved = (providerName, sourceRemovals) => {
    if (typeof rawSources[providerName] === 'undefined') {
      return;
    }

    var sourcesRoot = rawSources[providerName];

    for (var key of sourceRemovals) {
      var normalizedKey = normalizeKey(key);
      var normalizedKeyParts = normalizedKey.split('/');
      var _rawSources = sourcesRoot.__sources__;

      for (var index in normalizedKeyParts) {
        var keyPart = normalizedKeyParts[index];
        var inSources = keyPart in _rawSources;

        if (!inSources) {
          break;
        }

        if (normalizedKeyParts.length - 1 === parseInt(index)) {
          _rawSources[keyPart].__fromProvider__ = false;
          _rawSources[keyPart].__value__ = undefined;
        }

        _rawSources = _rawSources[keyPart].__sources__;
      }

      cleanSource(providerName, sourcesRoot.__sources__, normalizedKeyParts);
    }
  };
  var sourcesChanged = (providerName, sourceChanges) => {
    if (typeof rawSources[providerName] === 'undefined') {
      rawSources[providerName] = createRawSource();
      sources[providerName] = createSource();
    }

    var sourcesRoot = rawSources[providerName];

    var _loop4 = function _loop4(key) {
      var value = sourceChanges[key];
      var keyParts = key.split('/');
      var normalizedKey = normalizeKey(key);
      var normalizedKeyParts = normalizedKey.split('/');
      var rawSources = sourcesRoot.__sources__;
      var prevRawSource = {};
      normalizedKeyParts.forEach((keyPart, index) => {
        var inSources = keyPart in rawSources;
        var sourceKey = keyParts.slice(0, index + 1).join('/');
        var providerSources = sources[providerName];
        var normalizedKeyPartsJoined = normalizedKeyParts.slice(0, index + 1).join('/');

        if (!inSources) {
          rawSources[keyPart] = {
            __fromProvider__: false,
            __normalizedKey__: normalizedKeyPartsJoined,
            __key__: sourceKey,
            __value__: undefined,
            __sources__: {}
          };
          providerSources.getterValues[normalizedKeyPartsJoined] = getSourceObject(providerName, sourceKey);

          providerSources.setters[normalizedKeyPartsJoined] = () => {};

          Object.defineProperty(providerSources.sources, normalizedKeyPartsJoined, {
            configurable: true,

            set(value) {
              var providerSources = sources[providerName];

              if (typeof providerSources === 'undefined') {
                return;
              }

              var setter = providerSources.setters[normalizedKeyPartsJoined];

              if (typeof setter === 'undefined') {
                return;
              }

              setter(value);
            },

            get() {
              if (typeof sources[providerName] === 'undefined') {
                return undefined;
              }

              return sources[providerName].getterValues[normalizedKeyPartsJoined];
            }

          });
        }

        if (normalizedKeyParts.length - 1 === index) {
          rawSources[keyPart].__fromProvider__ = true;
          rawSources[keyPart].__value__ = value;

          if (Object.keys(rawSources[keyPart].__sources__).length === 0) {
            providerSources.getterValues[normalizedKeyPartsJoined] = value;
          }

          var sourceProvider = getSourceProvider(providerName);

          providerSources.setters[normalizedKeyPartsJoined] = value => {
            sourceProvider.userUpdate(sourceKey, value);
          };
        }

        if (index !== 0) {
          if (!isSourceType(providerSources.getterValues[prevRawSource.__normalizedKey__])) {
            providerSources.getterValues[prevRawSource.__normalizedKey__] = getSourceObject(providerName, prevRawSource.__normalizedKey__);
          }

          setSourceObjectProps(providerName, prevRawSource.__normalizedKey__, prevRawSource);
        }

        prevRawSource = rawSources[keyPart];
        rawSources = rawSources[keyPart].__sources__;
      });
      notifySubscribers(providerName, key);
    };

    for (var key in sourceChanges) {
      _loop4(key);
    }
  };

  class SourceProvider {
    static get __WEBBIT_CLASSNAME__() {
      return 'SourceProvider';
    }

    static get typeName() {
      return null;
    }

    static get settingsDefaults() {
      return {};
    }
    /**
     * Parent class all source providers must inherit from. Each source provider
     * instance is responsible for maintaining its own state object in the store. 
     * 
     * @memberof module:@webbitjs/store
     * @abstract
     * @param {string} providerName - The name of the provider.
     */


    constructor(providerName, settings) {
      if (new.target === SourceProvider) {
        throw new TypeError("Cannot construct SourceProvider instances directly.");
      }

      if (typeof providerName !== 'string') {
        throw new TypeError("The providerName needs to be passed into super() from your provider's constructor.");
      }

      if (typeof settings === 'undefined') {
        throw new Error("settings must be passed into the super() from your provider's constructor.");
      }

      if (typeof this.constructor.typeName !== 'string') {
        throw new Error("A typeName string must be defined.");
      }

      this._providerName = providerName;
      this.settings = _objectSpread2({}, this.constructor.settingsDefaults, {}, settings);
      this._sourceUpdates = {};
      this._interval = setInterval(this._sendUpdates.bind(this), 100);
    }
    /** 
     * Updates the value of a source in the store. If the source doesn't
     * exist then it is added. Should only be called internally by the 
     * source provider.
     * 
     * @protected
     * @param {string} key - The source's key. This is a string separated
     * by '/'.
     * @param {*} value - The new value.
     */


    updateSource(key, value) {
      if (this._sourceUpdates[key] === undefined) {
        this._sourceUpdates[key] = {
          first: {
            type: 'change',
            value
          }
        };
      } else {
        this._sourceUpdates[key].last = {
          type: 'change',
          value
        };
      }
    }
    /**
     * Removes an existing source from the store. If the source
     * doesn't exist this does nothing. Should only be called 
     * internally by the source provider.
     * 
     * @protected
     * @param {string} key - The source's key. This is a string separated
     * by '/'.
     */


    removeSource(key) {
      if (this._sourceUpdates[key] === undefined) {
        this._sourceUpdates[key] = {
          first: {
            type: 'removal'
          }
        };
      } else {
        this._sourceUpdates[key].last = {
          type: 'removal'
        };
      }
    }
    /**
     * Subscribes to changes for a particular source and all that source's
     * children.
     * 
     * @param {string} key - The source's key. This is a string separated
     * by '/'.
     * @param {function} callback - A function that takes in the source's
     * value, key, and key of child source that changed.
     * @param {boolean} callImmediately - If true, the callback is called
     * immediately with the source's current value.
     */


    subscribe(key, callback, callImmediately) {
      return subscribe(this._providerName, key, callback, callImmediately);
    }
    /**
     * Subscribes to all source changes.
     * 
     * @param {function} callback - A function that takes in the source's
     * value, key, and key of child source that changed.
     * @param {boolean} callImmediately - If true, the callback is called
     * immediately with the source's current value.
     */


    subscribeAll(callback, callImmediately) {
      return subscribeAll(this._providerName, callback, callImmediately);
    }
    /**
     * Gets a source's value.
     * 
     * @param {string} key - The source's key. This is a string separated
     * by '/'.
     */


    getSource(key) {
      return getSource(this._providerName, key);
    }

    getRawSource(key) {
      return getRawSource(this._providerName, key);
    }
    /**
     * Gets all sources
     */


    getSources() {
      return getSources(this._providerName);
    }
    /**
     * Removes all sources in the store for this provider. Should only be
     * called internally by the source provider.
     * 
     * @protected 
     * @param {function} callback - An optional callback. Called when sources
     * have been cleared.
     */


    clearSources(callback) {
      // send updates now to prevent them from being incorrectly sent after
      // sources were cleared.
      this._sendUpdates(() => {
        clearSources(this._providerName);

        if (typeof callback === 'function') {
          callback();
        }
      });
    }
    /**
     * Called when a source's value is modified by the user. This method
     * should be overridden by the child class to handle these updates.
     * This method should not be called directly.
     * 
     * @protected
     * @param {string} key - The source's key. This is a string separated
     * by '/'.
     * @param {*} value - The source's updated value.
     */


    userUpdate(key, value) {}
    /**
     * Helper function to get the type of a variable represented
     * by a string.
     * 
     * @param {*} value
     * @returns {string} - The value's type.
     */


    getType(value) {
      if (typeof value === 'string') {
        return 'string';
      } else if (typeof value === 'number') {
        return 'number';
      } else if (typeof value === 'boolean') {
        return 'boolean';
      } else if (value instanceof Array) {
        return 'Array';
      } else if (value === null) {
        return 'null';
      }

      return null;
    }

    _disconnect() {
      clearTimeout(this._interval);
    }

    _sendUpdates(callback) {
      if (Object.keys(this._sourceUpdates).length === 0) {
        if (typeof callback === 'function') {
          callback();
        }

        return;
      } // send first updates then last


      var firstUpdates = {};
      var lastUpdates = {};

      for (var key in this._sourceUpdates) {
        var values = this._sourceUpdates[key];
        firstUpdates[key] = values.first;
        if ('last' in values) lastUpdates[key] = values.last;
      }

      this._sendChanges(firstUpdates);

      this._sendRemovals(firstUpdates);

      if (Object.keys(lastUpdates).length > 0) {
        setTimeout(() => {
          this._sendChanges(lastUpdates);

          this._sendRemovals(lastUpdates);

          this._sourceUpdates = {};

          if (typeof callback === 'function') {
            callback();
          }
        });
      } else {
        this._sourceUpdates = {};

        if (typeof callback === 'function') {
          callback();
        }
      }
    }

    _sendChanges(updates) {
      var changes = {};

      for (var key in updates) {
        if (updates[key].type === 'change') {
          changes[key] = updates[key].value;
        }
      }

      if (Object.keys(changes).length > 0) {
        sourcesChanged(this._providerName, changes);
      }
    }

    _sendRemovals(updates) {
      var removals = [];

      for (var key in updates) {
        if (updates[key].type === 'removal') {
          removals.push(key);
        }
      }

      if (removals.length > 0) {
        sourcesRemoved(this._providerName, removals);
      }
    }

  }

  var SourceProvider$1 = SourceProvider;

  exports.SourceProvider = SourceProvider$1;
  exports.addSourceProvider = addSourceProvider;
  exports.addSourceProviderType = addSourceProviderType;
  exports.defaultSourceProviderSet = defaultSourceProviderSet;
  exports.getDefaultSourceProvider = getDefaultSourceProvider;
  exports.getRawSource = getRawSource;
  exports.getRawSources = getRawSources;
  exports.getSource = getSource;
  exports.getSourceProvider = getSourceProvider;
  exports.getSourceProviderNames = getSourceProviderNames;
  exports.getSourceProviderTypeNames = getSourceProviderTypeNames;
  exports.getSources = getSources;
  exports.hasSourceProvider = hasSourceProvider;
  exports.hasSourceProviderType = hasSourceProviderType;
  exports.removeSourceProvider = removeSourceProvider;
  exports.setDefaultSourceProvider = setDefaultSourceProvider;
  exports.sourceProviderAdded = sourceProviderAdded;
  exports.subscribe = subscribe;
  exports.subscribeAll = subscribeAll;

  Object.defineProperty(exports, '__esModule', { value: true });

})));
