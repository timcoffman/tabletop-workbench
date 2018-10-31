import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { ModelsService } from '../api/services';

export class ModelsDataSource implements DataSource<string> {
    private modelsSubject = new BehaviorSubject<string[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private readonly modelsService: ModelsService) {
    }

    connect(collectionViewer: CollectionViewer): Observable<string[]> {
      return this.modelsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
      this.modelsSubject.complete();
      this.loadingSubject.complete();
    }

    loadModels(): void {
        this.loadingSubject.next(true) ;
        this.modelsService.getModels().pipe(
            catchError( () => of([]) ),
            finalize( () => this.loadingSubject.next(false) )
        )
        .subscribe( models => this.modelsSubject.next(models) )
        ;
    }
}
