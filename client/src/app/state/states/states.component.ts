import { Component, OnInit } from '@angular/core';
import { StatesDataSource } from '../states-data-source';
import { StatesService } from 'src/app/api/services';

@Component({
  selector: 'app-states',
  templateUrl: './states.component.html',
  styleUrls: ['./states.component.css']
})
export class StatesComponent implements OnInit {
  stateDataSource: StatesDataSource;
  stateColumnsToDisplay = [ 'name', 'url' ] ;

  constructor( private readonly statesService: StatesService ) { }

  ngOnInit() {
    this.stateDataSource = new StatesDataSource(this.statesService) ;
    this.stateDataSource.loadStates() ;
  }

  idFromUrl( url: string ): string {
    const parsedUrl = new URL(url) ;
    return parsedUrl.pathname.split('/').pop();
  }
}
