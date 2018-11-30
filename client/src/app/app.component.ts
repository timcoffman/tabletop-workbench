import { Component, OnInit } from '@angular/core';
import { Observable, interval, Subject, BehaviorSubject } from 'rxjs';
import { AppInfo } from './api/models';
import { ApplicationInfoService } from './api/services';
import { map, distinctUntilChanged } from 'rxjs/operators';
import { MatSnackBarRef, MatSnackBar, SimpleSnackBar } from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  navLinks = [
    { path: '/home', label: 'Home', image: 'three-hexagons.png'},
    { path: '/model', label: 'Models' },
    { path: '/state', label: 'States' },
    { path: '/runner', label: 'Runners' }
  ] ;

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
  }

  public ngOnInit(): void {
  }

}
