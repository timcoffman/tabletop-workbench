/* tslint:disable */
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ApiConfiguration } from './api-configuration';

import { ApplicationInfoService } from './services/application-info.service';
import { ScenarioEngineService } from './services/scenario-engine.service';
import { ModelsService } from './services/models.service';
import { ScenariosService } from './services/scenarios.service';

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
    ApplicationInfoService,
    ScenarioEngineService,
    ModelsService,
    ScenariosService
  ],
})
export class ApiModule { }
