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
    define(['ApiClient', 'model/StageResource'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    module.exports = factory(require('../ApiClient'), require('./StageResource'));
  } else {
    // Browser globals (root is window)
    if (!root.SwaggerJsClient) {
      root.SwaggerJsClient = {};
    }
    root.SwaggerJsClient.StagesResource = factory(root.SwaggerJsClient.ApiClient, root.SwaggerJsClient.StageResource);
  }
}(this, function(ApiClient, StageResource) {
  'use strict';




  /**
   * The StagesResource model module.
   * @module model/StagesResource
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>StagesResource</code>.
   * @alias module:model/StagesResource
   * @class
   */
  var exports = function() {
    var _this = this;


  };

  /**
   * Constructs a <code>StagesResource</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/StagesResource} obj Optional instance to populate.
   * @return {module:model/StagesResource} The populated <code>StagesResource</code> instance.
   */
  exports.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new exports();

      if (data.hasOwnProperty('stages')) {
        obj['stages'] = ApiClient.convertToType(data['stages'], [StageResource]);
      }
    }
    return obj;
  }

  /**
   * @member {Array.<module:model/StageResource>} stages
   */
  exports.prototype['stages'] = undefined;



  return exports;
}));

