/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

<<<<<<< HEAD
import { RoleResource } from '../models/role-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { ModelResource } from '../models/model-resource';
import { ModelPluginResource } from '../models/model-plugin-resource';
import { PlaceTypeResource } from '../models/place-type-resource';
import { RelationshipTypeResource } from '../models/relationship-type-resource';
import { PartPrototypeResource } from '../models/part-prototype-resource';
import { PlacePrototypeResource } from '../models/place-prototype-resource';
import { StageResource } from '../models/stage-resource';
import { RuleResource } from '../models/rule-resource';
import { PartInstanceResource } from '../models/part-instance-resource';
=======
import { ModelResource } from '../models/model-resource';
import { PartInstanceResource } from '../models/part-instance-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { RoleResource } from '../models/role-resource';
import { ModelPluginResource } from '../models/model-plugin-resource';
import { PlaceTypeResource } from '../models/place-type-resource';
import { RelationshipTypeResource } from '../models/relationship-type-resource';
import { StageResource } from '../models/stage-resource';
import { RuleResource } from '../models/rule-resource';
import { PartPrototypeResource } from '../models/part-prototype-resource';
import { PlacePrototypeResource } from '../models/place-prototype-resource';
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
@Injectable({
  providedIn: 'root',
})
class ModelsService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getRolesResponse(modelId: string): Observable<StrictHttpResponse<Array<RoleResource>>> {
=======
  getModelResponse(modelId: string): Observable<StrictHttpResponse<ModelResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/roles`,
=======
      this.rootUrl + `/models/${modelId}`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<Array<RoleResource>>;
=======
        return _r as StrictHttpResponse<ModelResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getRoles(modelId: string): Observable<Array<RoleResource>> {
    return this.getRolesResponse(modelId).pipe(
=======
  getModel(modelId: string): Observable<ModelResource> {
    return this.getModelResponse(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRoleResponse(roleId: string,
    modelId: string): Observable<StrictHttpResponse<RoleResource>> {
=======
   * @param modelId undefined
   * @return successful operation
   */
  getPartsResponse(modelId: string): Observable<StrictHttpResponse<Array<PartInstanceResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/parts`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PartInstanceResource>>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getParts(modelId: string): Observable<Array<PartInstanceResource>> {
    return this.getPartsResponse(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartResponse(partIndex: number,
    modelId: string): Observable<StrictHttpResponse<PartInstanceResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/roles/${roleId}`,
=======
      this.rootUrl + `/models/${modelId}/parts/${partIndex}`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<RoleResource>;
=======
        return _r as StrictHttpResponse<PartInstanceResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRole(roleId: string,
    modelId: string): Observable<RoleResource> {
    return this.getRoleResponse(roleId, modelId).pipe(
=======
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPart(partIndex: number,
    modelId: string): Observable<PartInstanceResource> {
    return this.getPartResponse(partIndex, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta1Response(roleId: string,
=======
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMetaResponse(partIndex: number,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/roles/${roleId}/meta`,
=======
      this.rootUrl + `/models/${modelId}/parts/${partIndex}/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta1(roleId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta1Response(roleId, modelId).pipe(
=======
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta(partIndex: number,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMetaResponse(partIndex, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta2Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta1Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/roles/meta`,
=======
      this.rootUrl + `/models/${modelId}/parts/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta2(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta2Response(modelId).pipe(
=======
  getMeta1(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta1Response(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getModelResponse(modelId: string): Observable<StrictHttpResponse<ModelResource>> {
=======
  getRolesResponse(modelId: string): Observable<StrictHttpResponse<Array<RoleResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}`,
=======
      this.rootUrl + `/models/${modelId}/roles`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<ModelResource>;
=======
        return _r as StrictHttpResponse<Array<RoleResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getModel(modelId: string): Observable<ModelResource> {
    return this.getModelResponse(modelId).pipe(
=======
  getRoles(modelId: string): Observable<Array<RoleResource>> {
    return this.getRolesResponse(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRoleResponse(roleId: string,
    modelId: string): Observable<StrictHttpResponse<RoleResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/roles/${roleId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<RoleResource>;
      })
    );
  }

  /**
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRole(roleId: string,
    modelId: string): Observable<RoleResource> {
    return this.getRoleResponse(roleId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta2Response(roleId: string,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/roles/${roleId}/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param roleId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta2(roleId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta2Response(roleId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta3Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/roles/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta3(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta3Response(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getRequiredPluginsResponse(modelId: string): Observable<StrictHttpResponse<Array<ModelPluginResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<ModelPluginResource>>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getRequiredPlugins(modelId: string): Observable<Array<ModelPluginResource>> {
    return this.getRequiredPluginsResponse(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
  getModelPluginResponse(pluginUri: string,
    modelId: string): Observable<StrictHttpResponse<ModelPluginResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ModelPluginResource>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
  getModelPlugin(pluginUri: string,
    modelId: string): Observable<ModelPluginResource> {
    return this.getModelPluginResponse(pluginUri, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
  getPlaceTypeResponse(pluginUri: string,
    modelId: string,
    localName: string): Observable<StrictHttpResponse<PlaceTypeResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;



    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/placeTypes/${localName}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<PlaceTypeResource>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
  getPlaceType(pluginUri: string,
    modelId: string,
    localName: string): Observable<PlaceTypeResource> {
    return this.getPlaceTypeResponse(pluginUri, modelId, localName).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta3Response(pluginUri: string,
=======
  getMeta4Response(pluginUri: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string,
    localName: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;



    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/placeTypes/${localName}/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta3(pluginUri: string,
    modelId: string,
    localName: string): Observable<ResourceMetaData> {
    return this.getMeta3Response(pluginUri, modelId, localName).pipe(
=======
  getMeta4(pluginUri: string,
    modelId: string,
    localName: string): Observable<ResourceMetaData> {
    return this.getMeta4Response(pluginUri, modelId, localName).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlaceTypesResponse(pluginUri: string,
    modelId: string): Observable<StrictHttpResponse<Array<PlaceTypeResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/placeTypes`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PlaceTypeResource>>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlaceTypes(pluginUri: string,
    modelId: string): Observable<Array<PlaceTypeResource>> {
    return this.getPlaceTypesResponse(pluginUri, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta4Response(pluginUri: string,
=======
  getMeta5Response(pluginUri: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/placeTypes/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta4(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta4Response(pluginUri, modelId).pipe(
=======
  getMeta5(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta5Response(pluginUri, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
  getRelationshipTypeResponse(pluginUri: string,
    modelId: string,
    localName: string): Observable<StrictHttpResponse<RelationshipTypeResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;



    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/relationshipTypes/${localName}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<RelationshipTypeResource>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
  getRelationshipType(pluginUri: string,
    modelId: string,
    localName: string): Observable<RelationshipTypeResource> {
    return this.getRelationshipTypeResponse(pluginUri, modelId, localName).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta5Response(pluginUri: string,
=======
  getMeta6Response(pluginUri: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string,
    localName: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;



    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/relationshipTypes/${localName}/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @param localName undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta5(pluginUri: string,
    modelId: string,
    localName: string): Observable<ResourceMetaData> {
    return this.getMeta5Response(pluginUri, modelId, localName).pipe(
=======
  getMeta6(pluginUri: string,
    modelId: string,
    localName: string): Observable<ResourceMetaData> {
    return this.getMeta6Response(pluginUri, modelId, localName).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRelationshipTypesResponse(pluginUri: string,
    modelId: string): Observable<StrictHttpResponse<Array<RelationshipTypeResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/relationshipTypes`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<RelationshipTypeResource>>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRelationshipTypes(pluginUri: string,
    modelId: string): Observable<Array<RelationshipTypeResource>> {
    return this.getRelationshipTypesResponse(pluginUri, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta6Response(pluginUri: string,
=======
  getMeta7Response(pluginUri: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/relationshipTypes/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta6(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta6Response(pluginUri, modelId).pipe(
=======
  getMeta7(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta7Response(pluginUri, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta7Response(pluginUri: string,
=======
  getMeta8Response(pluginUri: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/${pluginUri}/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta7(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta7Response(pluginUri, modelId).pipe(
=======
  getMeta8(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta8Response(pluginUri, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta8Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta9Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/plugins/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta8(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta8Response(modelId).pipe(
=======
  getMeta9(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta9Response(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getPartPrototypesResponse(modelId: string): Observable<StrictHttpResponse<Array<PartPrototypeResource>>> {
=======
  getStagesResponse(modelId: string): Observable<StrictHttpResponse<Array<StageResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/prototypes`,
=======
      this.rootUrl + `/models/${modelId}/stages`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<Array<PartPrototypeResource>>;
=======
        return _r as StrictHttpResponse<Array<StageResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getPartPrototypes(modelId: string): Observable<Array<PartPrototypeResource>> {
    return this.getPartPrototypesResponse(modelId).pipe(
=======
  getStages(modelId: string): Observable<Array<StageResource>> {
    return this.getStagesResponse(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypeResponse(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    modelId: string): Observable<StrictHttpResponse<PlacePrototypeResource>> {
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRulesResponse(stageId: string,
    modelId: string): Observable<StrictHttpResponse<Array<RuleResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


<<<<<<< HEAD


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/${pluginUri}/${placeTypeLocalName}`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<PlacePrototypeResource>;
=======
        return _r as StrictHttpResponse<Array<RuleResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototype(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    modelId: string): Observable<PlacePrototypeResource> {
    return this.getPlacePrototypeResponse(prototypeId, pluginUri, placeTypeLocalName, modelId).pipe(
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRules(stageId: string,
    modelId: string): Observable<Array<RuleResource>> {
    return this.getRulesResponse(stageId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta9Response(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRuleResponse(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<StrictHttpResponse<RuleResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;



<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules/${ruleIndex}`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<ResourceMetaData>;
=======
        return _r as StrictHttpResponse<RuleResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta9(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta9Response(prototypeId, pluginUri, placeTypeLocalName, modelId).pipe(
=======
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRule(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<RuleResource> {
    return this.getRuleResponse(stageId, ruleIndex, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypesResponse(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<Array<PlacePrototypeResource>>> {
=======
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta10Response(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules/${ruleIndex}/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<Array<PlacePrototypeResource>>;
=======
        return _r as StrictHttpResponse<ResourceMetaData>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypes(prototypeId: string,
    modelId: string): Observable<Array<PlacePrototypeResource>> {
    return this.getPlacePrototypesResponse(prototypeId, modelId).pipe(
=======
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta10(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta10Response(stageId, ruleIndex, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta10Response(prototypeId: string,
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta11Response(stageId: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/meta`,
=======
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta10(prototypeId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta10Response(prototypeId, modelId).pipe(
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta11(stageId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta11Response(stageId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototypeResponse(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<PartPrototypeResource>> {
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getStageResponse(stageId: string,
    modelId: string): Observable<StrictHttpResponse<StageResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}`,
=======
      this.rootUrl + `/models/${modelId}/stages/${stageId}`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<PartPrototypeResource>;
=======
        return _r as StrictHttpResponse<StageResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototype(prototypeId: string,
    modelId: string): Observable<PartPrototypeResource> {
    return this.getPartPrototypeResponse(prototypeId, modelId).pipe(
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getStage(stageId: string,
    modelId: string): Observable<StageResource> {
    return this.getStageResponse(stageId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta11Response(prototypeId: string,
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta12Response(stageId: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/meta`,
=======
      this.rootUrl + `/models/${modelId}/stages/${stageId}/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta11(prototypeId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta11Response(prototypeId, modelId).pipe(
=======
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta12(stageId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta12Response(stageId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta12Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta13Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/prototypes/meta`,
=======
      this.rootUrl + `/models/${modelId}/stages/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta12(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta12Response(modelId).pipe(
=======
  getMeta13(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta13Response(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getStagesResponse(modelId: string): Observable<StrictHttpResponse<Array<StageResource>>> {
=======
  getPartPrototypesResponse(modelId: string): Observable<StrictHttpResponse<Array<PartPrototypeResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/stages`,
=======
      this.rootUrl + `/models/${modelId}/prototypes`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<Array<StageResource>>;
=======
        return _r as StrictHttpResponse<Array<PartPrototypeResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getStages(modelId: string): Observable<Array<StageResource>> {
    return this.getStagesResponse(modelId).pipe(
=======
  getPartPrototypes(modelId: string): Observable<Array<PartPrototypeResource>> {
    return this.getPartPrototypesResponse(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRulesResponse(stageId: string,
    modelId: string): Observable<StrictHttpResponse<Array<RuleResource>>> {
=======
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypeResponse(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    modelId: string): Observable<StrictHttpResponse<PlacePrototypeResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<RuleResource>>;
      })
    );
  }

  /**
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRules(stageId: string,
    modelId: string): Observable<Array<RuleResource>> {
    return this.getRulesResponse(stageId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRuleResponse(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<StrictHttpResponse<RuleResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

=======
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules/${ruleIndex}`,
=======
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/${pluginUri}/${placeTypeLocalName}`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<RuleResource>;
=======
        return _r as StrictHttpResponse<PlacePrototypeResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRule(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<RuleResource> {
    return this.getRuleResponse(stageId, ruleIndex, modelId).pipe(
=======
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototype(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    modelId: string): Observable<PlacePrototypeResource> {
    return this.getPlacePrototypeResponse(prototypeId, pluginUri, placeTypeLocalName, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta13Response(stageId: string,
    ruleIndex: number,
=======
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta14Response(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;



<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules/${ruleIndex}/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta13(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta13Response(stageId, ruleIndex, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta14Response(stageId: string,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules/meta`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta14(stageId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta14Response(stageId, modelId).pipe(
=======
   * @param prototypeId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta14(prototypeId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta14Response(prototypeId, pluginUri, placeTypeLocalName, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getStageResponse(stageId: string,
    modelId: string): Observable<StrictHttpResponse<StageResource>> {
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypesResponse(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<Array<PlacePrototypeResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/stages/${stageId}`,
=======
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<StageResource>;
=======
        return _r as StrictHttpResponse<Array<PlacePrototypeResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getStage(stageId: string,
    modelId: string): Observable<StageResource> {
    return this.getStageResponse(stageId, modelId).pipe(
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypes(prototypeId: string,
    modelId: string): Observable<Array<PlacePrototypeResource>> {
    return this.getPlacePrototypesResponse(prototypeId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta15Response(stageId: string,
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta15Response(prototypeId: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/stages/${stageId}/meta`,
=======
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta15(stageId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta15Response(stageId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta16Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta16(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta16Response(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getPartsResponse(modelId: string): Observable<StrictHttpResponse<Array<PartInstanceResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/parts`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PartInstanceResource>>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getParts(modelId: string): Observable<Array<PartInstanceResource>> {
    return this.getPartsResponse(modelId).pipe(
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta15(prototypeId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta15Response(prototypeId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartResponse(partIndex: number,
    modelId: string): Observable<StrictHttpResponse<PartInstanceResource>> {
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototypeResponse(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<PartPrototypeResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/parts/${partIndex}`,
=======
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
<<<<<<< HEAD
        return _r as StrictHttpResponse<PartInstanceResource>;
=======
        return _r as StrictHttpResponse<PartPrototypeResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPart(partIndex: number,
    modelId: string): Observable<PartInstanceResource> {
    return this.getPartResponse(partIndex, modelId).pipe(
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototype(prototypeId: string,
    modelId: string): Observable<PartPrototypeResource> {
    return this.getPartPrototypeResponse(prototypeId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta17Response(partIndex: number,
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta16Response(prototypeId: string,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/parts/${partIndex}/meta`,
=======
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta17(partIndex: number,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta17Response(partIndex, modelId).pipe(
=======
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta16(prototypeId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta16Response(prototypeId, modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta18Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta17Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/models/${modelId}/parts/meta`,
=======
      this.rootUrl + `/models/${modelId}/prototypes/meta`,
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta18(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta18Response(modelId).pipe(
=======
  getMeta17(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta17Response(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta19Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta18Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta19(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta19Response(modelId).pipe(
=======
  getMeta18(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta18Response(modelId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @return successful operation
   */
  getModelsResponse(): Observable<StrictHttpResponse<Array<string>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<string>>;
      })
    );
  }

  /**
   * @return successful operation
   */
  getModels(): Observable<Array<string>> {
    return this.getModelsResponse().pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta20Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta19Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/meta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ResourceMetaData>;
      })
    );
  }

  /**
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta20(): Observable<ResourceMetaData> {
    return this.getMeta20Response().pipe(
=======
  getMeta19(): Observable<ResourceMetaData> {
    return this.getMeta19Response().pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }
}

module ModelsService {
}

export { ModelsService }
