import { Component, OnInit } from '@angular/core';
import { ModelsDataSource } from 'src/app/model/models-data-source';
import { ModelsService, ScenariosService } from 'src/app/api/services';
import { Model } from 'src/app/api/models';
import { Scenario } from 'src/app/api/models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-models',
  templateUrl: './models.component.html',
  styleUrls: ['./models.component.css']
})
export class ModelsComponent implements OnInit {
  modelDataSource: ModelsDataSource;
  modelColumnsToDisplay = [ 'url', 'action' ] ;

  constructor(
    private readonly modelsService: ModelsService,
    private readonly router: Router,
    private readonly scenariosService: ScenariosService
    ) { }

  public ngOnInit(): void {
    this.modelDataSource = new ModelsDataSource(this.modelsService) ;
    this.modelDataSource.loadModels() ;
  }

  createScenarioFor( model: Model ) {
    const scenario = <Scenario> {
      model: model.url,
      description: 'Scenario for ' + model.url
    };
    this.scenariosService
      .putScenario( scenario )
      .subscribe( s => this.router.navigate( [ '/scenario', s.id ] ))
      ;
  }

}
