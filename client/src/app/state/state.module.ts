import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatesComponent } from './states/states.component';
import { StateRoutingModule } from './state-routing.module';
<<<<<<< HEAD
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
=======
import { MatButtonModule, MatTableModule, MatCheckboxModule, MatProgressSpinnerModule } from '@angular/material';
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatBadgeModule,
    MatChipsModule,
    MatDividerModule,
    NgxChartsModule,
    NgxGraphModule
=======
    MatProgressSpinnerModule
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
