import { Component, OnInit } from '@angular/core';
import { ModelsDataSource } from 'src/app/model/models-data-source';
import { ModelsService } from 'src/app/api/services';

@Component({
  selector: 'app-models',
  templateUrl: './models.component.html',
  styleUrls: ['./models.component.css']
})
export class ModelsComponent implements OnInit {
  modelDataSource: ModelsDataSource;
  modelColumnsToDisplay = [ 'name', 'url' ] ;

  constructor( private readonly modelsService: ModelsService ) { }

  public ngOnInit(): void {
    this.modelDataSource = new ModelsDataSource(this.modelsService) ;
    this.modelDataSource.loadModels() ;
  }

  idFromUrl( url: string ): string {
    const parsedUrl = new URL(url) ;
    return parsedUrl.pathname.split('/').pop();
  }

}
