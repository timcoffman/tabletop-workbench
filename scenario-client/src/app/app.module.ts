import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { AllModule } from './all/all.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatTabsModule,
  MatBadgeModule,
  MatSnackBarModule,
  MAT_LABEL_GLOBAL_OPTIONS,
  MatIconModule,
  MatButtonModule
  } from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AllModule,
    MatTabsModule,
    MatBadgeModule,
    MatSnackBarModule,
    MatIconModule,
    MatButtonModule
  ],
  providers: [
    {provide: MAT_LABEL_GLOBAL_OPTIONS, useValue: {float: 'auto', hideRequiredMarker: 'false'}}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
