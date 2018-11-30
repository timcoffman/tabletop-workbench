/* tslint:disable */
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ApiConfiguration } from './api-configuration';

<<<<<<< HEAD
import { ApplicationInfoService } from './services/application-info.service';
=======
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
    ApplicationInfoService,
=======
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    ModelsService,
    RunnersService,
    StatesService
  ],
})
export class ApiModule { }
