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
    define(['ApiClient'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    module.exports = factory(require('../ApiClient'));
  } else {
    // Browser globals (root is window)
    if (!root.SwaggerJsClient) {
      root.SwaggerJsClient = {};
    }
    root.SwaggerJsClient.PartPatternResourceObject = factory(root.SwaggerJsClient.ApiClient);
  }
}(this, function(ApiClient) {
  'use strict';




  /**
   * The PartPatternResourceObject model module.
   * @module model/PartPatternResourceObject
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>PartPatternResourceObject</code>.
   * @alias module:model/PartPatternResourceObject
   * @class
   */
  var exports = function() {
    var _this = this;


  };

  /**
   * Constructs a <code>PartPatternResourceObject</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/PartPatternResourceObject} obj Optional instance to populate.
   * @return {module:model/PartPatternResourceObject} The populated <code>PartPatternResourceObject</code> instance.
   */
  exports.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new exports();

      if (data.hasOwnProperty('type')) {
        obj['type'] = ApiClient.convertToType(data['type'], 'String');
      }
    }
    return obj;
  }

  /**
   * @member {String} type
   */
  exports.prototype['type'] = undefined;



  return exports;
}));


