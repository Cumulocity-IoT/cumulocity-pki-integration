import { Component, OnInit } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { FetchClient } from '@c8y/client';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { AlertService } from '@c8y/ngx-components';
import { CreateCertificateService } from '@services/create-certificate.service';
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
    private certificateService: CreateCertificateService,
    ) {}

 

  createCertificate() {
    this.modalService.show(CreateCertificateComponent, {
      initialState: { isModal: true }
    });
    let sub = this.modalService.onHidden.subscribe(() => {
      sub.unsubscribe();
    });
  }
}
