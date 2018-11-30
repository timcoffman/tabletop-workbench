/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { Scenario } from '../models/scenario';
@Injectable({
  providedIn: 'root',
})
class ScenariosService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * @return successful operation
   */
  getScenariosResponse(): Observable<StrictHttpResponse<Array<Scenario>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/scenario`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as StrictHttpResponse<Array<Scenario>>;
      })
    );
  }
  /**
   * @return successful operation
   */
  getScenarios(): Observable<Array<Scenario>> {
    return this.getScenariosResponse().pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param body undefined
   * @return successful operation
   */
  putScenarioResponse(body?: Scenario): Observable<StrictHttpResponse<Scenario>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/scenario`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as StrictHttpResponse<Scenario>;
      })
    );
  }
  /**
   * @param body undefined
   * @return successful operation
   */
  putScenario(body?: Scenario): Observable<Scenario> {
    return this.putScenarioResponse(body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param scenarioId undefined
   * @return successful operation
   */
  getScenarioResponse(scenarioId: number): Observable<StrictHttpResponse<Scenario>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/scenario/${scenarioId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as StrictHttpResponse<Scenario>;
      })
    );
  }
  /**
   * @param scenarioId undefined
   * @return successful operation
   */
  getScenario(scenarioId: number): Observable<Scenario> {
    return this.getScenarioResponse(scenarioId).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param scenarioId undefined
   * @param body undefined
   * @return successful operation
   */
  postScenarioResponse(scenarioId: number,
    body?: Scenario): Observable<StrictHttpResponse<Scenario>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/scenario/${scenarioId}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as StrictHttpResponse<Scenario>;
      })
    );
  }
  /**
   * @param scenarioId undefined
   * @param body undefined
   * @return successful operation
   */
  postScenario(scenarioId: number,
    body?: Scenario): Observable<Scenario> {
    return this.postScenarioResponse(scenarioId, body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @param scenarioId undefined
   */
  runScenarioResponse(scenarioId: number): Observable<StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/scenario/${scenarioId}/run`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as StrictHttpResponse<null>;
      })
    );
  }
  /**
   * @param scenarioId undefined
   */
  runScenario(scenarioId: number): Observable<null> {
    return this.runScenarioResponse(scenarioId).pipe(
      __map(_r => _r.body)
    );
  }
}

module ScenariosService {
}

export { ScenariosService }
