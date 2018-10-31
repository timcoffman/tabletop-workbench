/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { RunnerResource } from '../models/runner-resource';
import { RunnerCreationForm } from '../models/runner-creation-form';
import { LogEntryResource } from '../models/log-entry-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { StateMutationForm } from '../models/state-mutation-form';
import { PartResource } from '../models/part-resource';
import { PlaceResource } from '../models/place-resource';
import { ParticipantResource } from '../models/participant-resource';
import { ParticipantCreationForm } from '../models/participant-creation-form';
import { RelationshipResource } from '../models/relationship-resource';
import { OperationPatternSetResource } from '../models/operation-pattern-set-resource';
@Injectable({
  providedIn: 'root',
})
class RunnersService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * @return successful operation
   */
  getRunnersResponse(): Observable<StrictHttpResponse<Array<RunnerResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<Array<RunnerResource>>;
      })
    );
  }

  /**
   * @return successful operation
   */
  getRunners(): Observable<Array<RunnerResource>> {
    return this.getRunnersResponse().pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param body undefined
   * @return successful operation
   */
  createRunnerResponse(body?: RunnerCreationForm): Observable<StrictHttpResponse<RunnerResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<RunnerResource>;
      })
    );
  }

  /**
   * @param body undefined
   * @return successful operation
   */
  createRunner(body?: RunnerCreationForm): Observable<RunnerResource> {
    return this.createRunnerResponse(body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntryResponse(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<LogEntryResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/${logEntryIndex}`,
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
  getLogEntry(stateId: string,
    logEntryIndex: number): Observable<LogEntryResource> {
    return this.getLogEntryResponse(stateId, logEntryIndex).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta20Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/${logEntryIndex}/meta`,
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
  getMeta20(stateId: string,
    logEntryIndex: number): Observable<ResourceMetaData> {
    return this.getMeta20Response(stateId, logEntryIndex).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getLogEntriesResponse(stateId: string): Observable<StrictHttpResponse<Array<LogEntryResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log`,
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
  getLogEntries(stateId: string): Observable<Array<LogEntryResource>> {
    return this.getLogEntriesResponse(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  mutateResponse(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<LogEntryResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/log`,
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
  mutate(stateId: string,
    body?: StateMutationForm): Observable<LogEntryResource> {
    return this.mutateResponse(stateId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  mutationsResponse(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<Array<StateMutationForm>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/log/mutations`,
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
  mutations(stateId: string,
    body?: StateMutationForm): Observable<Array<StateMutationForm>> {
    return this.mutationsResponse(stateId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta21Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/meta`,
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
  getMeta21(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta21Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getParts1Response(stateId: string): Observable<StrictHttpResponse<Array<PartResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts`,
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
  getParts1(stateId: string): Observable<Array<PartResource>> {
    return this.getParts1Response(stateId).pipe(
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
  getPlaceResponse(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<PlaceResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;




    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}`,
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
  getPlace(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<PlaceResource> {
    return this.getPlaceResponse(stateId, pluginUri, placeTypeLocalName, partId).pipe(
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
  getMeta22Response(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;




    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
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
  getMeta22(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta22Response(stateId, pluginUri, placeTypeLocalName, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlacesResponse(stateId: string,
    partId: string): Observable<StrictHttpResponse<Array<PlaceResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places`,
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
  getPlaces(stateId: string,
    partId: string): Observable<Array<PlaceResource>> {
    return this.getPlacesResponse(stateId, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta23Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/meta`,
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
  getMeta23(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta23Response(stateId, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta24Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/meta`,
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
  getMeta24(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta24Response(stateId, partId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta25Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/meta`,
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
  getMeta25(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta25Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getRunnerResponse(stateId: string): Observable<StrictHttpResponse<RunnerResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r: HttpResponse<any>) => {
        return _r as StrictHttpResponse<RunnerResource>;
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getRunner(stateId: string): Observable<RunnerResource> {
    return this.getRunnerResponse(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getParticipantsResponse(stateId: string): Observable<StrictHttpResponse<Array<ParticipantResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/participants`,
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
  getParticipants(stateId: string): Observable<Array<ParticipantResource>> {
    return this.getParticipantsResponse(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @param body undefined
   * @return successful operation
   */
  createParticipantResponse(stateId: string,
    body?: ParticipantCreationForm): Observable<StrictHttpResponse<ParticipantResource>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/participants`,
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
  createParticipant(stateId: string,
    body?: ParticipantCreationForm): Observable<ParticipantResource> {
    return this.createParticipantResponse(stateId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta26Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/participants/meta`,
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
  getMeta26(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta26Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getRelationshipsResponse(stateId: string): Observable<StrictHttpResponse<Array<RelationshipResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/relationships`,
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
  getRelationships(stateId: string): Observable<Array<RelationshipResource>> {
    return this.getRelationshipsResponse(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta27Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/relationships/meta`,
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
  getMeta27(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta27Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getOperationPatternSetsResponse(stateId: string): Observable<StrictHttpResponse<Array<OperationPatternSetResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/allowedOperations`,
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
  getOperationPatternSets(stateId: string): Observable<Array<OperationPatternSetResource>> {
    return this.getOperationPatternSetsResponse(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta28Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/allowedOperations/meta`,
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
  getMeta28(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta28Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
  getMeta29Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/meta`,
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
  getMeta29(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta29Response(stateId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @return successful operation
   */
  getMeta30Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/meta`,
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
  getMeta30(): Observable<ResourceMetaData> {
    return this.getMeta30Response().pipe(
      __map(_r => _r.body)
    );
  }
}

module RunnersService {
}

export { RunnersService }
