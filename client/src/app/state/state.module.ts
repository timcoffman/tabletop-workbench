import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatesComponent } from './states/states.component';
import { StateRoutingModule } from './state-routing.module';
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
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { HttpClientModule } from '@angular/common/http';
import { StateComponent } from './state/state.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    StateRoutingModule,
    MatButtonModule,
    MatCheckboxModule,
    MatTableModule,
    HttpClientModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatBadgeModule,
    MatChipsModule,
    MatDividerModule,
    NgxChartsModule,
    NgxGraphModule
  ],
  declarations: [
    StatesComponent,
    StateComponent
  ],
  bootstrap: [
    StatesComponent
  ]
})
export class StateModule { }
