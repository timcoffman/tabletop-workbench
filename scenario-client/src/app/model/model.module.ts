import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModelsComponent } from './models/models.component';
import { ModelRoutingModule } from './model-routing.module';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatTableModule,
  MatProgressSpinnerModule,
  MatExpansionModule,
  MatBadgeModule,
  MatChipsModule,
  MatDividerModule,
  MatIconModule
  } from '@angular/material';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [ModelsComponent],
  imports: [
    CommonModule,
    RouterModule,
    ModelRoutingModule,
    MatButtonModule,
    MatCheckboxModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatBadgeModule,
    MatChipsModule,
    MatDividerModule,
    MatIconModule
  ]
})
export class ModelModule { }
