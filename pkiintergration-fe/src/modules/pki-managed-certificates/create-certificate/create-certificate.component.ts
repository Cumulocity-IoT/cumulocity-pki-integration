import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
@Component({
  selector: 'app-create-certificate',
  templateUrl: './create-certificate.component.html'
})
export class CreateCertificateComponent {
  file: File | null = null;

  constructor(public bsModalRef: BsModalRef) {}

  onFileInput(files: FileList): void {
    if (files) {
      this.file = files.item(0);
    }
  }

  // This part should be continued to implement in the future
  //   checkFile() {
  //     var fileElement = document.getElementById("uploadFile");
  //     var fileExtension = "";
  //     if (fileElement.value.lastIndexOf(".") > 0) {
  //         fileExtension = fileElement.value.substring(fileElement.value.lastIndexOf(".") + 1, fileElement.value.length);
  //     }
  //     if (fileExtension.toLowerCase() == "pub") {
  //         return true;
  //     }
  //     else {
  //         alert("You must select a PUB file for upload");
  //         return false;
  //     }
  // }
}
