import { WebPlugin } from '@capacitor/core';

import type { MetaAudienceNetworkPlugin } from './definitions';

export class MetaAudienceNetworkWeb extends WebPlugin implements MetaAudienceNetworkPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
