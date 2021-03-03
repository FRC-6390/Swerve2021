/**
 @module @webbitjs/store
*/

const providerTypes = {};
const providers = {};
let defaultSourceProvider = null;
const sourceProviderListeners = [];
const defaultSourceProviderListeners = [];

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
export const addSourceProviderType = (constructor) => {
  
  const { typeName } = constructor;

  if (typeof typeName !== 'string') {
    throw new Error('A typeName for your source provider type must be set.');
  }

  if (hasSourceProviderType(typeName)) {
    throw new Error('A source provider type with the same name has already been added.');
  }

  if (constructor.__WEBBIT_CLASSNAME__ === 'SourceProvider') {
    providerTypes[typeName] = constructor;
  }
}

export const hasSourceProviderType = (typeName) => {
  return typeName in providerTypes;
}

export const addSourceProvider = (providerType, providerName, settings) => {

  settings = settings || {};
  
  if (typeof providerName !== 'string') {
    providerName = providerType;
  }

  if (!hasSourceProviderType(providerType)) {
    throw new Error(`A source provider type with that name hasn't been added.`);
  }

  if (hasSourceProvider(providerName)) {
    throw new Error('A source provider with that name has already been added.');
  }

  const SourceProvider = providerTypes[providerType];

  providers[providerName] = new SourceProvider(providerName, {
    ...SourceProvider.settingsDefaults,
    ...settings
  });

  sourceProviderListeners.forEach(listener => {
    listener(providerName);
  }); 
  return providers[providerName];
};

export const sourceProviderAdded = (listener) => {

  if (typeof listener !== 'function') {
    throw new Error('listener is not a function');
  }

  sourceProviderListeners.push(listener);
};

export const removeSourceProvider = (providerName) => {
  if (!hasSourceProvider(providerName)) {
    return;
  }

  const provider = providers[providerName];
  provider._disconnect();
  delete providers[providerName];
}

export const getSourceProvider = (providerName) => {
  return providers[providerName];
};

export const getSourceProviderTypeNames = () => {
  return Object.keys(providerTypes);
};

export const getSourceProviderNames = () => {
  return Object.keys(providers);
};

export const hasSourceProvider = (providerName) => {
  return providerName in providers;
};

export const setDefaultSourceProvider = (providerName) => {
  defaultSourceProvider = providerName;

  defaultSourceProviderListeners.forEach(listener => {
    listener(defaultSourceProvider);
  }); 
};

export const getDefaultSourceProvider = () => {
  return defaultSourceProvider;
};

export const defaultSourceProviderSet = (listener) => {
  if (typeof listener !== 'function') {
    throw new Error('listener is not a function');
  }

  defaultSourceProviderListeners.push(listener);
};

export { 
  getRawSource, 
  getRawSources, 
  getSources, 
  getSource, 
  subscribe, 
  subscribeAll
} from './sources';