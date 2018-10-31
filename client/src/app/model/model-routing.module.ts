import { NgModule } from '@angular/core';
import { ModelsComponent } from './models/models.component';
import { Routes, RouterModule } from '@angular/router';
import { ModelComponent } from './model/model.component';

const routes: Routes = [
  {
    path: '',
    component: ModelsComponent
  },
  {
    path: ':modelId',
    component: ModelComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class ModelRoutingModule { }
