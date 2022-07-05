import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ActionBarFactory, ActionBarItem } from '@c8y/ngx-components';
import { CreateKeypairComponent } from '@modules/create-keypair-operation-button/create-keypair.component';

@Injectable()
export class CreateOperationActionFactory implements ActionBarFactory {
  constructor(private router: Router) {}

  get() {
    const actions: ActionBarItem[] = [];

    const showButton: ActionBarItem = {
      priority: 0,
      placement: 'more',
      template: CreateKeypairComponent
    };

    if (this.router.url.match(/device-info/g)) {
      actions.push(showButton);
    }

    return actions;
  }
}
