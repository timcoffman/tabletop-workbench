import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RunnersComponent } from './runners/runners.component';

const routes: Routes = [
  {
    path: '',
    component: RunnersComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class RunnerRoutingModule { }
