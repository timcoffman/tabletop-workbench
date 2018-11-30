import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { StatesComponent } from './states/states.component';
import { StateComponent } from './state/state.component';

const routes: Routes = [
  {
    path: '',
    component: StatesComponent
  },
  {
    path: ':stateId',
    component: StateComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class StateRoutingModule { }
