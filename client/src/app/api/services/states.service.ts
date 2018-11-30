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
<<<<<<< HEAD
import { PartResource } from '../models/part-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { LogEntryResource } from '../models/log-entry-resource';
import { StateMutationForm } from '../models/state-mutation-form';
=======
import { LogEntryResource } from '../models/log-entry-resource';
import { ResourceMetaData } from '../models/resource-meta-data';
import { StateMutationForm } from '../models/state-mutation-form';
import { PartResource } from '../models/part-resource';
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
   * @return successful operation
   */
  getParts2Response(stateId: string): Observable<StrictHttpResponse<Array<PartResource>>> {
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntry1Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<LogEntryResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log/${logEntryIndex}`,
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
        return _r as StrictHttpResponse<LogEntryResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getParts2(stateId: string): Observable<Array<PartResource>> {
    return this.getParts2Response(stateId).pipe(
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getLogEntry1(stateId: string,
    logEntryIndex: number): Observable<LogEntryResource> {
    return this.getLogEntry1Response(stateId, logEntryIndex).pipe(
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
  getPlace1Response(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<PlaceResource>> {
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta31Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;


<<<<<<< HEAD


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log/${logEntryIndex}/meta`,
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
        return _r as StrictHttpResponse<ResourceMetaData>;
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
  getPlace1(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<PlaceResource> {
    return this.getPlace1Response(stateId, pluginUri, placeTypeLocalName, partId).pipe(
=======
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta31(stateId: string,
    logEntryIndex: number): Observable<ResourceMetaData> {
    return this.getMeta31Response(stateId, logEntryIndex).pipe(
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
  getMeta32Response(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @return successful operation
   */
  getLogEntries1Response(stateId: string): Observable<StrictHttpResponse<Array<LogEntryResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD



    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log`,
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
        return _r as StrictHttpResponse<Array<LogEntryResource>>;
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
  getMeta32(stateId: string,
    pluginUri: string,
    placeTypeLocalName: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta32Response(stateId, pluginUri, placeTypeLocalName, partId).pipe(
=======
   * @return successful operation
   */
  getLogEntries1(stateId: string): Observable<Array<LogEntryResource>> {
    return this.getLogEntries1Response(stateId).pipe(
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
  getPlaces1Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<Array<PlaceResource>>> {
=======
   * @param body undefined
   * @return successful operation
   */
  mutate1Response(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<LogEntryResource>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places`,
=======
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/log`,
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
  getPlaces1(stateId: string,
    partId: string): Observable<Array<PlaceResource>> {
    return this.getPlaces1Response(stateId, partId).pipe(
=======
   * @param body undefined
   * @return successful operation
   */
  mutate1(stateId: string,
    body?: StateMutationForm): Observable<LogEntryResource> {
    return this.mutate1Response(stateId, body).pipe(
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
  getMeta33Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param body undefined
   * @return successful operation
   */
  mutations1Response(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<Array<StateMutationForm>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/meta`,
=======
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/log/mutations`,
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
  getMeta33(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta33Response(stateId, partId).pipe(
=======
   * @param body undefined
   * @return successful operation
   */
  mutations1(stateId: string,
    body?: StateMutationForm): Observable<Array<StateMutationForm>> {
    return this.mutations1Response(stateId, body).pipe(
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
  getMeta34Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @return successful operation
   */
  getMeta32Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/meta`,
=======
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log/meta`,
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
   * @param partId undefined
   * @return successful operation
   */
  getMeta34(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta34Response(stateId, partId).pipe(
=======
   * @return successful operation
   */
  getMeta32(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta32Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta35Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getParts2Response(stateId: string): Observable<StrictHttpResponse<Array<PartResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/states/${stateId}/parts/meta`,
=======
      this.rootUrl + `/states/${stateId}/parts`,
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
        return _r as StrictHttpResponse<Array<PartResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta35(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta35Response(stateId).pipe(
=======
  getParts2(stateId: string): Observable<Array<PartResource>> {
    return this.getParts2Response(stateId).pipe(
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
  getLogEntry1Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<LogEntryResource>> {
=======
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getPlace1Response(stateId: string,
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
      this.rootUrl + `/states/${stateId}/log/${logEntryIndex}`,
=======


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}`,
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
  getLogEntry1(stateId: string,
    logEntryIndex: number): Observable<LogEntryResource> {
    return this.getLogEntry1Response(stateId, logEntryIndex).pipe(
=======
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
  getMeta36Response(stateId: string,
    logEntryIndex: number): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
   * @param pluginUri undefined
   * @param placeTypeLocalName undefined
   * @param partId undefined
   * @return successful operation
   */
  getMeta33Response(stateId: string,
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
      this.rootUrl + `/states/${stateId}/log/${logEntryIndex}/meta`,
=======


    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/${pluginUri}/${placeTypeLocalName}/meta`,
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
   * @param logEntryIndex undefined
   * @return successful operation
   */
  getMeta36(stateId: string,
    logEntryIndex: number): Observable<ResourceMetaData> {
    return this.getMeta36Response(stateId, logEntryIndex).pipe(
=======
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
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getLogEntries1Response(stateId: string): Observable<StrictHttpResponse<Array<LogEntryResource>>> {
=======
   * @param partId undefined
   * @return successful operation
   */
  getPlaces1Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<Array<PlaceResource>>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/log`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places`,
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
        return _r as StrictHttpResponse<Array<PlaceResource>>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      })
    );
  }

  /**
   * @param stateId undefined
<<<<<<< HEAD
   * @return successful operation
   */
  getLogEntries1(stateId: string): Observable<Array<LogEntryResource>> {
    return this.getLogEntries1Response(stateId).pipe(
=======
   * @param partId undefined
   * @return successful operation
   */
  getPlaces1(stateId: string,
    partId: string): Observable<Array<PlaceResource>> {
    return this.getPlaces1Response(stateId, partId).pipe(
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
  mutate1Response(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<LogEntryResource>> {
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta34Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/log`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/places/meta`,
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
  mutate1(stateId: string,
    body?: StateMutationForm): Observable<LogEntryResource> {
    return this.mutate1Response(stateId, body).pipe(
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta34(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta34Response(stateId, partId).pipe(
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
  mutations1Response(stateId: string,
    body?: StateMutationForm): Observable<StrictHttpResponse<Array<StateMutationForm>>> {
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta35Response(stateId: string,
    partId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

<<<<<<< HEAD
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/states/${stateId}/log/mutations`,
=======

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/states/${stateId}/parts/${partId}/meta`,
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
  mutations1(stateId: string,
    body?: StateMutationForm): Observable<Array<StateMutationForm>> {
    return this.mutations1Response(stateId, body).pipe(
=======
   * @param partId undefined
   * @return successful operation
   */
  getMeta35(stateId: string,
    partId: string): Observable<ResourceMetaData> {
    return this.getMeta35Response(stateId, partId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta37Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta36Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
<<<<<<< HEAD
      this.rootUrl + `/states/${stateId}/log/meta`,
=======
      this.rootUrl + `/states/${stateId}/parts/meta`,
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
  getMeta37(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta37Response(stateId).pipe(
=======
  getMeta36(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta36Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta38Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta37Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta38(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta38Response(stateId).pipe(
=======
  getMeta37(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta37Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta39Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta38Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta39(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta39Response(stateId).pipe(
=======
  getMeta38(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta38Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta40Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta39Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta40(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta40Response(stateId).pipe(
=======
  getMeta39(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta39Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }

  /**
   * @param stateId undefined
   * @return successful operation
   */
<<<<<<< HEAD
  getMeta41Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta40Response(stateId: string): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta41(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta41Response(stateId).pipe(
=======
  getMeta40(stateId: string): Observable<ResourceMetaData> {
    return this.getMeta40Response(stateId).pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta42Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
=======
  getMeta41Response(): Observable<StrictHttpResponse<ResourceMetaData>> {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
  getMeta42(): Observable<ResourceMetaData> {
    return this.getMeta42Response().pipe(
=======
  getMeta41(): Observable<ResourceMetaData> {
    return this.getMeta41Response().pipe(
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
      __map(_r => _r.body)
    );
  }
}

module StatesService {
}

export { StatesService }
