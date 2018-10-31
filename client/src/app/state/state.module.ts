import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatesComponent } from './states/states.component';
import { StateRoutingModule } from './state-routing.module';
import { MatButtonModule, MatTableModule, MatCheckboxModule, MatProgressSpinnerModule } from '@angular/material';
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
    MatProgressSpinnerModule
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
