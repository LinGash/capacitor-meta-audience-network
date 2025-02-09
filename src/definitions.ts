export interface MetaAudienceNetworkPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
