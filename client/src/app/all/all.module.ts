import { NgModule } from '@angular/core';
import { AuthGuard } from './auth-guard.service';

import { HttpClientModule } from '@angular/common/http';

@NgModule({
  providers: [
    AuthGuard
  ],
  imports: [
    HttpClientModule
  ],
  declarations: [
  ]
})
export class AllModule { }
