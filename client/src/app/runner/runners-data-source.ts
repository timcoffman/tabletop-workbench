import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { RunnersService } from '../api/services';
import { RunnerResource } from '../api/models';

export class RunnersDataSource implements DataSource<RunnerResource> {
    private runnersSubject = new BehaviorSubject<RunnerResource[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private readonly runnersService: RunnersService) {
    }

    connect(collectionViewer: CollectionViewer): Observable<RunnerResource[]> {
      return this.runnersSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
      this.runnersSubject.complete();
      this.loadingSubject.complete();
    }

    loadRunners(): void {
        this.loadingSubject.next(true) ;
        this.runnersService.getRunners().pipe(
            catchError( () => of([]) ),
            finalize( () => this.loadingSubject.next(false) )
        )
        .subscribe( runners => this.runnersSubject.next(runners) )
        ;
    }
}
