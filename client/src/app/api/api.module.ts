/* tslint:disable */
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ApiConfiguration } from './api-configuration';

import { ModelsService } from './services/models.service';
import { RunnersService } from './services/runners.service';
import { StatesService } from './services/states.service';

/**
 * Provider for all Api services, plus ApiConfiguration
 */
@NgModule({
  imports: [
    HttpClientModule
  ],
  exports: [
    HttpClientModule
  ],
  declarations: [],
  providers: [
    ApiConfiguration,
    ModelsService,
    RunnersService,
    StatesService
  ],
})
export class ApiModule { }
