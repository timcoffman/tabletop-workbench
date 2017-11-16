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
    define(['ApiClient', 'model/OperationPatternSetResource'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    module.exports = factory(require('../ApiClient'), require('./OperationPatternSetResource'));
  } else {
    // Browser globals (root is window)
    if (!root.SwaggerJsClient) {
      root.SwaggerJsClient = {};
    }
    root.SwaggerJsClient.OperationPatternSetsResource = factory(root.SwaggerJsClient.ApiClient, root.SwaggerJsClient.OperationPatternSetResource);
  }
}(this, function(ApiClient, OperationPatternSetResource) {
  'use strict';




  /**
   * The OperationPatternSetsResource model module.
   * @module model/OperationPatternSetsResource
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>OperationPatternSetsResource</code>.
   * @alias module:model/OperationPatternSetsResource
   * @class
   */
  var exports = function() {
    var _this = this;



  };

  /**
   * Constructs a <code>OperationPatternSetsResource</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/OperationPatternSetsResource} obj Optional instance to populate.
   * @return {module:model/OperationPatternSetsResource} The populated <code>OperationPatternSetsResource</code> instance.
   */
  exports.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new exports();

      if (data.hasOwnProperty('resource')) {
        obj['resource'] = ApiClient.convertToType(data['resource'], 'String');
      }
      if (data.hasOwnProperty('operationPatternSets')) {
        obj['operationPatternSets'] = ApiClient.convertToType(data['operationPatternSets'], [OperationPatternSetResource]);
      }
    }
    return obj;
  }

  /**
   * @member {String} resource
   */
  exports.prototype['resource'] = undefined;
  /**
   * @member {Array.<module:model/OperationPatternSetResource>} operationPatternSets
   */
  exports.prototype['operationPatternSets'] = undefined;



  return exports;
}));

