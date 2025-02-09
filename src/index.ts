import { registerPlugin } from '@capacitor/core';

import type { MetaAudienceNetworkPlugin } from './definitions';

const MetaAudienceNetwork = registerPlugin<MetaAudienceNetworkPlugin>('MetaAudienceNetwork', {
  web: () => import('./web').then((m) => new m.MetaAudienceNetworkWeb()),
});

export * from './definitions';
export { MetaAudienceNetwork };
