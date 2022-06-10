import { Component } from '@angular/core';
import { FetchClient } from '@c8y/client';
import { AlertService } from '@c8y/ngx-components';
import { CreateCertificateService } from '@services/create-certificate.service';
import { BsModalService } from 'ngx-bootstrap/modal';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
@Component({
  selector: 'app-pki-managed-certificates',
  templateUrl: './pki-managed-certificates.component.html'
})
export class PkiManagedCertificatesComponent {
  data: any;

  constructor(
    private fetchClient: FetchClient,
    private modalService: BsModalService,
    private alertService: AlertService,
    private certificateService: CreateCertificateService
  ) {}

  createCertificate() {
    this.modalService.show(CreateCertificateComponent, {});
    const sub = this.modalService.onHidden.subscribe(() => {
      sub.unsubscribe();
    });
  }
}
