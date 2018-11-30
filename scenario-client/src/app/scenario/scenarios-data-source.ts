import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { ScenariosService } from '../api/services';
import { Scenario } from '../api/models';

export class ScenariosDataSource implements DataSource<Scenario> {
    private scenariosSubject = new BehaviorSubject<Scenario[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private readonly scenariosService: ScenariosService) {
    }

    connect(collectionViewer: CollectionViewer): Observable<Scenario[]> {
      return this.scenariosSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
      this.scenariosSubject.complete();
      this.loadingSubject.complete();
    }

    loadScenarios(): void {
        this.loadingSubject.next(true) ;
        this.scenariosService.getScenarios().pipe(
            catchError( () => of([]) ),
            finalize( () => this.loadingSubject.next(false) )
        )
        .subscribe( scenarios => this.scenariosSubject.next(scenarios) )
        ;
    }
}
