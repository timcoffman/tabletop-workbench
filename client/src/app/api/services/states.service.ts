/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { PlaceResource } from '../models/place-resource';
import { PlacePatternForm } from '../models/place-pattern-form';
import { StateResource } from '../models/state-resource';
import { LogEntryResource } from '../models/log-entry-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { StateMutationForm } from '../models/state-mutation-form';
import { PartResource } from '../models/part-resource';
import { ParticipantResource } from '../models/participant-resource';
import { ParticipantCreationForm } from '../models/participant-creation-form';
import { RelationshipResource } from '../models/relationship-resource';
import { OperationPatternSetResource } from '../models/operation-pattern-set-resource';
import { StateCreationForm } from '../models/state-creation-form';
@Injectable({
  providedIn: 'root',
})
class StatesService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  findResponse(stateId: string,
    body?: PlacePatternForm): Observable<StrictHttpResponse<Array<PlaceResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/find`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PlaceResource>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  find(stateId: string,
    body?: PlacePatternForm): Observable<Array<PlaceResource>> {
    return this.findResponse(stateId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getStateResponse(stateId: string): Observable<StrictHttpResponse<StateResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<StateResource>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getState(stateId: string): Observable<StateResource> {
    return this.getStateResponse(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   */
  deleteResponse(stateId: string): Observable<StrictHttpResponse<void>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/states/${stateId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'text'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r.clone({ body: null }) as StrictHttpResponse<void>
      })
    );
  }

  /**
   * @param stateId undefined
   */
  delete(stateId: string): Observable<void> {
    return this.deleteResponse(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntry1Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<LogEntryResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log/${logEntryIndex}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<LogEntryResource>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntry1(stateId: string,
    logEntryIndex: number): Observable<LogEntryResource> {
    return this.getLogEntry1Response(stateId, logEntryIndex).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta31Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log/${logEntryIndex}/meta`,
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
   * @param stateId undefined
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta31(stateId: string,
    logEntryIndex: number): Observable<ResourceMetaData> {
    return this.getMeta31Response(stateId, logEntryIndex).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getLogEntries1Response(stateId: string): Observable<StrictHttpResponse<Array<LogEntryResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<LogEntryResource>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getLogEntries1(stateId: string): Observable<Array<LogEntryResource>> {
    return this.getLogEntries1Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  mutate1Response(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<LogEntryResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/log`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<LogEntryResource>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  mutate1(stateId: string,
    body?: StateMutationForm): Observable<LogEntryResource> {
    return this.mutate1Response(stateId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  mutations1Response(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<Array<StateMutationForm>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/log/mutations`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<StateMutationForm>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  mutations1(stateId: string,
    body?: StateMutationForm): Observable<Array<StateMutationForm>> {
    return this.mutations1Response(stateId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta32Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
  getMeta32(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta32Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getParts2Response(stateId: string): Observable<StrictHttpResponse<Array<PartResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PartResource>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getParts2(stateId: string): Observable<Array<PartResource>> {
    return this.getParts2Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlace1Response(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<PlaceResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;




    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<PlaceResource>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlace1(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<PlaceResource> {
    return this.getPlace1Response(stateId, pluginUri, placeTypeLocalName, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta33Response(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;




    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
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
   * @param stateId undefined
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta33(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta33Response(stateId, pluginUri, placeTypeLocalName, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlaces1Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<Array<PlaceResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<PlaceResource>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlaces1(stateId: string,
    partId: string): Observable<Array<PlaceResource>> {
    return this.getPlaces1Response(stateId, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta34Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/meta`,
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
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta34(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta34Response(stateId, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta35Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/meta`,
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
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta35(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta35Response(stateId, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta36Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
  getMeta36(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta36Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getParticipants1Response(stateId: string): Observable<StrictHttpResponse<Array<ParticipantResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/participants`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<ParticipantResource>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getParticipants1(stateId: string): Observable<Array<ParticipantResource>> {
    return this.getParticipants1Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  createParticipant1Response(stateId: string,
    body?: ParticipantCreationForm): Observable<StrictHttpResponse<ParticipantResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/participants`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<ParticipantResource>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  createParticipant1(stateId: string,
    body?: ParticipantCreationForm): Observable<ParticipantResource> {
    return this.createParticipant1Response(stateId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta37Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/participants/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
  getMeta37(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta37Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getRelationships1Response(stateId: string): Observable<StrictHttpResponse<Array<RelationshipResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/relationships`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<RelationshipResource>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getRelationships1(stateId: string): Observable<Array<RelationshipResource>> {
    return this.getRelationships1Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta38Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/relationships/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
  getMeta38(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta38Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getOperationPatternSets1Response(stateId: string): Observable<StrictHttpResponse<Array<OperationPatternSetResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/allowedOperations`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<OperationPatternSetResource>>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getOperationPatternSets1(stateId: string): Observable<Array<OperationPatternSetResource>> {
    return this.getOperationPatternSets1Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta39Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/allowedOperations/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
  getMeta39(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta39Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta40Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
  getMeta40(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta40Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @return successful operation
   */
  getStatesResponse(): Observable<StrictHttpResponse<Array<string>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states`,
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
  getStates(): Observable<Array<string>> {
    return this.getStatesResponse().pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param body undefined
   * @return successful operation
   */
  createStateResponse(body?: StateCreationForm): Observable<StrictHttpResponse<StateResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<StateResource>;
      })
    );
  }

  /**
   * @param body undefined
   * @return successful operation
   */
  createState(body?: StateCreationForm): Observable<StateResource> {
    return this.createStateResponse(body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @return successful operation
   */
  getMeta41Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/meta`,
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
  getMeta41(): Observable<ResourceMetaData> {
    return this.getMeta41Response().pipe(
      __map(_r => _r.body)
    );
  }
}

module StatesService {
}

export { StatesService }
