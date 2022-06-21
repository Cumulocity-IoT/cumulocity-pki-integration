import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CreateKeyPairService } from '@services/create-certificate.service';

@Component({
  selector: 'app-create-keypair',
  templateUrl: './create-keypair.component.html'
})
export class CreateKeypairComponent implements OnInit {
  deviceId: string;

  constructor(private keypairService: CreateKeyPairService, private route: ActivatedRoute) {}

  ngOnInit() {
    const snapshot = this.route.snapshot;
    if (snapshot && snapshot.firstChild && snapshot.firstChild.data) {
      this.deviceId = snapshot.firstChild.data.contextData.id;
    }

    console.log('devideid:', this.deviceId);
  }

  requestPubKey() {
    this.keypairService.getKeyPair(this.deviceId);
    console.log('Buttondevideid:', this.deviceId);
  }
}
