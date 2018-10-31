import { Injectable, Component, OnDestroy, OnInit } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable()
export class AuthGuard implements CanActivate, OnDestroy, OnInit {


    constructor(
        private readonly router: Router
        ) {
    }

    private cannotActivate( route: ActivatedRouteSnapshot ): boolean {
        const opts = route.data['authGuardOptions'] ;
        if ( opts ) {
            if ( opts.redirect ) {
                this.router.navigate( [ opts.redirect ] ) ;
            }
        }
        return false ;
    }

    canActivate(
            route: ActivatedRouteSnapshot,
            state: RouterStateSnapshot
            ): boolean | Observable<boolean> | Promise<boolean> {
        return true ;
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }

}

