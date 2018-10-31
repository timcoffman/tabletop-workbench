import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  navLinks = [
    { path: '/home', label: 'Home', image: 'three-hexagons.png' },
    { path: '/model', label: 'Models' },
    { path: '/state', label: 'States' },
    { path: '/runner', label: 'Runners' }
  ] ;

  constructor ( ) {
  }

  public ngOnInit(): void {
  }


}
