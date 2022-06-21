import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule as NgRouterModule, Routes } from '@angular/router';
import { CoreModule, HOOK_NAVIGATOR_NODES, RouterModule } from '@c8y/ngx-components';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NavigationFactory } from '../../hooks/navi.factory';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { PkiManagedCertificatesComponent } from './pki-managed-certificates.component';

const moduleRoutes: Routes = [{ path: 'managed-certificates', component: PkiManagedCertificatesComponent }];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(),
    NgRouterModule.forRoot(moduleRoutes, { enableTracing: true, useHash: true }),
    CoreModule.forRoot(),
    BsDropdownModule.forRoot()
  ],

  // providers: [NavigationFactory, { provide: NavigatorService, useExisting: NavigationFactory }],
  providers: [{ provide: HOOK_NAVIGATOR_NODES, useClass: NavigationFactory, multi: true }],

  declarations: [PkiManagedCertificatesComponent, CreateCertificateComponent]
})
export class PkiManagedCertificatesModule {}
