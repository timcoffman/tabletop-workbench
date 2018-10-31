import { Component, OnInit } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { StateResource } from 'src/app/api/models';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { StatesService } from 'src/app/api/services';
import { switchMap, finalize } from 'rxjs/operators';

@Component({
  selector: 'app-state',
  templateUrl: './state.component.html',
  styleUrls: ['./state.component.css']
})
export class StateComponent implements OnInit {

  public loading = false;
  public state: StateResource ;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private statesService: StatesService
   ) { }

  ngOnInit() {
    this.route.paramMap
      .pipe(
          switchMap((params: ParamMap) => {
            this.loading = true ;
            return this.statesService.getState(params.get('stateId')) ;
          }),
          finalize( () => this.loading = false )
        )
      .subscribe( (state) => { this.state = state; this.loading = false ; } )
      ;
  }

}
