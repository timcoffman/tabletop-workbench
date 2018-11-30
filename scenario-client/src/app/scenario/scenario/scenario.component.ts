import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { ScenariosService } from 'src/app/api/services';
import { switchMap, finalize } from 'rxjs/operators';
import { Scenario } from 'src/app/api/models';
import { FormControl, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-scenario',
  templateUrl: './scenario.component.html',
  styleUrls: ['./scenario.component.css']
})
export class ScenarioComponent implements OnInit {

  public loading = false;
  public scenarioId: number ;

  scenarioForm: FormGroup ;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private scenariosService: ScenariosService
  ) {
    this.scenarioForm = formBuilder.group({
        id: new FormControl(),
        model: new FormControl(),
        description: new FormControl(),
        participantCountMin: new FormControl(),
        participantCountMax: new FormControl(),
        executionCount: new FormControl(),
        termination: new FormControl(),
      }) ;
  }

  ngOnInit() {
    this.route.paramMap
      .pipe(
          switchMap((params: ParamMap) => {
            this.loading = true ;
            this.scenarioId = parseInt(params.get('scenarioId'), 10);
            return this.scenariosService.getScenario(this.scenarioId) ;
          }),
          finalize( () => this.loading = false )
        )
      .subscribe( (scenario) => { this.scenarioForm.patchValue(scenario); this.loading = false ; } )
      ;
  }

  saveChanges( successNavigation: any[] ): void {
    this.scenariosService.postScenario( this.scenarioId, this.scenarioForm.value )
      .subscribe( s => this.router.navigate( successNavigation) )
      ;
  }

}
