import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlertService } from '@c8y/ngx-components';
import { CreateKeyPairService } from '@services/create-certificate.service';

@Component({
  selector: 'app-create-keypair',
  templateUrl: './create-keypair.component.html'
})
export class CreateKeypairComponent implements OnInit {
  deviceId: string;
  // device: IManagedObject;
  alertService: AlertService;
  ActivatedRoute: ActivatedRoute;

  constructor(private createKeypairService: CreateKeyPairService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    const snapshot = this.activatedRoute.snapshot;
    if (snapshot && snapshot.firstChild && snapshot.firstChild.data) {
      this.deviceId = snapshot.firstChild.data.contextData.id;
    }
    
    console.log('devideid:', this.deviceId);
  }

  requestPubKey() {
    console.log('requestPubKey');
    this.createKeyPair();
    
  }

  private async createKeyPair() {
    try {
      if (this.deviceId) {
        await this.createKeypairService.getKeyPair(this.deviceId);
      } else {
        this.alertService.danger('Device information missing.');
      }
    } catch (e) {
      this.alertService.danger('Failed to create Keypair.');
    }
  }
}
