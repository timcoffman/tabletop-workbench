/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

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
  getRolesResponse(modelId: string): Observable<StrictHttpResponse<Array<RoleResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/roles`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<RoleResource>>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
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
  getMeta1Response(roleId: string,
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
  getMeta1(roleId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta1Response(roleId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta2Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
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
  getMeta2(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta2Response(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getModelResponse(modelId: string): Observable<StrictHttpResponse<ModelResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ModelResource>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getModel(modelId: string): Observable<ModelResource> {
    return this.getModelResponse(modelId).pipe(
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
  getMeta3Response(pluginUri: string,
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
  getMeta3(pluginUri: string,
    modelId: string,
    localName: string): Observable<ResourceMetaData> {
    return this.getMeta3Response(pluginUri, modelId, localName).pipe(
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
  getMeta4Response(pluginUri: string,
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
  getMeta4(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta4Response(pluginUri, modelId).pipe(
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
  getMeta5Response(pluginUri: string,
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
  getMeta5(pluginUri: string,
    modelId: string,
    localName: string): Observable<ResourceMetaData> {
    return this.getMeta5Response(pluginUri, modelId, localName).pipe(
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
  getMeta6Response(pluginUri: string,
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
  getMeta6(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta6Response(pluginUri, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param pluginUri undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta7Response(pluginUri: string,
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
  getMeta7(pluginUri: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta7Response(pluginUri, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta8Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
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
  getMeta8(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta8Response(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototypesResponse(modelId: string): Observable<StrictHttpResponse<Array<PartPrototypeResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PartPrototypeResource>>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototypes(modelId: string): Observable<Array<PartPrototypeResource>> {
    return this.getPartPrototypesResponse(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
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
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;




    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/${pluginUri}/${placeTypeLocalName}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<PlacePrototypeResource>;
      })
    );
  }

  /**
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
      __map(_r => _r.body)
    );
  }

  /**
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
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;




    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
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
      __map(_r => _r.body)
    );
  }

  /**
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypesResponse(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<Array<PlacePrototypeResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PlacePrototypeResource>>;
      })
    );
  }

  /**
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPlacePrototypes(prototypeId: string,
    modelId: string): Observable<Array<PlacePrototypeResource>> {
    return this.getPlacePrototypesResponse(prototypeId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta10Response(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/places/meta`,
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
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta10(prototypeId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta10Response(prototypeId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototypeResponse(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<PartPrototypeResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<PartPrototypeResource>;
      })
    );
  }

  /**
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPartPrototype(prototypeId: string,
    modelId: string): Observable<PartPrototypeResource> {
    return this.getPartPrototypeResponse(prototypeId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta11Response(prototypeId: string,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/${prototypeId}/meta`,
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
   * @param prototypeId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta11(prototypeId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta11Response(prototypeId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta12Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/prototypes/meta`,
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
  getMeta12(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta12Response(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getStagesResponse(modelId: string): Observable<StrictHttpResponse<Array<StageResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<StageResource>>;
      })
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getStages(modelId: string): Observable<Array<StageResource>> {
    return this.getStagesResponse(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRulesResponse(stageId: string,
    modelId: string): Observable<StrictHttpResponse<Array<RuleResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


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



    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/rules/${ruleIndex}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<RuleResource>;
      })
    );
  }

  /**
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getRule(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<RuleResource> {
    return this.getRuleResponse(stageId, ruleIndex, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stageId undefined
   * @param ruleIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta13Response(stageId: string,
    ruleIndex: number,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;



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
   * @param modelId undefined
   * @return successful operation
   */
  getMeta14(stageId: string,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta14Response(stageId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getStageResponse(stageId: string,
    modelId: string): Observable<StrictHttpResponse<StageResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<StageResource>;
      })
    );
  }

  /**
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getStage(stageId: string,
    modelId: string): Observable<StageResource> {
    return this.getStageResponse(stageId, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stageId undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta15Response(stageId: string,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/stages/${stageId}/meta`,
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
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/parts/${partIndex}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<PartInstanceResource>;
      })
    );
  }

  /**
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getPart(partIndex: number,
    modelId: string): Observable<PartInstanceResource> {
    return this.getPartResponse(partIndex, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta17Response(partIndex: number,
    modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/parts/${partIndex}/meta`,
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
   * @param partIndex undefined
   * @param modelId undefined
   * @return successful operation
   */
  getMeta17(partIndex: number,
    modelId: string): Observable<ResourceMetaData> {
    return this.getMeta17Response(partIndex, modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta18Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/models/${modelId}/parts/meta`,
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
  getMeta18(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta18Response(modelId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param modelId undefined
   * @return successful operation
   */
  getMeta19Response(modelId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
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
  getMeta19(modelId: string): Observable<ResourceMetaData> {
    return this.getMeta19Response(modelId).pipe(
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
  getMeta20Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
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
  getMeta20(): Observable<ResourceMetaData> {
    return this.getMeta20Response().pipe(
      __map(_r => _r.body)
    );
  }
}

module ModelsService {
}

export { ModelsService }
