import { NgModule } from '@angular/core';
import { CoreModule, HOOK_ACTION_BAR } from '@c8y/ngx-components';
import { CreateOperationActionFactory } from './action';

const hooks = [{ provide: HOOK_ACTION_BAR, useClass: CreateOperationActionFactory, multi: true }];

@NgModule({
  imports: [CoreModule],
  providers: hooks
})
export class HooksModule {}
