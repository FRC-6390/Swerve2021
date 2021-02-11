import { LitElement } from 'lit-element';
import isPlainObject from './isplainobject';
import ResizeObserver from 'resize-observer-polyfill';
import { FlowLayout } from './layouts';

import { 
  hasSourceProvider,
  getSourceProvider,
  sourceProviderAdded,
  getDefaultSourceProvider,
  defaultSourceProviderSet
} from '@webbitjs/store';

function isSourceObject(value) {
  return (
    value instanceof Object
    && value !== null
    && value.constructor.__WEBBIT_CLASSNAME__ === 'Source'
  );
}

export default class Webbit extends LitElement {

  static get __WEBBIT_CLASSNAME__() {
    return 'Webbit';
  }

  constructor() {
    super();

    this.defaultProps = {};
    this.hasUpdatedAtleastOnce = new Promise(async resolve => {
      await this.updateComplete;
      resolve();
    });

    for (let name in this.constructor.properties) {
      const property = this.constructor.properties[name];
      if (['sourceProvider', 'sourceKey', 'webbitId', 'fromProperties'].includes(name)) {
        continue;
      }

      const { type, attribute, reflect, structure } = property;

      if (attribute === false || !reflect) {
        continue;
      }

      Object.defineProperty(this, name, {
        get() {
          const getter = this.constructor.properties[name].get;
          if (typeof getter === 'function') {
            return getter.bind(this)();
          }
          return this[`_${name}`];
        },
        set(value) {
          const setter = this.constructor.properties[name].set;
          const sourceProvider = getSourceProvider(this.sourceProvider);

          if (this.isClone) {
            if (isPlainObject(value) && (value.__fromSource__ || value.__fromDefault__)) {
              return;
            }
            const oldValue = this[`_${name}`];
            this[`_${name}`] = typeof setter === 'function' 
              ? setter.bind(this)(value)
              : value;
            this.requestUpdate(name, oldValue);
            this._dispatchPropertyChange(name, oldValue, value);
            return;
          }

          if (isPlainObject(value) && (value.__fromSource__ || value.__fromDefault__)) {
            const oldValue = this[`_${name}`];
            this[`_${name}`] = typeof setter === 'function' 
              ? setter.bind(this)(value.__value__)
              : value.__value__;
            this.requestUpdate(name, oldValue);
            this._dispatchPropertyChange(name, oldValue, value.__value__);
            return;
          } else if (typeof this.sourceKey === 'string' && sourceProvider) {
            const source = sourceProvider.getRawSource(this.sourceKey);
            if (source) {
              const propSource = source.__sources__[name];

              if (typeof propSource === 'undefined') {
                if (this.constructor.properties[name].primary && source.__fromProvider__) {
                  sourceProvider.userUpdate(this.sourceKey, value);
                  return;
                }
              } else if (propSource.__fromProvider__) {
                sourceProvider.userUpdate(propSource.__key__, value);
                return;
              }
            }
          }

          const oldValue = this[`_${name}`];
          this[`_${name}`] = typeof setter === 'function' 
            ? setter.bind(this)(value)
            : value;
          this.requestUpdate(name, oldValue);
          this._dispatchPropertyChange(name, oldValue, value);
        }
      });
    }

    Object.defineProperty(this, 'sourceProvider', {
      get() {
        return this._sourceProvider || getDefaultSourceProvider();
      },      
      async set(value) {
        const oldValue = this._sourceProvider;
        this._sourceProvider = value;
        await this.requestUpdate('sourceProvider', oldValue);
        this._dispatchSourceProviderChange();

        if (hasSourceProvider(value)) { 
          this._subscribeToSource();
          this.setSourcesFromProperties();
        }
      }
    });

    Object.defineProperty(this, 'fromProperties', {
      get() {
        return this._fromProperties;
      },      
      async set(value) {
        const oldValue = this._fromProperties;
        this._fromProperties = value;
        await this.requestUpdate('fromProperties', oldValue);
        this.setSourcesFromProperties();
      }
    });

    Object.defineProperty(this, 'sourceKey', {
      get() {
        return this._sourceKey;
      },
      async set(value) {
        const oldValue = this._sourceKey;
        this._sourceKey = value;
        await this.requestUpdate('sourceKey', oldValue);
        this._dispatchSourceKeyChange();
        this._subscribeToSource();
        this.setSourcesFromProperties();
      }
    });

    Object.defineProperty(this, 'webbitId', {
      get() {
        return this._webbitId;
      },
      set(value) {
        const oldValue = this._webbitId;

        // If a node is currently being cloned, we want to
        // make sure the cloned node and all its children
        // maintain the same Webbit IDs.
        if (window.webbitRegistry.isCloning()) {
          this._webbitId = value;
          this.requestUpdate('webbitId', oldValue);
          return;
        }

        if (value === oldValue) {
          return;
        }

        const webbitId = window.webbitRegistry._generateWebbitId(this, value);

        if (!window.webbitRegistry.hasWebbit(oldValue)) {
          window.webbitRegistry._created(webbitId, this);
        } else {
          window.webbitRegistry._changedWebbitId(oldValue, webbitId, this);
        }

        this._webbitId = webbitId;
        this.requestUpdate('webbitId', oldValue);

        if (this.getAttribute('webbit-id') !== webbitId) {
          this.setAttribute('webbit-id', webbitId);
        }

        this._dispatchWebbitIdChange();
      }
    });

    this.sourceProvider = null;
    this.sourceKey = null;
    this.fromProperties = [];
    this._source = null;
    this._unsubscribeSource = null;
    this.webbitId = null;
    this.isClone = false;

    const resizeObserver = new ResizeObserver(() => {
      this.resized();
    });
    resizeObserver.observe(this);

    sourceProviderAdded(providerName => {
      if (providerName === this.sourceProvider) {
        this._subscribeToSource();
        this.setSourcesFromProperties();
      }
    });

    defaultSourceProviderSet(defaultSourceProvider => {
      if (!this._sourceProvider) {
        this.sourceProvider = defaultSourceProvider;
      }
      this._subscribeToSource();
      this.setSourcesFromProperties();
    });
  }

  async _subscribeToSource() {

    await this.hasUpdatedAtleastOnce;

    if (this._unsubscribeSource) {
      this._unsubscribeSource();
    }

    const sourceProvider = getSourceProvider(this.sourceProvider);

    if (this.sourceKey && sourceProvider) {
      this._unsubscribeSource = sourceProvider.subscribe(this.sourceKey, source => {
        this._setPropsFromSource(source);
        // Request update in case there are no props but we need an update anyway
        this.requestUpdate();
      });
      this._setPropsFromSource(sourceProvider.getSource(this.sourceKey));
      // Request update in case there are no props but we need an update anyway
      this.requestUpdate();
    }
  }

  _dispatchSourceKeyChange() {
    const event = new CustomEvent('source-key-change', {
      detail: {
        sourceKey: this.sourceKey
      },
      bubbles: true,
      composed: true
    });
    this.dispatchEvent(event);
  }

  _dispatchPropertyChange(property, oldValue, newValue) {
    const event = new CustomEvent('property-change', {
      detail: {
        property,
        oldValue,
        newValue
      },
      bubbles: true,
      composed: true
    });
    this.dispatchEvent(event);
  }

  _dispatchSourceProviderChange() {
    const event = new CustomEvent('source-provider-change', {
      detail: {
        sourceProvider: this.sourceProvider
      },
      bubbles: true,
      composed: true
    });
    this.dispatchEvent(event);
  }

  _dispatchWebbitIdChange() {
    const event = new CustomEvent('source-add', {
      detail: {
        webbitId: this.webbitId
      },
      bubbles: true,
      composed: true
    });
    this.dispatchEvent(event);
  }

  _setPropsFromSource(source) {

    this._source = source;

    for (let name in this.constructor.properties) {
      const property = this.constructor.properties[name];
      if (['sourceProvider', 'sourceKey', 'webbitId', 'fromProperties'].includes(name)) {
        continue;
      }

      const { type, attribute, reflect, structure, primary } = property;

      if (attribute === false || !reflect) {
        continue;
      }

      const propSource = source ? source[name] : undefined;

      if (typeof propSource === 'undefined') {
        if (primary && !isSourceObject(source) && typeof source !== 'undefined') {
          this[name] = {
            __fromSource__: true,
            __value__: source
          }
        // If we're no longer using the source to set the prop
        // change the prop to the default value
        } else {
          this.setPropToDefault(name);
        }
      } else {
        this[name] = {
          __fromSource__: true,
          __value__: propSource
        }
      }
    };
  }

  setSourcesFromProperties() {

    const sourceProvider = getSourceProvider(this.sourceProvider);

    if (!this.hasSource() || !sourceProvider) {
      return;
    }

    if (isSourceObject(this.getSource()) || typeof sourceProvider.getRawSource(this.sourceKey) === 'undefined') {
      const allProps = Object.keys(this.constructor.properties);
      const sourceProps = allProps.filter(prop => !['sourceProvider', 'sourceKey', 'fromProperties', 'webbitId'].includes(prop));
      this.fromProperties.forEach(fromProperty => {
        if (!sourceProps.includes(fromProperty)) {
          return;
        }

        const key = `${this.sourceKey}/${fromProperty}`;
        if (typeof sourceProvider.getRawSource(key) === 'undefined') {
          sourceProvider.userUpdate(key, this.defaultProps[fromProperty]);
        }
      });
    }
  }

  firstUpdated() {
    for (let name in this.constructor.properties) {
      if (['sourceProvider', 'sourceKey', 'webbitId', 'fromProperties'].includes(name)) {
        continue;
      }
      this.setDefaultValue(name, this[`_${name}`]);
    }
  }

  setDefaultValue(property, value) {
    this.defaultProps[property] = value;
  }

  setPropToDefault(property) {
    this[property] = {
      __fromDefault__: true,
      __value__: this.defaultProps[property]
    };
  }

  isPropertyConnectedToSource(name) {
    if (!this.hasSource()) {
      return false;
    }

    const source = this.getSource();

    if (!isSourceObject(source)) {
      const property = this.constructor.properties[name];
      return !!property && !!property.primary;
    }

    return name in source;
  }
  
  hasSource() {
    return this.sourceKey !== null && typeof this.sourceKey !== 'undefined';
  }

  getSource() {
    return this.hasSource() ? this._source : undefined;
  }

  resized() {}

  placeLayoutElement(element, context) {
    FlowLayout.placeLayoutElement(element, context);
  }
}
