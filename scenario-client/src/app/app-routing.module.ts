import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './all/auth-guard.service';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [

  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomeModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'model',
    loadChildren: './model/model.module#ModelModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'scenario',
    loadChildren: './scenario/scenario.module#ScenarioModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'report',
    loadChildren: './report/report.module#ReportModule',
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
