import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScenariosComponent } from './scenarios/scenarios.component';
import { ScenarioRoutingModule } from './scenario-routing.module';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatTableModule,
  MatProgressSpinnerModule,
  MatExpansionModule,
  MatBadgeModule,
  MatChipsModule,
  MatDividerModule,
  MatIconModule,
  MatFormFieldModule,
  MatInputModule
  } from '@angular/material';
import { RouterModule } from '@angular/router';
import { ScenarioComponent } from './scenario/scenario.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [ScenariosComponent, ScenarioComponent],
  imports: [
    CommonModule,
    RouterModule,
    ScenarioRoutingModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatBadgeModule,
    MatChipsModule,
    MatDividerModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule
  ]
})
export class ScenarioModule { }
