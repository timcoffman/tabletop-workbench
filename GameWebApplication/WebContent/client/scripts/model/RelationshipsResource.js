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
    define(['ApiClient', 'model/RelationshipResource'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    module.exports = factory(require('../ApiClient'), require('./RelationshipResource'));
  } else {
    // Browser globals (root is window)
    if (!root.SwaggerJsClient) {
      root.SwaggerJsClient = {};
    }
    root.SwaggerJsClient.RelationshipsResource = factory(root.SwaggerJsClient.ApiClient, root.SwaggerJsClient.RelationshipResource);
  }
}(this, function(ApiClient, RelationshipResource) {
  'use strict';




  /**
   * The RelationshipsResource model module.
   * @module model/RelationshipsResource
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>RelationshipsResource</code>.
   * @alias module:model/RelationshipsResource
   * @class
   */
  var exports = function() {
    var _this = this;


  };

  /**
   * Constructs a <code>RelationshipsResource</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/RelationshipsResource} obj Optional instance to populate.
   * @return {module:model/RelationshipsResource} The populated <code>RelationshipsResource</code> instance.
   */
  exports.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new exports();

      if (data.hasOwnProperty('relationships')) {
        obj['relationships'] = ApiClient.convertToType(data['relationships'], [RelationshipResource]);
      }
    }
    return obj;
  }

  /**
   * @member {Array.<module:model/RelationshipResource>} relationships
   */
  exports.prototype['relationships'] = undefined;



  return exports;
}));


