export interface MetaAudiencePlugin {
  showBannerAd(options: { placementId: string, position: 'TOP' | 'CENTER' | 'BUTTON' }): Promise<void>;
  removeBannerAd(): Promise<void>;
  showInterstitialAd(options: { placementId: string, testDevices?: string[] }): Promise<void>;
}