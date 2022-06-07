import { Injectable } from '@angular/core';
import { IOperation } from '@c8y/client';
import { OperationService } from '@c8y/ngx-components/api';

@Injectable({ providedIn: 'root' })
export class yourService {
  constructor(private operationService: OperationService) {}

  async requestPubKey(deviceId: string): Promise<IOperation> {
    // http://resources.cumulocity.com/documentation/websdk/client/interfaces/ioperation.html
    const operation: IOperation = {
      request_pubkey: {},
      deviceId
    };
    // http://resources.cumulocity.com/documentation/websdk/client/classes/operationservice.html#create
    const { res, data } = await this.operationService.create(operation);
    return data;
  }
}
