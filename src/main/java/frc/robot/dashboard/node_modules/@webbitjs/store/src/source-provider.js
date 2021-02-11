import { 
  subscribe,
  subscribeAll,
  sourcesChanged,
  getRawSource,
  getSource,
  getSources,
  clearSources, 
  sourcesRemoved
} from './store/sources';

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
    this.settings = {
      ...this.constructor.settingsDefaults,
      ...settings
    };
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
    }
    else {
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
          type: 'removal',
        }
      };
    }
    else {
      this._sourceUpdates[key].last = {
        type: 'removal',
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
    }
    // send first updates then last
    const firstUpdates = {};
    const lastUpdates = {};

    for (let key in this._sourceUpdates) {
      const values = this._sourceUpdates[key];
      firstUpdates[key] = values.first;
      if ('last' in values)
        lastUpdates[key] = values.last;
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
    const changes = {};
    for (let key in updates) {
      if (updates[key].type === 'change') {
        changes[key] = updates[key].value;
      }
    }
    if (Object.keys(changes).length > 0) {
      sourcesChanged(this._providerName, changes);
    }
  }

  _sendRemovals(updates) {
    const removals = [];
    for (let key in updates) {
      if (updates[key].type === 'removal') {
        removals.push(key);
      }
    }

    if (removals.length > 0) {
      sourcesRemoved(this._providerName, removals);
    }
  }
}

export default SourceProvider;