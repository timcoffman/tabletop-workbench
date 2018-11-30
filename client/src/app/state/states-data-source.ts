import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { StatesService } from '../api/services';

export class StatesDataSource implements DataSource<string> {
    private statesSubject = new BehaviorSubject<string[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private readonly statesService: StatesService) {
    }

    connect(collectionViewer: CollectionViewer): Observable<string[]> {
      return this.statesSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
      this.statesSubject.complete();
      this.loadingSubject.complete();
    }

    loadStates(): void {
        this.loadingSubject.next(true) ;
        this.statesService.getStates().pipe(
            catchError( () => of([]) ),
            finalize( () => this.loadingSubject.next(false) )
        )
        .subscribe( States => this.statesSubject.next(States) )
        ;
    }
}
