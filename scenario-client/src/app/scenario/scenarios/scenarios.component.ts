import { Component, OnInit } from '@angular/core';
import { ScenariosService } from 'src/app/api/services';
import { ScenariosDataSource } from '../scenarios-data-source';
import { Scenario } from 'src/app/api/models';

@Component({
  selector: 'app-scenarios',
  templateUrl: './scenarios.component.html',
  styleUrls: ['./scenarios.component.css']
})
export class ScenariosComponent implements OnInit {
  scenarioDataSource: ScenariosDataSource;
  scenarioColumnsToDisplay = [ 'id', 'model', 'description', 'action', 'action2' ] ;

  constructor( private readonly scenariosService: ScenariosService ) { }

  ngOnInit() {
    this.scenarioDataSource = new ScenariosDataSource(this.scenariosService) ;
    this.scenarioDataSource.loadScenarios() ;
  }

  runScenario( scenario: Scenario ): void {
    this.scenariosService.runScenario(scenario.id).subscribe( r => {} ) ;
  }
}
