package com.tcoffman.ttwb.service.api;

import com.tcoffman.ttwb.service.ApiException;
import com.tcoffman.ttwb.service.ApiClient;
import com.tcoffman.ttwb.service.Configuration;
import com.tcoffman.ttwb.service.Pair;

import javax.ws.rs.core.GenericType;

import com.tcoffman.ttwb.service.api.data.LogEntryResource;
import com.tcoffman.ttwb.service.api.data.OperationPatternSetResource;
import com.tcoffman.ttwb.service.api.data.PartResource;
import com.tcoffman.ttwb.service.api.data.ParticipantCreationForm;
import com.tcoffman.ttwb.service.api.data.ParticipantResource;
import com.tcoffman.ttwb.service.api.data.PlaceResource;
import com.tcoffman.ttwb.service.api.data.RelationshipResource;
import com.tcoffman.ttwb.service.api.data.RunnerCreationForm;
import com.tcoffman.ttwb.service.api.data.RunnerResource;
import com.tcoffman.ttwb.service.api.data.StateMutationForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class RunnersApi {
  private ApiClient apiClient;

  public RunnersApi() {
    this(Configuration.getDefaultApiClient());
  }

  public RunnersApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * 
   * 
   * @param stateId  (required)
   * @param body  (optional)
   * @return ParticipantResource
   * @throws ApiException if fails to make API call
   */
  public ParticipantResource createParticipant(String stateId, ParticipantCreationForm body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling createParticipant");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/participants"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ParticipantResource> localVarReturnType = new GenericType<ParticipantResource>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param body  (optional)
   * @return RunnerResource
   * @throws ApiException if fails to make API call
   */
  public RunnerResource createRunner(RunnerCreationForm body) throws ApiException {
    Object localVarPostBody = body;
    
    // create path and map variables
    String localVarPath = "/runners";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<RunnerResource> localVarReturnType = new GenericType<RunnerResource>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param stateId  (required)
   * @return List&lt;LogEntryResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<LogEntryResource> getLogEntries(String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getLogEntries");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/log"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<List<LogEntryResource>> localVarReturnType = new GenericType<List<LogEntryResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param logEntryIndex  (required)
   * @param stateId  (required)
   * @return LogEntryResource
   * @throws ApiException if fails to make API call
   */
  public LogEntryResource getLogEntry(Long logEntryIndex, String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'logEntryIndex' is set
    if (logEntryIndex == null) {
      throw new ApiException(400, "Missing the required parameter 'logEntryIndex' when calling getLogEntry");
    }
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getLogEntry");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/log/{logEntryIndex}"
      .replaceAll("\\{" + "logEntryIndex" + "\\}", apiClient.escapeString(logEntryIndex.toString()))
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<LogEntryResource> localVarReturnType = new GenericType<LogEntryResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param stateId  (required)
   * @return List&lt;OperationPatternSetResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<OperationPatternSetResource> getOperationPatternSets(String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getOperationPatternSets");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/allowedOperations"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<List<OperationPatternSetResource>> localVarReturnType = new GenericType<List<OperationPatternSetResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param stateId  (required)
   * @return List&lt;ParticipantResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<ParticipantResource> getParticipants(String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getParticipants");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/participants"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<List<ParticipantResource>> localVarReturnType = new GenericType<List<ParticipantResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param stateId  (required)
   * @return List&lt;PartResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<PartResource> getParts1(String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getParts1");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/parts"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<List<PartResource>> localVarReturnType = new GenericType<List<PartResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param pluginUri  (required)
   * @param placeTypeLocalName  (required)
   * @param partId  (required)
   * @param stateId  (required)
   * @return PlaceResource
   * @throws ApiException if fails to make API call
   */
  public PlaceResource getPlace(String pluginUri, String placeTypeLocalName, String partId, String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'pluginUri' is set
    if (pluginUri == null) {
      throw new ApiException(400, "Missing the required parameter 'pluginUri' when calling getPlace");
    }
    
    // verify the required parameter 'placeTypeLocalName' is set
    if (placeTypeLocalName == null) {
      throw new ApiException(400, "Missing the required parameter 'placeTypeLocalName' when calling getPlace");
    }
    
    // verify the required parameter 'partId' is set
    if (partId == null) {
      throw new ApiException(400, "Missing the required parameter 'partId' when calling getPlace");
    }
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getPlace");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/parts/{partId}/places/{pluginUri}/{placeTypeLocalName}"
      .replaceAll("\\{" + "pluginUri" + "\\}", apiClient.escapeString(pluginUri.toString()))
      .replaceAll("\\{" + "placeTypeLocalName" + "\\}", apiClient.escapeString(placeTypeLocalName.toString()))
      .replaceAll("\\{" + "partId" + "\\}", apiClient.escapeString(partId.toString()))
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<PlaceResource> localVarReturnType = new GenericType<PlaceResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param partId  (required)
   * @param stateId  (required)
   * @return List&lt;PlaceResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<PlaceResource> getPlaces(String partId, String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'partId' is set
    if (partId == null) {
      throw new ApiException(400, "Missing the required parameter 'partId' when calling getPlaces");
    }
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getPlaces");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/parts/{partId}/places"
      .replaceAll("\\{" + "partId" + "\\}", apiClient.escapeString(partId.toString()))
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<List<PlaceResource>> localVarReturnType = new GenericType<List<PlaceResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param stateId  (required)
   * @return List&lt;RelationshipResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<RelationshipResource> getRelationships(String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getRelationships");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/relationships"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<List<RelationshipResource>> localVarReturnType = new GenericType<List<RelationshipResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve a Runner
   * 
   * @param stateId  (required)
   * @return RunnerResource
   * @throws ApiException if fails to make API call
   */
  public RunnerResource getRunner(String stateId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling getRunner");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

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

    GenericType<RunnerResource> localVarReturnType = new GenericType<RunnerResource>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * List of all runners
   * 
   * @return List&lt;RunnerResource&gt;
   * @throws ApiException if fails to make API call
   */
  public List<RunnerResource> getRunners() throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/runners";

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

    GenericType<List<RunnerResource>> localVarReturnType = new GenericType<List<RunnerResource>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param stateId  (required)
   * @param body  (optional)
   * @return LogEntryResource
   * @throws ApiException if fails to make API call
   */
  public LogEntryResource mutate(String stateId, StateMutationForm body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling mutate");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/log"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<LogEntryResource> localVarReturnType = new GenericType<LogEntryResource>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param stateId  (required)
   * @param body  (optional)
   * @return List&lt;StateMutationForm&gt;
   * @throws ApiException if fails to make API call
   */
  public List<StateMutationForm> mutations(String stateId, StateMutationForm body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'stateId' is set
    if (stateId == null) {
      throw new ApiException(400, "Missing the required parameter 'stateId' when calling mutations");
    }
    
    // create path and map variables
    String localVarPath = "/runners/{stateId}/log/mutations"
      .replaceAll("\\{" + "stateId" + "\\}", apiClient.escapeString(stateId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/xml", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<List<StateMutationForm>> localVarReturnType = new GenericType<List<StateMutationForm>>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}
