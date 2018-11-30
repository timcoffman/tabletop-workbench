import { Component, OnInit } from '@angular/core';
import { ParamMap, Router, ActivatedRoute } from '@angular/router';
import { switchMap, finalize } from 'rxjs/operators';
import { StatesService, ModelsService } from 'src/app/api/services';
import { ModelResource, PartPrototypeResource } from 'src/app/api/models';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-model',
  templateUrl: './model.component.html',
  styleUrls: ['./model.component.css']
})
export class ModelComponent implements OnInit {

  public loading = false;
  public modelId: string ;
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
            this.modelId = params.get('modelId');
            return this.modelsService.getModel(this.modelId) ;
          }),
          finalize( () => this.loading = false )
        )
      .subscribe( (model) => { this.model = model; this.loading = false ; } )
      ;
  }

  partPrototypeForId( partPrototypeId: string ): Observable<PartPrototypeResource> {
    return this.modelsService.getPartPrototype( partPrototypeId, this.modelId ) ;
  }

  idFromUrl( url: string ): string {
    const parsedUrl = new URL(url) ;
    return parsedUrl.pathname.split('/').pop();
  }

}
