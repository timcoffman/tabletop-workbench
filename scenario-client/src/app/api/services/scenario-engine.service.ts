/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { EngineInfo } from '../models/engine-info';
@Injectable({
  providedIn: 'root',
})
class ScenarioEngineService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * @param body undefined
   */
  statusResponse(body?: boolean): Observable<StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/engine/status`,
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
   * @param body undefined
   */
  status(body?: boolean): Observable<null> {
    return this.statusResponse(body).pipe(
      __map(_r => _r.body)
    );
  }

  /**
   * @return successful operation
   */
  getInfo1Response(): Observable<StrictHttpResponse<EngineInfo>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/engine`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as StrictHttpResponse<EngineInfo>;
      })
    );
  }
  /**
   * @return successful operation
   */
  getInfo1(): Observable<EngineInfo> {
    return this.getInfo1Response().pipe(
      __map(_r => _r.body)
    );
  }
}

module ScenarioEngineService {
}

export { ScenarioEngineService }
