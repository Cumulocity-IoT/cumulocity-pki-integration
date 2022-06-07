import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
@Component({
  selector: 'app-create-certificate',
  templateUrl: './create-certificate.component.html'
})
export class CreateCertificateComponent {
  constructor(public bsModalRef: BsModalRef) {}

  ngOnInit() {}
}
