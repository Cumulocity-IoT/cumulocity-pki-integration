import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule as NgRouterModule } from '@angular/router';
import { UpgradeModule as NgUpgradeModule } from '@angular/upgrade/static';
import { CoreModule, RouterModule } from '@c8y/ngx-components';
import { AssetsNavigatorModule } from '@c8y/ngx-components/assets-navigator';
import { BinaryFileDownloadModule } from '@c8y/ngx-components/binary-file-download';
import { ChildDevicesModule } from '@c8y/ngx-components/child-devices';
import { DeviceInfoDashboardModule, DeviceManagementHomeDashboardModule } from '@c8y/ngx-components/context-dashboard';
import { DeviceGridExampleModule } from '@c8y/ngx-components/device-grid-example';
import { DeviceProfileModule } from '@c8y/ngx-components/device-profile';
import { DiagnosticsModule } from '@c8y/ngx-components/diagnostics';
import { OperationsModule } from '@c8y/ngx-components/operations';
import { ImpactProtocolModule } from '@c8y/ngx-components/protocol-impact';
import { LpwanProtocolModule } from '@c8y/ngx-components/protocol-lpwan';
import { OpcuaProtocolModule } from '@c8y/ngx-components/protocol-opcua';
import { RepositoryModule } from '@c8y/ngx-components/repository';
import { SearchModule } from '@c8y/ngx-components/search';
import { SubAssetsModule } from '@c8y/ngx-components/sub-assets';
import { TrustedCertificatesModule } from '@c8y/ngx-components/trusted-certificates';
import { DashboardUpgradeModule, HybridAppModule, UpgradeModule, UPGRADE_ROUTES } from '@c8y/ngx-components/upgrade';
import { PkiManagedCertificatesModule } from '@modules/pki-managed-certificates/pki-managed-certificates.module';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { HooksModule } from '@modules/hooks/hooks.module';

@NgModule({
  imports: [
    // Upgrade module must be the first
    UpgradeModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(),
    NgRouterModule.forRoot([...UPGRADE_ROUTES], { enableTracing: false, useHash: true }),
    CoreModule.forRoot(),
    AssetsNavigatorModule.config({
      smartGroups: true
    }),
    OperationsModule,
    OpcuaProtocolModule,
    ImpactProtocolModule,
    TrustedCertificatesModule,
    DeviceGridExampleModule,
    NgUpgradeModule,
    DashboardUpgradeModule,
    RepositoryModule,
    DeviceProfileModule,
    BinaryFileDownloadModule,
    SearchModule,
    LpwanProtocolModule,
    SubAssetsModule,
    ChildDevicesModule,
    DeviceManagementHomeDashboardModule,
    DeviceInfoDashboardModule,
    DiagnosticsModule,
    PkiManagedCertificatesModule,
    HooksModule
  ],
  declarations: [],
  entryComponents: [],

  providers: [BsModalRef]
})
export class AppModule extends HybridAppModule {
  constructor(protected upgrade: NgUpgradeModule) {
    super();
  }
}
