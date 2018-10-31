import { Component, OnInit } from '@angular/core';
import { ParamMap, Router, ActivatedRoute } from '@angular/router';
import { switchMap, finalize } from 'rxjs/operators';
import { StatesService, ModelsService } from 'src/app/api/services';
import { ModelResource } from 'src/app/api/models';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';

@Component({
  selector: 'app-model',
  templateUrl: './model.component.html',
  styleUrls: ['./model.component.css']
})
export class ModelComponent implements OnInit {

  public loading = false;
  public model: ModelResource ;

  pluginColumnsToDisplay = [ 'name', 'info' ];
  placeTypeColumnsToDisplay = [ 'name', 'info' ];
  relationshipTypeColumnsToDisplay = [ 'name', 'info' ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private modelsService: ModelsService
   ) { }

  ngOnInit() {
    this.route.paramMap
      .pipe(
          switchMap((params: ParamMap) => {
            this.loading = true ;
            return this.modelsService.getModel(params.get('modelId')) ;
          }),
          finalize( () => this.loading = false )
        )
      .subscribe( (model) => { this.model = model; this.loading = false ; } )
      ;
  }

}
