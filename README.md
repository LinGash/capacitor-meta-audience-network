# Capacitor Meta Audience Plugin

This Capacitor plugin integrates Meta Audience Network ads into your Android application. It supports both **banner ads** and **interstitial ads**

## Features

- **Banner Ads**: Display banner ads at the **top, center, or bottom** of the screen.
- **Interstitial Ads**: Show full-screen interstitial ads.
- **Test Devices Support**: Optionally add test device IDs to receive test ads.
- **Android-Only**: This plugin currently supports **Android** only.

## Installation

```bash
npm install @lingash/capacitor-meta-audience-network
```

## Usage

### Import and Initialize

```typescript
import MetaAudiencePlugin from '@lingash/capacitor-meta-audience-network';

async showInterstitial() {
    try{
        await MetaAudiencePlugin.showInterstitialAd({placementId: this.interstitialPlacementId, testDevices: ["c23f7790-1359-4af9-8c25-e6534c0de019"]});
        // testDevices is optional
    }catch(err){
        console.log('err from loadInterstitial: ', err);
        return null
    }
}

async showBanner(position: 'TOP' | 'CENTER' | 'BUTTON' = 'BUTTON') {
    try{
        await MetaAudiencePlugin.showBannerAd({placementId: 'YOUR_BANNER_ID', position: 'BUTTON', testDevices: ["c23f7790-1359-4af9-8c25-e6534c0de019"]});
            // testDevices is optional
    }catch(err){
        console.log("err from loadBanner: ",err);
    }
}

async removeBanner() {
    try{
        await MetaAudiencePlugin.removeBannerAd();
    }catch(err){
        console.log("err from removeBanner: ",err);
    }
}
```

## API

### `showInterstitialAd(options: { placementId: string, testDevices?: string[] })`
- **Description**: Displays an interstitial ad.
- **Parameters**:
  - `placementId` (**string**): The ad placement ID.
  - `testDevices` (**string[]**, optional): List of test device IDs.
- **Returns**: A promise that resolves when the ad is displayed.

### `showBannerAd(options: { placementId: string, position: 'TOP' | 'CENTER' | 'BOTTOM', testDevices?: string[] })`
- **Description**: Displays a banner ad.
- **Parameters**:
  - `placementId` (**string**): The ad placement ID.
  - `position` (**'TOP' | 'CENTER' | 'BOTTOM'**): Position of the banner.
  - `testDevices` (**string[]**, optional): List of test device IDs.
- **Returns**: A promise that resolves when the banner is displayed.

### `removeBannerAd()`
- **Description**: Removes the banner ad.
- **Parameters**: None.
- **Returns**: A promise that resolves when the banner is removed.

## Notes

- Ensure that your **Meta Audience Network** account is properly set up and approved.
- Use **test devices** to avoid violations of Meta Audience Network policies.
- This plugin currently supports **Android only**.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Support

For any issues or feature requests, please file an issue on [GitHub](https://github.com/LinGash/capacitor-meta-audience-network/issues).

