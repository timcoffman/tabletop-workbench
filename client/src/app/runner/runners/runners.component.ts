import { Component, OnInit } from '@angular/core';
import { RunnersDataSource } from '../runners-data-source';
import { RunnersService } from 'src/app/api/services';

@Component({
  selector: 'app-runners',
  templateUrl: './runners.component.html',
  styleUrls: ['./runners.component.css']
})
export class RunnersComponent implements OnInit {
  runnerDataSource: RunnersDataSource;
  runnerColumnsToDisplay = [ 'label', 'resource' ] ;

  constructor( private readonly runnersService: RunnersService ) { }

  ngOnInit() {
    this.runnerDataSource = new RunnersDataSource(this.runnersService) ;
    this.runnerDataSource.loadRunners() ;
  }

  onRowClicked( row: string ): void {
    console.log( row ) ;
  }
}
