import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule as NgRouterModule, Routes } from '@angular/router';
import { CoreModule, HOOK_NAVIGATOR_NODES, RouterModule } from '@c8y/ngx-components';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { NavigationFactory } from './navi.factory';
import { PkiManagedCertificatesComponent } from './pki-managed-certificates.component';

const moduleRoutes: Routes = [{ path: 'managed-certificates', component: PkiManagedCertificatesComponent }];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(),
    NgRouterModule.forRoot(moduleRoutes, { enableTracing: true, useHash: true }),
    CoreModule.forRoot()
  ],

  // providers: [NavigationFactory, { provide: NavigatorService, useExisting: NavigationFactory }],
  providers: [{ provide: HOOK_NAVIGATOR_NODES, useClass: NavigationFactory, multi: true }],

  declarations: [PkiManagedCertificatesComponent, CreateCertificateComponent]
})
export class PkiManagedCertificatesModule {}
