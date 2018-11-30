import { Component, OnInit } from '@angular/core';
import { ParamMap, Router, ActivatedRoute } from '@angular/router';
import { switchMap, finalize } from 'rxjs/operators';
import { StatesService, ModelsService } from 'src/app/api/services';
<<<<<<< HEAD
import { ModelResource, PartPrototypeResource } from 'src/app/api/models';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import { Observable } from 'rxjs';
=======
import { ModelResource } from 'src/app/api/models';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444

@Component({
  selector: 'app-model',
  templateUrl: './model.component.html',
  styleUrls: ['./model.component.css']
})
export class ModelComponent implements OnInit {

  public loading = false;
<<<<<<< HEAD
  public modelId: string ;
=======
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
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
<<<<<<< HEAD
            this.modelId = params.get('modelId');
            return this.modelsService.getModel(this.modelId) ;
=======
            return this.modelsService.getModel(params.get('modelId')) ;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
          }),
          finalize( () => this.loading = false )
        )
      .subscribe( (model) => { this.model = model; this.loading = false ; } )
      ;
  }

<<<<<<< HEAD
  partPrototypeForId( partPrototypeId: string ): Observable<PartPrototypeResource> {
    return this.modelsService.getPartPrototype( partPrototypeId, this.modelId ) ;
  }

  idFromUrl( url: string ): string {
    const parsedUrl = new URL(url) ;
    return parsedUrl.pathname.split('/').pop();
  }

=======
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
}
