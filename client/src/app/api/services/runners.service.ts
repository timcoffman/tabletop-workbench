/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { RunnerResource } from '../models/runner-resource';
<<<<<<< HEAD
import { PartResource } from '../models/part-resource';
import { PlaceResource } from '../models/place-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { LogEntryResource } from '../models/log-entry-resource';
import { StateMutationForm } from '../models/state-mutation-form';
=======
import { RunnerCreationForm } from '../models/runner-creation-form';
import { LogEntryResource } from '../models/log-entry-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { StateMutationForm } from '../models/state-mutation-form';
import { PartResource } from '../models/part-resource';
import { PlaceResource } from '../models/place-resource';
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
import { ParticipantResource } from '../models/participant-resource';
import { ParticipantCreationForm } from '../models/participant-creation-form';
import { RelationshipResource } from '../models/relationship-resource';
import { OperationPatternSetResource } from '../models/operation-pattern-set-resource';
<<<<<<< HEAD
import { RunnerCreationForm } from '../models/runner-creation-form';
=======
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
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
=======
   * @return successful operation
   */
  getRunnersResponse(): Observable<StrictHttpResponse<Array<RunnerResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners`,
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
        return _r as StrictHttpResponse<RunnerResource>;
=======
        return _r as StrictHttpResponse<Array<RunnerResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param stateId undefined
   * @return successful operation
   */
  getRunner(stateId: string): Observable<RunnerResource> {
    return this.getRunnerResponse(stateId).pipe(
=======
   * @return successful operation
   */
  getRunners(): Observable<Array<RunnerResource>> {
    return this.getRunnersResponse().pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
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
=======
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
        return _r as StrictHttpResponse<Array<PartResource>>;
=======
        return _r as StrictHttpResponse<RunnerResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param stateId undefined
   * @return successful operation
   */
  getParts1(stateId: string): Observable<Array<PartResource>> {
    return this.getParts1Response(stateId).pipe(
=======
   * @param body undefined
   * @return successful operation
   */
  createRunner(body?: RunnerCreationForm): Observable<RunnerResource> {
    return this.createRunnerResponse(body).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlaceResponse(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<PlaceResource>> {
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntryResponse(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<LogEntryResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


<<<<<<< HEAD


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/${logEntryIndex}`,
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
        return _r as StrictHttpResponse<PlaceResource>;
=======
        return _r as StrictHttpResponse<LogEntryResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
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
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntry(stateId: string,
    logEntryIndex: number): Observable<LogEntryResource> {
    return this.getLogEntryResponse(stateId, logEntryIndex).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta21Response(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta20Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


<<<<<<< HEAD


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/${logEntryIndex}/meta`,
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
   * @param stateId undefined
<<<<<<< HEAD
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta21(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta21Response(stateId, pluginUri, placeTypeLocalName, partId).pipe(
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta20(stateId: string,
    logEntryIndex: number): Observable<ResourceMetaData> {
    return this.getMeta20Response(stateId, logEntryIndex).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param partId undefined
   * @return successful operation
   */
  getPlacesResponse(stateId: string,
    partId: string): Observable<StrictHttpResponse<Array<PlaceResource>>> {
=======
   * @return successful operation
   */
  getLogEntriesResponse(stateId: string): Observable<StrictHttpResponse<Array<LogEntryResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log`,
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
        return _r as StrictHttpResponse<Array<PlaceResource>>;
=======
        return _r as StrictHttpResponse<Array<LogEntryResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param partId undefined
   * @return successful operation
   */
  getPlaces(stateId: string,
    partId: string): Observable<Array<PlaceResource>> {
    return this.getPlacesResponse(stateId, partId).pipe(
=======
   * @return successful operation
   */
  getLogEntries(stateId: string): Observable<Array<LogEntryResource>> {
    return this.getLogEntriesResponse(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param partId undefined
   * @return successful operation
   */
  getMeta22Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param body undefined
   * @return successful operation
   */
  mutateResponse(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<LogEntryResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/meta`,
=======
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/log`,
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
        return _r as StrictHttpResponse<LogEntryResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param partId undefined
   * @return successful operation
   */
  getMeta22(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta22Response(stateId, partId).pipe(
=======
   * @param body undefined
   * @return successful operation
   */
  mutate(stateId: string,
    body?: StateMutationForm): Observable<LogEntryResource> {
    return this.mutateResponse(stateId, body).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param partId undefined
   * @return successful operation
   */
  getMeta23Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param body undefined
   * @return successful operation
   */
  mutationsResponse(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<Array<StateMutationForm>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/meta`,
=======
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/log/mutations`,
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
        return _r as StrictHttpResponse<Array<StateMutationForm>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param partId undefined
   * @return successful operation
   */
  getMeta23(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta23Response(stateId, partId).pipe(
=======
   * @param body undefined
   * @return successful operation
   */
  mutations(stateId: string,
    body?: StateMutationForm): Observable<Array<StateMutationForm>> {
    return this.mutationsResponse(stateId, body).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta24Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta21Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/runners/${stateId}/parts/meta`,
=======
      this.rootUrl + `/runners/${stateId}/log/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta24(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta24Response(stateId).pipe(
=======
  getMeta21(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta21Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntryResponse(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<LogEntryResource>> {
=======
   * @return successful operation
   */
  getParts1Response(stateId: string): Observable<StrictHttpResponse<Array<PartResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/${logEntryIndex}`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts`,
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
        return _r as StrictHttpResponse<LogEntryResource>;
=======
        return _r as StrictHttpResponse<Array<PartResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntry(stateId: string,
    logEntryIndex: number): Observable<LogEntryResource> {
    return this.getLogEntryResponse(stateId, logEntryIndex).pipe(
=======
   * @return successful operation
   */
  getParts1(stateId: string): Observable<Array<PartResource>> {
    return this.getParts1Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta25Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlaceResponse(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<PlaceResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/${logEntryIndex}/meta`,
=======


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}`,
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
        return _r as StrictHttpResponse<PlaceResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta25(stateId: string,
    logEntryIndex: number): Observable<ResourceMetaData> {
    return this.getMeta25Response(stateId, logEntryIndex).pipe(
=======
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
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getLogEntriesResponse(stateId: string): Observable<StrictHttpResponse<Array<LogEntryResource>>> {
=======
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta22Response(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log`,
=======



    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
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
        return _r as StrictHttpResponse<Array<LogEntryResource>>;
=======
        return _r as StrictHttpResponse<ResourceMetaData>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getLogEntries(stateId: string): Observable<Array<LogEntryResource>> {
    return this.getLogEntriesResponse(stateId).pipe(
=======
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
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param body undefined
   * @return successful operation
   */
  mutateResponse(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<LogEntryResource>> {
=======
   * @param partId undefined
   * @return successful operation
   */
  getPlacesResponse(stateId: string,
    partId: string): Observable<StrictHttpResponse<Array<PlaceResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/log`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places`,
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
        return _r as StrictHttpResponse<LogEntryResource>;
=======
        return _r as StrictHttpResponse<Array<PlaceResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param body undefined
   * @return successful operation
   */
  mutate(stateId: string,
    body?: StateMutationForm): Observable<LogEntryResource> {
    return this.mutateResponse(stateId, body).pipe(
=======
   * @param partId undefined
   * @return successful operation
   */
  getPlaces(stateId: string,
    partId: string): Observable<Array<PlaceResource>> {
    return this.getPlacesResponse(stateId, partId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param body undefined
   * @return successful operation
   */
  mutationsResponse(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<Array<StateMutationForm>>> {
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta23Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/log/mutations`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/places/meta`,
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
        return _r as StrictHttpResponse<Array<StateMutationForm>>;
=======
        return _r as StrictHttpResponse<ResourceMetaData>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param body undefined
   * @return successful operation
   */
  mutations(stateId: string,
    body?: StateMutationForm): Observable<Array<StateMutationForm>> {
    return this.mutationsResponse(stateId, body).pipe(
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta23(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta23Response(stateId, partId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getMeta26Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta24Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/log/meta`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/parts/${partId}/meta`,
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
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getMeta26(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta26Response(stateId).pipe(
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta24(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta24Response(stateId, partId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getParticipantsResponse(stateId: string): Observable<StrictHttpResponse<Array<ParticipantResource>>> {
=======
  getMeta25Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/runners/${stateId}/participants`,
=======
      this.rootUrl + `/runners/${stateId}/parts/meta`,
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
        return _r as StrictHttpResponse<Array<ParticipantResource>>;
=======
        return _r as StrictHttpResponse<ResourceMetaData>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getParticipants(stateId: string): Observable<Array<ParticipantResource>> {
    return this.getParticipantsResponse(stateId).pipe(
=======
  getMeta25(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta25Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param body undefined
   * @return successful operation
   */
  createParticipantResponse(stateId: string,
    body?: ParticipantCreationForm): Observable<StrictHttpResponse<ParticipantResource>> {
=======
   * @return successful operation
   */
  getRunnerResponse(stateId: string): Observable<StrictHttpResponse<RunnerResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/participants`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}`,
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
        return _r as StrictHttpResponse<ParticipantResource>;
=======
        return _r as StrictHttpResponse<RunnerResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @param body undefined
   * @return successful operation
   */
  createParticipant(stateId: string,
    body?: ParticipantCreationForm): Observable<ParticipantResource> {
    return this.createParticipantResponse(stateId, body).pipe(
=======
   * @return successful operation
   */
  getRunner(stateId: string): Observable<RunnerResource> {
    return this.getRunnerResponse(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta27Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getParticipantsResponse(stateId: string): Observable<StrictHttpResponse<Array<ParticipantResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/runners/${stateId}/participants/meta`,
=======
      this.rootUrl + `/runners/${stateId}/participants`,
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
        return _r as StrictHttpResponse<Array<ParticipantResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta27(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta27Response(stateId).pipe(
=======
  getParticipants(stateId: string): Observable<Array<ParticipantResource>> {
    return this.getParticipantsResponse(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getRelationshipsResponse(stateId: string): Observable<StrictHttpResponse<Array<RelationshipResource>>> {
=======
   * @param body undefined
   * @return successful operation
   */
  createParticipantResponse(stateId: string,
    body?: ParticipantCreationForm): Observable<StrictHttpResponse<ParticipantResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners/${stateId}/relationships`,
=======
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/runners/${stateId}/participants`,
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
        return _r as StrictHttpResponse<Array<RelationshipResource>>;
=======
        return _r as StrictHttpResponse<ParticipantResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getRelationships(stateId: string): Observable<Array<RelationshipResource>> {
    return this.getRelationshipsResponse(stateId).pipe(
=======
   * @param body undefined
   * @return successful operation
   */
  createParticipant(stateId: string,
    body?: ParticipantCreationForm): Observable<ParticipantResource> {
    return this.createParticipantResponse(stateId, body).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta28Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta26Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/runners/${stateId}/relationships/meta`,
=======
      this.rootUrl + `/runners/${stateId}/participants/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta28(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta28Response(stateId).pipe(
=======
  getMeta26(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta26Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getOperationPatternSetsResponse(stateId: string): Observable<StrictHttpResponse<Array<OperationPatternSetResource>>> {
=======
  getRelationshipsResponse(stateId: string): Observable<StrictHttpResponse<Array<RelationshipResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/runners/${stateId}/allowedOperations`,
=======
      this.rootUrl + `/runners/${stateId}/relationships`,
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
        return _r as StrictHttpResponse<Array<OperationPatternSetResource>>;
=======
        return _r as StrictHttpResponse<Array<RelationshipResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getOperationPatternSets(stateId: string): Observable<Array<OperationPatternSetResource>> {
    return this.getOperationPatternSetsResponse(stateId).pipe(
=======
  getRelationships(stateId: string): Observable<Array<RelationshipResource>> {
    return this.getRelationshipsResponse(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta29Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta27Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/runners/${stateId}/allowedOperations/meta`,
=======
      this.rootUrl + `/runners/${stateId}/relationships/meta`,
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
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta29(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta29Response(stateId).pipe(
=======
  getMeta27(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta27Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta30Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getOperationPatternSetsResponse(stateId: string): Observable<StrictHttpResponse<Array<OperationPatternSetResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/runners/${stateId}/meta`,
=======
      this.rootUrl + `/runners/${stateId}/allowedOperations`,
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
        return _r as StrictHttpResponse<Array<OperationPatternSetResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta30(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta30Response(stateId).pipe(
=======
  getOperationPatternSets(stateId: string): Observable<Array<OperationPatternSetResource>> {
    return this.getOperationPatternSetsResponse(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
   * @return successful operation
   */
  getRunnersResponse(): Observable<StrictHttpResponse<Array<RunnerResource>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/runners`,
=======
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
        return _r as StrictHttpResponse<Array<RunnerResource>>;
=======
        return _r as StrictHttpResponse<ResourceMetaData>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @return successful operation
   */
  getRunners(): Observable<Array<RunnerResource>> {
    return this.getRunnersResponse().pipe(
=======
   * @param stateId undefined
   * @return successful operation
   */
  getMeta28(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta28Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
<<<<<<< HEAD
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
=======
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
        return _r as StrictHttpResponse<RunnerResource>;
=======
        return _r as StrictHttpResponse<ResourceMetaData>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
<<<<<<< HEAD
   * @param body undefined
   * @return successful operation
   */
  createRunner(body?: RunnerCreationForm): Observable<RunnerResource> {
    return this.createRunnerResponse(body).pipe(
=======
   * @param stateId undefined
   * @return successful operation
   */
  getMeta29(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta29Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta31Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta30Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta31(): Observable<ResourceMetaData> {
    return this.getMeta31Response().pipe(
=======
  getMeta30(): Observable<ResourceMetaData> {
    return this.getMeta30Response().pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }
}

module RunnersService {
}

export { RunnersService }
