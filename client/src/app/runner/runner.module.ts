import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RunnersComponent } from './runners/runners.component';
import { RunnerRoutingModule } from './runner-routing.module';
import { MatProgressSpinnerModule, MatTableModule, MatCheckboxModule, MatButtonModule } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    RunnerRoutingModule,
    MatButtonModule,
    MatCheckboxModule,
    MatTableModule,
    HttpClientModule,
    MatProgressSpinnerModule
  ],
  declarations: [
    RunnersComponent
  ],
  bootstrap: [
    RunnersComponent
  ]
})
export class RunnerModule { }
