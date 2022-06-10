import { Injectable } from '@angular/core';
import { FetchClient } from '@c8y/client';

@Injectable({
  providedIn: 'root'
})
export class CreateCertificateService {
  private options = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' }
  };

  constructor(private fetchClient: FetchClient) {}
  async uploadCertificate() {
    //Dummy service, there are not available from Backend
    const response = await this.fetchClient.fetch('service/certificates', this.options);
    if (response.status !== 200) {
      throw Error('Wrong status code');
    }
  }
}
