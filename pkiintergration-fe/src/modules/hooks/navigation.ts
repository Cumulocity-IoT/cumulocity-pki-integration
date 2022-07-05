import { Injectable } from '@angular/core';
import { NavigatorNode, NavigatorNodeFactory } from '@c8y/ngx-components';
import { PATH_MANAGED_CERTIFICATES } from '@constants/navigation.contants';

@Injectable()
export class NavigationFactory implements NavigatorNodeFactory {
  node = new NavigatorNode({
    label: 'Managed Certificates',
    icon: 'connected-people',
    path: `/${PATH_MANAGED_CERTIFICATES}`,
    priority: 98,
    parent: 'Management'
  });

  get() {
    return this.node;
  }
}
