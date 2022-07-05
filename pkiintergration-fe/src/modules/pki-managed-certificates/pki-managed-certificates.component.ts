import { Component } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
@Component({
  selector: 'app-pki-managed-certificates',
  templateUrl: './pki-managed-certificates.component.html'
})
export class PkiManagedCertificatesComponent {
  constructor(private modalService: BsModalService) {}

  createCertificate() {
    this.modalService.show(CreateCertificateComponent, {});
    const sub = this.modalService.onHidden.subscribe(() => {
      sub.unsubscribe();
    });
  }
}
