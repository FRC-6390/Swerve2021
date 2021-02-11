import { normalizeKey } from '../util';
import { getSourceProvider } from './index';

class Source {
  static get __WEBBIT_CLASSNAME__() {
    return 'Source';
  }
}

const rawSources = {};
const sources = {};
const sourceObjectRefs = {};

const subscribers = {};
const subscribersAll = {};
let nextSubscriberId = 0;

const createRawSource = () => {
  return {
    __normalizedKey__: undefined,
    __fromProvider__: false,
    __key__: undefined,
    __value__: undefined,
    __sources__: {}
  };
};

const createSource = () => {
  return {
    getterValues: {},
    setters: {},
    sources: {}
  };
};

const getSourceObject = (providerName, key) => {

  key = normalizeKey(key);

  if (typeof sourceObjectRefs[providerName] === 'undefined') {
    sourceObjectRefs[providerName] = {};
  }

  if (typeof sourceObjectRefs[providerName][key] === 'undefined') {
    sourceObjectRefs[providerName][key] = new Source();
  }

  return sourceObjectRefs[providerName][key];
};

const setSourceObjectProps = (providerName, key, rawSource) => {
  const value = getSourceObject(providerName, key);

  Object.getOwnPropertyNames(value).forEach(prop => {
    if (prop in rawSource.__sources__) {
      return;
    }
    delete value[prop];
  });

  for (let key in rawSource.__sources__) {

    const normalizedKey = normalizeKey(key);

    if (normalizedKey in value) {
      continue;
    }

    const rawSubSource = rawSource.__sources__[key];
    Object.defineProperty(value, normalizedKey, {
      configurable: true,
      set(value) {
        const providerSources = sources[providerName];
        
        if (typeof providerSources === 'undefined') {
          return;
        }

        const setter = providerSources.setters[rawSubSource.__normalizedKey__];

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
  }
};

const notifySubscribers = (providerName, key) => {
  const keyParts = normalizeKey(key).split('/');
  if (providerName in subscribers) {
    keyParts.forEach((keyPart, index) => {
      const sourceKey = keyParts.slice(0, index + 1).join('/');
      for (let id in subscribers[providerName][sourceKey] || {}) {
        const subscriber = subscribers[providerName][sourceKey][id];
        const source = getSource(providerName, sourceKey);
        subscriber(source, sourceKey, normalizeKey(key));
      }
    });
  }

  if (providerName in subscribersAll) {
    for (let id in subscribersAll[providerName]) {
      const subscriber = subscribersAll[providerName][id];
      const source = getSource(providerName, key);
      subscriber(source, normalizeKey(key));
    }
  }
};

const notifySubscribersRemoved = (providerName, keys, keysFomProviders) => {
  if (providerName in subscribers) {
    for (let key of keys) {
      key = normalizeKey(key);
      for (let id in subscribers[providerName][key]) {
        const subscriber = subscribers[providerName][key][id];
        subscriber(undefined, key, key);
      }
    }
  }

  if (providerName in subscribersAll) {
    for (let key of keysFomProviders || keys) {
      for (let id in subscribersAll[providerName]) {
        const subscriber = subscribersAll[providerName][id];
        subscriber(undefined, key);
      }
    }
  }
};

const isSourceType = (value) => {
  return value instanceof Object && value.constructor.__WEBBIT_CLASSNAME__ === 'Source';
};

const cleanSource = (providerName, rawSources, normalizedKeyParts) => {
  if (normalizedKeyParts.length === 0) {
    return false;
  }

  const keyPart = normalizedKeyParts[0];

  const rawSource = rawSources[keyPart];

  if (typeof rawSource === 'undefined') {
    return false;
  }

  if (normalizedKeyParts.length > 1) {
    cleanSource(providerName, rawSource.__sources__, normalizedKeyParts.slice(1));
  }

  if (
    Object.keys(rawSource.__sources__).length === 0 &&
    !rawSource.__fromProvider__
  ) {
    delete rawSources[keyPart];
    delete sources[providerName].sources[rawSource.__normalizedKey__];
    delete sources[providerName].getterValues[rawSource.__normalizedKey__];
    delete sources[providerName].setters[rawSource.__normalizedKey__];
    notifySubscribersRemoved(providerName, [rawSource.__normalizedKey__]);
  } else {
    const providerSources = sources[providerName];
    setSourceObjectProps(providerName, rawSource.__normalizedKey__, rawSource);
    if (Object.keys(rawSource.__sources__).length === 0) {
      providerSources.getterValues[rawSource.__normalizedKey__] = rawSource.__value__;
    }
  }

  return true;
};

export const getRawSources = (providerName) => {
  return rawSources[providerName];
};

export const getRawSource = (providerName, key) => {
  let sourcesRoot = getRawSources(providerName);

  if (typeof sourcesRoot === 'undefined') {
    return undefined;
  }

  if (typeof key !== 'string') {
    return sourcesRoot;
  }

  const keyParts = normalizeKey(key).split('/');

  let sources = sourcesRoot.__sources__;

  for (let index in keyParts) {
    const keyPart = keyParts[index];

    if (keyParts.length - 1 === parseInt(index)) {
      return (keyPart in sources) ? sources[keyPart] : undefined;
    }

    if (keyPart in sources) {
      sources = sources[keyPart].__sources__;
    } else {
      return undefined;
    }
  }

  return undefined;
}

export const getSources = (providerName) => {
  if (providerName in sources) {
    return sources[providerName].sources;
  }
  return undefined;
};

export const getSource = (providerName, key) => {
  const sources = getSources(providerName);
  if (sources) {
    return sources[normalizeKey(key)];
  }
  return undefined;
};

export const subscribe = (providerName, key, callback, callImmediately) => {
  if (typeof callback !== 'function') {
    throw new Error('Callback is not a function');
  }

  const normalizedKey = normalizeKey(key);

  if (subscribers[providerName] === undefined) {
    subscribers[providerName] = {};
  }

  if (subscribers[providerName][normalizedKey] === undefined) {
    subscribers[providerName][normalizedKey] = {};
  }

  const id = nextSubscriberId;
  nextSubscriberId++;
  subscribers[providerName][normalizedKey][id] = callback;

  if (callImmediately) {
    const source = getSource(providerName, normalizedKey);
    if (source !== undefined) {
      callback(source, key, key);
    }
  }

  const unsubscribe = () => {
    delete subscribers[providerName][normalizedKey][id];
  };

  return unsubscribe;
};

export const subscribeAll = (providerName, callback, callImmediately) => {
  if (typeof callback !== 'function') {
    throw new Error('Callback is not a function');
  }

  if (subscribersAll[providerName] === undefined) {
    subscribersAll[providerName] = {};
  }

  const id = nextSubscriberId;
  nextSubscriberId++;
  subscribersAll[providerName][id] = callback;

  if (callImmediately) {
    const sources = getSources(providerName);
    Object.getOwnPropertyNames(sources || {}).forEach(key => {
      const rawSource = getRawSource(providerName, key);
      if (rawSource.__fromProvider__) {
        const source = sources[key];
        callback(source, key);
      }
    });
  }

  const unsubscribe = () => {
    delete subscribersAll[providerName][id];
  };

  return unsubscribe;
};

export const clearSources = (providerName) => {

  const hasSources = providerName in rawSources;

  if (!hasSources) {
    return;
  }

  const sourceKeys = Object.getOwnPropertyNames(getSources(providerName) || {});
  const keysFomProviders = sourceKeys.filter(key => {
    return getRawSource(providerName, key).__fromProvider__;
  });

  for (let key in sources[providerName].getterValues) {
    const getterValue = sources[providerName].getterValues[key];
    if (isSourceType(getterValue)) {
      Object.getOwnPropertyNames(getterValue).forEach(prop => {
        delete getterValue[prop];
      });
    }
  }

  rawSources[providerName] = createRawSource();
  sources[providerName] = createSource();

  notifySubscribersRemoved(providerName, sourceKeys, keysFomProviders);
};

export const removeSources = (providerName) => {

  const hasSources = providerName in rawSources;

  if (!hasSources) {
    return;
  }

  const sourceKeys = Object.getOwnPropertyNames(getSources(providerName));
  const keysFomProviders = sourceKeys.filter(key => {
    return getRawSource(providerName, key).__fromProvider__;
  });

  for (let key in sources[providerName].getterValues) {
    const getterValue = sources[providerName].getterValues[key];
    if (isSourceType(getterValue)) {
      Object.getOwnPropertyNames(getterValue).forEach(prop => {
        delete getterValue[prop];
      });
    }
  }

  delete rawSources[providerName];
  delete sources[providerName];

  notifySubscribersRemoved(providerName, sourceKeys, keysFomProviders);
};

export const sourcesRemoved = (providerName, sourceRemovals) => {
  if (typeof rawSources[providerName] === 'undefined') {
    return;
  }

  const sourcesRoot = rawSources[providerName];

  for (let key of sourceRemovals) {
    const normalizedKey = normalizeKey(key);
    const normalizedKeyParts = normalizedKey.split('/');

    let rawSources = sourcesRoot.__sources__;

    for (let index in normalizedKeyParts) {

      const keyPart = normalizedKeyParts[index];
      const inSources = keyPart in rawSources;

      if (!inSources) {
        break;
      }

      if (normalizedKeyParts.length - 1 === parseInt(index)) {
        rawSources[keyPart].__fromProvider__ = false;
        rawSources[keyPart].__value__ = undefined;
      }

      rawSources = rawSources[keyPart].__sources__;
    }

    cleanSource(providerName, sourcesRoot.__sources__, normalizedKeyParts);
  }
};

export const sourcesChanged = (providerName, sourceChanges) => {

  if (typeof rawSources[providerName] === 'undefined') {
    rawSources[providerName] = createRawSource();
    sources[providerName] = createSource();
  }

  const sourcesRoot = rawSources[providerName];

  for (let key in sourceChanges) {

    const value = sourceChanges[key];

    const keyParts = key.split('/');
    const normalizedKey = normalizeKey(key);
    const normalizedKeyParts = normalizedKey.split('/');

    let rawSources = sourcesRoot.__sources__;
    let prevRawSource = {};

    normalizedKeyParts.forEach((keyPart, index) => {
      const inSources = keyPart in rawSources;
      const sourceKey = keyParts.slice(0, index + 1).join('/');
      const providerSources = sources[providerName];
      const normalizedKeyPartsJoined = normalizedKeyParts.slice(0, index + 1).join('/');

      if (!inSources) {
        rawSources[keyPart] = {
          __fromProvider__: false,
          __normalizedKey__: normalizedKeyPartsJoined,
          __key__: sourceKey,
          __value__: undefined,
          __sources__: {}
        }

        providerSources.getterValues[normalizedKeyPartsJoined] = getSourceObject(providerName, sourceKey);
        providerSources.setters[normalizedKeyPartsJoined] = () => {};
        Object.defineProperty(providerSources.sources, normalizedKeyPartsJoined, {
          configurable: true,
          set(value) {     
            const providerSources = sources[providerName];

            if (typeof providerSources === 'undefined') {
              return;
            }

            const setter = providerSources.setters[normalizedKeyPartsJoined];

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

        const sourceProvider = getSourceProvider(providerName);
        providerSources.setters[normalizedKeyPartsJoined] = (value) => {
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
  }
};