import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AllModule } from './all/all.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
<<<<<<< HEAD
import { MatTabsModule, MatBadgeModule, MatSnackBarModule } from '@angular/material';
=======
import { MatTabsModule } from '@angular/material';
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AllModule,
<<<<<<< HEAD
    MatTabsModule,
    MatBadgeModule,
    MatSnackBarModule
=======
    MatTabsModule
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  ],
  providers: [
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
