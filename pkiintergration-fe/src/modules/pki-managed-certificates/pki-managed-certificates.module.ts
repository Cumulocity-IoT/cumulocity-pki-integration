import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule as NgRouterModule, Routes } from '@angular/router';
import { CoreModule, HOOK_NAVIGATOR_NODES, RouterModule } from '@c8y/ngx-components';
import { PATH_MANAGED_CERTIFICATES } from '@constants/navigation.contants';
import { NavigationFactory } from '@modules/hooks/navigation';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { PkiManagedCertificatesComponent } from './pki-managed-certificates.component';

const moduleRoutes: Routes = [{ path: PATH_MANAGED_CERTIFICATES, component: PkiManagedCertificatesComponent }];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(),
    NgRouterModule.forRoot(moduleRoutes, { enableTracing: true, useHash: true }),
    CoreModule.forRoot(),
    BsDropdownModule.forRoot()
  ],

  providers: [{ provide: HOOK_NAVIGATOR_NODES, useClass: NavigationFactory, multi: true }],
  declarations: [PkiManagedCertificatesComponent, CreateCertificateComponent]
})
export class PkiManagedCertificatesModule {}
