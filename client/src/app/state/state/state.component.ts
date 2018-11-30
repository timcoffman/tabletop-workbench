import { Component, OnInit } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { StateResource, PlaceResource } from 'src/app/api/models';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { StatesService } from 'src/app/api/services';
import { switchMap, finalize } from 'rxjs/operators';
import * as shape from 'd3-shape';

@Component({
  selector: 'app-state',
  templateUrl: './state.component.html',
  styleUrls: ['./state.component.css']
})
export class StateComponent implements OnInit {

  public loading = false;
  public state: StateResource ;

  graphView: any[] = [ 500, 500 ];
  graphCurve: any = shape.curveLinear ;
  graphColorScheme: any = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };
  graphShowLegend = false ;
  graphOrientation = 'LR' ;

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

  relationshipsHavingSource(place: PlaceResource) {
    const placeDesc = this.parsePlaceUrl(place.resource) ;
    const matchPlace = placeDesc.ownerPartId + '|' + placeDesc.declaringPluginUrl + '/' + placeDesc.localName ;
    return this.state.relationships.relationships.filter( r => r.source === matchPlace ) ;
  }

  relationshipsHavingDestination(place: PlaceResource) {
    const placeDesc = this.parsePlaceUrl(place.resource) ;
    const matchPlace = placeDesc.ownerPartId + '|' + placeDesc.declaringPluginUrl + '/' + placeDesc.localName ;
    return this.state.relationships.relationships.filter( r => r.destination === matchPlace ) ;
  }

  idFromUrl( url: string ): string {
    const parsedUrl = new URL(url) ;
    return parsedUrl.pathname.split('/').pop();
  }

  parsePlaceUrl( url: string ): { ownerPartId: string, declaringPluginUrl: string, localName: string } {
    const parsedUrl = new URL(url) ;
    const pathComponents = parsedUrl.pathname.split('/');
    const localName = pathComponents.pop() ;
    const declaringPluginUrl = pathComponents.pop() ;
    pathComponents.pop() ;
    const ownerPartId = pathComponents.pop() ;
    return {
      ownerPartId: decodeURIComponent(ownerPartId),
      declaringPluginUrl: decodeURIComponent(declaringPluginUrl),
      localName: decodeURIComponent(localName)
    } ;
  }

  partsAsNodes(): { id: string, label: string}[] {
    return [ { id: 'x', label: 'x' }, { id: 'y', label: 'y' } , { id: 'z', label: 'z' } ] ;
  }

  relationshipsAsLinks(): { source: string, target: string, label: string}[] {
    return [
      { source: 'x', target: 'y', label: 'a' },
      { source: 'x', target: 'z', label: 'b' },
      { source: 'y', target: 'z', label: 'c' }
    ] ;
  }
}
