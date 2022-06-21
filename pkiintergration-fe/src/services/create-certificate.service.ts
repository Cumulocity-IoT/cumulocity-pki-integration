import { Injectable } from '@angular/core';
import { FetchClient } from '@c8y/client';
import { AlertService, gettext } from '@c8y/ngx-components';

@Injectable({
  providedIn: 'root'
})
export class CreateKeyPairService {
  constructor(private fetchClient: FetchClient, private alertService: AlertService) {}
  async getKeyPair(deviceId: string): Promise<void> {
    try {
      //Dummy service, there are not available from Backend
      const response = await this.fetchClient.fetch('createOperationForDevice/{deviceId}', {
        method: 'POST',
        body: JSON.stringify({ deviceId }),
        headers: { 'Content-Type': 'application/json' }
      } as RequestInit);
      // TODO: Remove console
      console.log('res: ', response);
      if (response.status >= 200 && response.status < 300) {
        this.alertService.success(gettext('Operation for Device created'));
      } else {
        throw Error();
      }
    } catch (error) {
      this.alertService.danger(gettext('Couldnt create the Operation for Device'));
    }
  }
}
