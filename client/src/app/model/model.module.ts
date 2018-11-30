import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModelsComponent } from './models/models.component';
import { ModelRoutingModule } from './model-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatTableModule,
  MatProgressSpinnerModule,
  MatExpansionModule,
  MatBadgeModule,
  MatChipsModule,
  MatDividerModule
  } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { ModelComponent } from './model/model.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    ModelRoutingModule,
    MatButtonModule,
    MatCheckboxModule,
    MatTableModule,
    HttpClientModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatBadgeModule,
    MatChipsModule,
    MatDividerModule
  ],
  declarations: [
    ModelsComponent,
    ModelComponent
  ],
  bootstrap: [
    ModelsComponent
  ]
})
export class ModelModule { }
