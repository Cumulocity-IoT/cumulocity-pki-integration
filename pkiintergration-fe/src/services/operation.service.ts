import { Injectable } from '@angular/core';
import { IOperation } from '@c8y/client';
import { OperationService } from '@c8y/ngx-components/api';

@Injectable({ providedIn: 'root' })
export class KeypairOperationService {
  constructor(private operationService: OperationService) {}

  async requestPubKey(deviceId: string): Promise<IOperation> {
    const operation: IOperation = {
      request_pubkey: {},
      deviceId
    };

    const { res, data } = await this.operationService.create(operation);
    // TODO: Remove console
    console.log('resService: ', res);
    return data;
  }
}
