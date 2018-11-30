import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ScenariosComponent } from './scenarios/scenarios.component';
import { ScenarioComponent } from './scenario/scenario.component';

const routes: Routes = [
  {
    path: '',
    component: ScenariosComponent
  },
  {
    path: ':scenarioId',
    component: ScenarioComponent
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class ScenarioRoutingModule { }
