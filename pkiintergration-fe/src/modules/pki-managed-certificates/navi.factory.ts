import { Injectable } from '@angular/core';
import { NavigatorNode, NavigatorNodeFactory, _ } from '@c8y/ngx-components';

@Injectable()
export class NavigationFactory implements NavigatorNodeFactory {
  node: NavigatorNode;
  constructor() {
    this.node = new NavigatorNode({
      label: 'Managed Certificates',
      icon: 'connected-people',
      path: '/managed-certificates',
      priority: 98,
      parent: 'Management'
    })
  }

  get() {
    return this.node;
  }
}