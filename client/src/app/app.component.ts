import { Component, OnInit } from '@angular/core';
<<<<<<< HEAD
import { Observable, interval, Subject, BehaviorSubject } from 'rxjs';
import { AppInfo } from './api/models';
import { ApplicationInfoService } from './api/services';
import { map, distinctUntilChanged } from 'rxjs/operators';
import { MatSnackBarRef, MatSnackBar, SimpleSnackBar } from '@angular/material';
=======
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  navLinks = [
<<<<<<< HEAD
    { path: '/home', label: 'Home', image: 'three-hexagons.png'},
=======
    { path: '/home', label: 'Home', image: 'three-hexagons.png' },
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
    { path: '/model', label: 'Models' },
    { path: '/state', label: 'States' },
    { path: '/runner', label: 'Runners' }
  ] ;

<<<<<<< HEAD
  appInfo = new BehaviorSubject<AppInfo>( null ) ;

  connectivitySnackBarRef: MatSnackBarRef<SimpleSnackBar> ;

  constructor (
    public snackBar: MatSnackBar,
    private readonly appService: ApplicationInfoService
    ) {

    interval(5000).subscribe( (x) => {
      this.appService.getInfo().subscribe(
        appInfo => this.appInfo.next(appInfo),
        error => this.appInfo.next(null)
      );
    });

    this.appInfo
      .pipe( distinctUntilChanged( AppComponent.compareAppInfo ) )
      .subscribe(
        appInfo => {
          if ( appInfo ) {
            if ( this.connectivitySnackBarRef ) {
              this.connectivitySnackBarRef.dismiss() ;
              this.connectivitySnackBarRef = null ;
            }
            this.snackBar.open( 'connected to server (' + appInfo.version + ')', 'Thanks', { duration: 3000 } ) ;
          } else {
            if ( !this.connectivitySnackBarRef ) {
              this.connectivitySnackBarRef = this.snackBar.open( 'disconnected from server', null, { verticalPosition: 'top' } ) ;
            }
          }
        }
      );
  }

  private static compareAppInfo( a: AppInfo, b: AppInfo): boolean {
    if ( a === b ) {
      return true ;
    }
    if ( !a || !b ) {
      return false ;
    }
    return a.version === b.version ;
=======
  constructor ( ) {
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  }

  public ngOnInit(): void {
  }

<<<<<<< HEAD
=======

>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
}
