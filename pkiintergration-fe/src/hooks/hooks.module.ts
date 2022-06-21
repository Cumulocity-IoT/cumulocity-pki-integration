import { NgModule } from '@angular/core';
import { CoreModule, HOOK_ACTION_BAR } from '@c8y/ngx-components';
import { CreateOperationActionFactory } from './action';


export const hooks = [{ provide: HOOK_ACTION_BAR, useClass: CreateOperationActionFactory, multi: true }];

@NgModule({
  declarations: [],
  imports: [CoreModule],
  providers: hooks
})
export class HooksModule {}
