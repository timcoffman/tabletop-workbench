package com.tcoffman.ttwb.service.api;

import com.tcoffman.ttwb.service.ApiException;
import com.tcoffman.ttwb.service.ApiClient;
import com.tcoffman.ttwb.service.Configuration;
import com.tcoffman.ttwb.service.Pair;

import javax.ws.rs.core.GenericType;

import com.tcoffman.ttwb.service.api.data.AppInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class ApplicationInfoApi {
  private ApiClient apiClient;

  public ApplicationInfoApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ApplicationInfoApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Game Web Application Info
   * 
   * @return AppInfo
   * @throws ApiException if fails to make API call
   */
  public AppInfo getInfo() throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/app";

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

    GenericType<AppInfo> localVarReturnType = new GenericType<AppInfo>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}
