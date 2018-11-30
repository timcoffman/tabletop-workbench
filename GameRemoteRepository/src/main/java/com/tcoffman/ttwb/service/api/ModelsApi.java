package com.tcoffman.ttwb.service.api;

import com.tcoffman.ttwb.service.ApiException;
import com.tcoffman.ttwb.service.ApiClient;
import com.tcoffman.ttwb.service.Configuration;
import com.tcoffman.ttwb.service.Pair;

import javax.ws.rs.core.GenericType;

import com.tcoffman.ttwb.service.api.data.ModelPluginResource;
import com.tcoffman.ttwb.service.api.data.ModelResource;
import com.tcoffman.ttwb.service.api.data.PartInstanceResource;
import com.tcoffman.ttwb.service.api.data.PartPrototypeResource;
import com.tcoffman.ttwb.service.api.data.PlacePrototypeResource;
import com.tcoffman.ttwb.service.api.data.PlaceTypeResource;
import com.tcoffman.ttwb.service.api.data.RelationshipTypeResource;
import com.tcoffman.ttwb.service.api.data.RoleResource;
import com.tcoffman.ttwb.service.api.data.RuleResource;
import com.tcoffman.ttwb.service.api.data.StageResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class ModelsApi {
  private ApiClient apiClient;

  public ModelsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ModelsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Retrieve a Game Model
   * 
   * @param modelId  (required)
   * @return ModelResource
   * @throws ApiException if fails to make API call
   */
  public ModelResource getModel(String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getModel");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}"
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ModelResource> localVarReturnType = new GenericType<ModelResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param pluginUri  (required)
   * @param modelId  (required)
   * @return ModelPluginResource
   * @throws ApiException if fails to make API call
   */
  public ModelPluginResource getModelPlugin(String pluginUri, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'pluginUri' is set
    if (pluginUri == null) {
      throw new ApiException(400, "Missing the required parameter 'pluginUri' when calling getModelPlugin");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getModelPlugin");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/plugins/{pluginUri}"
      .replaceAll("\\{" + "pluginUri" + "\\}", apiClient.escapeString(pluginUri.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ModelPluginResource> localVarReturnType = new GenericType<ModelPluginResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of all Game Models
   * 
   * @return List&lt;String&gt;
   * @throws ApiException if fails to make API call
   */
  public List<String> getModels() throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/models";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<String>> localVarReturnType = new GenericType<List<String>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve a Part
   * 
   * @param partIndex  (required)
   * @param modelId  (required)
   * @return PartInstanceResource
   * @throws ApiException if fails to make API call
   */
  public PartInstanceResource getPart(Long partIndex, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'partIndex' is set
    if (partIndex == null) {
      throw new ApiException(400, "Missing the required parameter 'partIndex' when calling getPart");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getPart");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/parts/{partIndex}"
      .replaceAll("\\{" + "partIndex" + "\\}", apiClient.escapeString(partIndex.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PartInstanceResource> localVarReturnType = new GenericType<PartInstanceResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve a Part Prototype
   * 
   * @param prototypeId  (required)
   * @param modelId  (required)
   * @return PartPrototypeResource
   * @throws ApiException if fails to make API call
   */
  public PartPrototypeResource getPartPrototype(String prototypeId, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'prototypeId' is set
    if (prototypeId == null) {
      throw new ApiException(400, "Missing the required parameter 'prototypeId' when calling getPartPrototype");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getPartPrototype");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/prototypes/{prototypeId}"
      .replaceAll("\\{" + "prototypeId" + "\\}", apiClient.escapeString(prototypeId.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PartPrototypeResource> localVarReturnType = new GenericType<PartPrototypeResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of Part Prototypes
   * 
   * @param modelId  (required)
   * @return List&lt;PartPrototypeResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<PartPrototypeResource> getPartPrototypes(String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getPartPrototypes");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/prototypes"
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<PartPrototypeResource>> localVarReturnType = new GenericType<List<PartPrototypeResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of all Part Instances
   * 
   * @param modelId  (required)
   * @return List&lt;PartInstanceResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<PartInstanceResource> getParts(String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getParts");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/parts"
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<PartInstanceResource>> localVarReturnType = new GenericType<List<PartInstanceResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrive a Place Prototype
   * 
   * @param pluginUri  (required)
   * @param placeTypeLocalName  (required)
   * @param prototypeId  (required)
   * @param modelId  (required)
   * @return PlacePrototypeResource
   * @throws ApiException if fails to make API call
   */
  public PlacePrototypeResource getPlacePrototype(String pluginUri, String placeTypeLocalName, String prototypeId, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'pluginUri' is set
    if (pluginUri == null) {
      throw new ApiException(400, "Missing the required parameter 'pluginUri' when calling getPlacePrototype");
    }
    
    // verify the required parameter 'placeTypeLocalName' is set
    if (placeTypeLocalName == null) {
      throw new ApiException(400, "Missing the required parameter 'placeTypeLocalName' when calling getPlacePrototype");
    }
    
    // verify the required parameter 'prototypeId' is set
    if (prototypeId == null) {
      throw new ApiException(400, "Missing the required parameter 'prototypeId' when calling getPlacePrototype");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getPlacePrototype");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/prototypes/{prototypeId}/places/{pluginUri}/{placeTypeLocalName}"
      .replaceAll("\\{" + "pluginUri" + "\\}", apiClient.escapeString(pluginUri.toString()))
      .replaceAll("\\{" + "placeTypeLocalName" + "\\}", apiClient.escapeString(placeTypeLocalName.toString()))
      .replaceAll("\\{" + "prototypeId" + "\\}", apiClient.escapeString(prototypeId.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PlacePrototypeResource> localVarReturnType = new GenericType<PlacePrototypeResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of Place Prototypes
   * 
   * @param prototypeId  (required)
   * @param modelId  (required)
   * @return List&lt;PlacePrototypeResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<PlacePrototypeResource> getPlacePrototypes(String prototypeId, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'prototypeId' is set
    if (prototypeId == null) {
      throw new ApiException(400, "Missing the required parameter 'prototypeId' when calling getPlacePrototypes");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getPlacePrototypes");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/prototypes/{prototypeId}/places"
      .replaceAll("\\{" + "prototypeId" + "\\}", apiClient.escapeString(prototypeId.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<PlacePrototypeResource>> localVarReturnType = new GenericType<List<PlacePrototypeResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param localName  (required)
   * @param pluginUri  (required)
   * @param modelId  (required)
   * @return PlaceTypeResource
   * @throws ApiException if fails to make API call
   */
  public PlaceTypeResource getPlaceType(String localName, String pluginUri, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'localName' is set
    if (localName == null) {
      throw new ApiException(400, "Missing the required parameter 'localName' when calling getPlaceType");
    }
    
    // verify the required parameter 'pluginUri' is set
    if (pluginUri == null) {
      throw new ApiException(400, "Missing the required parameter 'pluginUri' when calling getPlaceType");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getPlaceType");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/plugins/{pluginUri}/placeTypes/{localName}"
      .replaceAll("\\{" + "localName" + "\\}", apiClient.escapeString(localName.toString()))
      .replaceAll("\\{" + "pluginUri" + "\\}", apiClient.escapeString(pluginUri.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PlaceTypeResource> localVarReturnType = new GenericType<PlaceTypeResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of all Place Types
   * 
   * @param pluginUri  (required)
   * @param modelId  (required)
   * @return List&lt;PlaceTypeResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<PlaceTypeResource> getPlaceTypes(String pluginUri, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'pluginUri' is set
    if (pluginUri == null) {
      throw new ApiException(400, "Missing the required parameter 'pluginUri' when calling getPlaceTypes");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getPlaceTypes");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/plugins/{pluginUri}/placeTypes"
      .replaceAll("\\{" + "pluginUri" + "\\}", apiClient.escapeString(pluginUri.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<PlaceTypeResource>> localVarReturnType = new GenericType<List<PlaceTypeResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param localName  (required)
   * @param pluginUri  (required)
   * @param modelId  (required)
   * @return RelationshipTypeResource
   * @throws ApiException if fails to make API call
   */
  public RelationshipTypeResource getRelationshipType(String localName, String pluginUri, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'localName' is set
    if (localName == null) {
      throw new ApiException(400, "Missing the required parameter 'localName' when calling getRelationshipType");
    }
    
    // verify the required parameter 'pluginUri' is set
    if (pluginUri == null) {
      throw new ApiException(400, "Missing the required parameter 'pluginUri' when calling getRelationshipType");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getRelationshipType");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/plugins/{pluginUri}/relationshipTypes/{localName}"
      .replaceAll("\\{" + "localName" + "\\}", apiClient.escapeString(localName.toString()))
      .replaceAll("\\{" + "pluginUri" + "\\}", apiClient.escapeString(pluginUri.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<RelationshipTypeResource> localVarReturnType = new GenericType<RelationshipTypeResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of all Relationship Types
   * 
   * @param pluginUri  (required)
   * @param modelId  (required)
   * @return List&lt;RelationshipTypeResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<RelationshipTypeResource> getRelationshipTypes(String pluginUri, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'pluginUri' is set
    if (pluginUri == null) {
      throw new ApiException(400, "Missing the required parameter 'pluginUri' when calling getRelationshipTypes");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getRelationshipTypes");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/plugins/{pluginUri}/relationshipTypes"
      .replaceAll("\\{" + "pluginUri" + "\\}", apiClient.escapeString(pluginUri.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<RelationshipTypeResource>> localVarReturnType = new GenericType<List<RelationshipTypeResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param modelId  (required)
   * @return List&lt;ModelPluginResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<ModelPluginResource> getRequiredPlugins(String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getRequiredPlugins");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/plugins"
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<ModelPluginResource>> localVarReturnType = new GenericType<List<ModelPluginResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve a Role
   * 
   * @param roleId  (required)
   * @param modelId  (required)
   * @return RoleResource
   * @throws ApiException if fails to make API call
   */
  public RoleResource getRole(String roleId, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'roleId' is set
    if (roleId == null) {
      throw new ApiException(400, "Missing the required parameter 'roleId' when calling getRole");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getRole");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/roles/{roleId}"
      .replaceAll("\\{" + "roleId" + "\\}", apiClient.escapeString(roleId.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<RoleResource> localVarReturnType = new GenericType<RoleResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of Game Roles
   * 
   * @param modelId  (required)
   * @return List&lt;RoleResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<RoleResource> getRoles(String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getRoles");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/roles"
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<RoleResource>> localVarReturnType = new GenericType<List<RoleResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve a Game Rule
   * 
   * @param ruleIndex  (required)
   * @param stageId  (required)
   * @param modelId  (required)
   * @return RuleResource
   * @throws ApiException if fails to make API call
   */
  public RuleResource getRule(Integer ruleIndex, String stageId, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'ruleIndex' is set
    if (ruleIndex == null) {
      throw new ApiException(400, "Missing the required parameter 'ruleIndex' when calling getRule");
    }
    
    // verify the required parameter 'stageId' is set
    if (stageId == null) {
      throw new ApiException(400, "Missing the required parameter 'stageId' when calling getRule");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getRule");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/stages/{stageId}/rules/{ruleIndex}"
      .replaceAll("\\{" + "ruleIndex" + "\\}", apiClient.escapeString(ruleIndex.toString()))
      .replaceAll("\\{" + "stageId" + "\\}", apiClient.escapeString(stageId.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<RuleResource> localVarReturnType = new GenericType<RuleResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of Game Rules
   * 
   * @param stageId  (required)
   * @param modelId  (required)
   * @return List&lt;RuleResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<RuleResource> getRules(String stageId, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stageId' is set
    if (stageId == null) {
      throw new ApiException(400, "Missing the required parameter 'stageId' when calling getRules");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getRules");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/stages/{stageId}/rules"
      .replaceAll("\\{" + "stageId" + "\\}", apiClient.escapeString(stageId.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<RuleResource>> localVarReturnType = new GenericType<List<RuleResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve a Stage
   * 
   * @param stageId  (required)
   * @param modelId  (required)
   * @return StageResource
   * @throws ApiException if fails to make API call
   */
  public StageResource getStage(String stageId, String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stageId' is set
    if (stageId == null) {
      throw new ApiException(400, "Missing the required parameter 'stageId' when calling getStage");
    }
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getStage");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/stages/{stageId}"
      .replaceAll("\\{" + "stageId" + "\\}", apiClient.escapeString(stageId.toString()))
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<StageResource> localVarReturnType = new GenericType<StageResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of Game Stages
   * 
   * @param modelId  (required)
   * @return List&lt;StageResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<StageResource> getStages(String modelId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'modelId' is set
    if (modelId == null) {
      throw new ApiException(400, "Missing the required parameter 'modelId' when calling getStages");
    }
    
    // create path and map variables
    String localVarPath = "/models/{modelId}/stages"
      .replaceAll("\\{" + "modelId" + "\\}", apiClient.escapeString(modelId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<StageResource>> localVarReturnType = new GenericType<List<StageResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}
