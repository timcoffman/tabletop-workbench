/**
 * 
 * Access to view and update game states
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 */

(function(root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module.
    define(['ApiClient', 'model/PlaceTypesResource', 'model/RelationshipTypesResource'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    module.exports = factory(require('../ApiClient'), require('./PlaceTypesResource'), require('./RelationshipTypesResource'));
  } else {
    // Browser globals (root is window)
    if (!root.SwaggerJsClient) {
      root.SwaggerJsClient = {};
    }
    root.SwaggerJsClient.ModelPluginResource = factory(root.SwaggerJsClient.ApiClient, root.SwaggerJsClient.PlaceTypesResource, root.SwaggerJsClient.RelationshipTypesResource);
  }
}(this, function(ApiClient, PlaceTypesResource, RelationshipTypesResource) {
  'use strict';




  /**
   * The ModelPluginResource model module.
   * @module model/ModelPluginResource
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>ModelPluginResource</code>.
   * @alias module:model/ModelPluginResource
   * @class
   */
  var exports = function() {
    var _this = this;





  };

  /**
   * Constructs a <code>ModelPluginResource</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/ModelPluginResource} obj Optional instance to populate.
   * @return {module:model/ModelPluginResource} The populated <code>ModelPluginResource</code> instance.
   */
  exports.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new exports();

      if (data.hasOwnProperty('resource')) {
        obj['resource'] = ApiClient.convertToType(data['resource'], 'String');
      }
      if (data.hasOwnProperty('relationshipTypes')) {
        obj['relationshipTypes'] = RelationshipTypesResource.constructFromObject(data['relationshipTypes']);
      }
      if (data.hasOwnProperty('label')) {
        obj['label'] = ApiClient.convertToType(data['label'], 'String');
      }
      if (data.hasOwnProperty('placeTypes')) {
        obj['placeTypes'] = PlaceTypesResource.constructFromObject(data['placeTypes']);
      }
    }
    return obj;
  }

  /**
   * @member {String} resource
   */
  exports.prototype['resource'] = undefined;
  /**
   * @member {module:model/RelationshipTypesResource} relationshipTypes
   */
  exports.prototype['relationshipTypes'] = undefined;
  /**
   * @member {String} label
   */
  exports.prototype['label'] = undefined;
  /**
   * @member {module:model/PlaceTypesResource} placeTypes
   */
  exports.prototype['placeTypes'] = undefined;



  return exports;
}));


