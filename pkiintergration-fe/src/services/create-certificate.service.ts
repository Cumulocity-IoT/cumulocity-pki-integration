import { Injectable } from '@angular/core';
import { FetchClient } from '@c8y/client';
import { AlertService } from '@c8y/ngx-components';

@Injectable({
  providedIn: 'root'
})
export class CreateKeyPairService {
  constructor(private fetchClient: FetchClient, private alertService: AlertService) {}

  async getKeyPair(deviceId: string): Promise<void> {
    try {
      const response = await this.fetchClient.fetch(
        `/service/pki-integration/operations/createOperationForDevice/${deviceId}`,
        {
          method: 'GET',
          // body: JSON.stringify({ deviceId }),
          // headers: { 
          //   'Content-Type': 'application/json',
          //   Accept: 'application/json'
          // }
        }
      );

      console.log('res: ', response);

      if (response.status >= 200 && response.status < 300) {
        const body = await response.json();
        console.log(body);
        this.alertService.success('Operation for Device created');
      } else {
        throw Error();
      }
    } catch (error) {
      this.alertService.danger('Couldnt create the Operation for Device');
    }
  }
}
