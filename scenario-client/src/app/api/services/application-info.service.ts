/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { AppInfo } from '../models/app-info';
@Injectable({
  providedIn: 'root',
})
class ApplicationInfoService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * @return successful operation
   */
  getInfoResponse(): Observable<StrictHttpResponse<AppInfo>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/app`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as StrictHttpResponse<AppInfo>;
      })
    );
  }
  /**
   * @return successful operation
   */
  getInfo(): Observable<AppInfo> {
    return this.getInfoResponse().pipe(
      __map(_r => _r.body)
    );
  }
}

module ApplicationInfoService {
}

export { ApplicationInfoService }
