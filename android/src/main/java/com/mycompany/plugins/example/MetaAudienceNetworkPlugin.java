package me.lingash.plugins.metaaudiencenetwork;

import android.widget.FrameLayout;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.AdSettings;

import android.widget.RelativeLayout;
import android.util.Log;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.app.Activity;
import android.content.Context;
import androidx.core.util.Supplier;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.getcapacitor.Plugin;
import com.getcapacitor.JSObject;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.PluginCall;

import org.json.JSONArray;
import org.json.JSONException;

@CapacitorPlugin(name = "MetaAudiencePlugin")
public class MetaAudienceNetworkPlugin extends Plugin {

    private AdView adView;

    private RelativeLayout mAdViewLayout;

    private ViewGroup mViewGroup;

    @Override
    public void load() {
        super.load();
        // Initialize Meta Audience SDK
        AudienceNetworkAds.initialize(this.getContext());
    }

    @PluginMethod
    public void showBannerAd(PluginCall call) {
        String placementId = call.getString("placementId");
        String position = call.getString("position");
        JSONArray testDevices = call.getArray("testDevices");

        if (placementId == null) {
            call.reject("Placement ID is required");
            return;
        }

        mAdViewLayout = new RelativeLayout(this.getContext());
        mAdViewLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        // mAdViewLayout.setVerticalGravity(Gravity.BOTTOM);

        final CoordinatorLayout.LayoutParams mAdViewLayoutParams = new CoordinatorLayout.LayoutParams(
            CoordinatorLayout.LayoutParams.WRAP_CONTENT,
            CoordinatorLayout.LayoutParams.WRAP_CONTENT
        );

        switch (position) {
            case "TOP":
                mAdViewLayoutParams.gravity = Gravity.TOP;
                break;
            case "CENTER":
                mAdViewLayoutParams.gravity = Gravity.CENTER;
                break;
            default:    
                mAdViewLayoutParams.gravity = Gravity.BOTTOM;
                break;
        }

        mAdViewLayout.setLayoutParams(mAdViewLayoutParams);
        
        // AdSettings.addTestDevice("5e9df4a7-b3ad-47a0-84ce-0969c7752a15");

        // Add test devices if provided
        if (testDevices != null) {
            for (int i = 0; i < testDevices.length(); i++) {
                try {
                    String deviceId = testDevices.getString(i);
                    AdSettings.addTestDevice(deviceId);
                } catch (JSONException e) {
                    Log.e("MetaAudiencePlugin", "Error parsing test devices: " + e.getMessage());
                }
            }
        }
        
        // Create and configure the AdView
        adView = new AdView(this.getContext(), placementId, AdSize.BANNER_HEIGHT_50); 
        
        mAdViewLayout.addView(adView);
        
        mViewGroup = (ViewGroup) ((ViewGroup) this.getActivity().findViewById(android.R.id.content)).getChildAt(0);

        if (mViewGroup == null) {
            call.reject("mViewGroup not found");
            return;
        }

        // Add the banner ad view on the main thread
        this.getActivity().runOnUiThread(() -> {
            try {
                mViewGroup.addView(mAdViewLayout);
                adView.loadAd(
                adView.buildLoadAdConfig()
                    .withAdListener(new AdListener() {
                        @Override
                        public void onError(Ad ad, AdError adError) {
                            call.reject(adError.getErrorMessage());
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            call.resolve();
                        }

                        @Override
                        public void onAdClicked(Ad ad) {
                            // Optionally handle ad click
                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {
                            // Optionally handle ad impression
                        }
                    })
                    .build()
                );
            } catch (Exception e) {
                call.reject("An error occurred in runOnUiThread: " + e.getMessage());
            }
        });
    }

    @PluginMethod
    public void removeBannerAd(PluginCall call) {
        this.getActivity().runOnUiThread(() -> {
            if (mAdViewLayout != null && adView != null) {
                mAdViewLayout.removeView(adView);
            }

            if (adView != null) {
                adView.destroy();
                adView = null; // Clear reference to avoid memory leaks
            }

            mAdViewLayout = null;

            call.resolve();
        });
    }
    
    @PluginMethod
    public void showInterstitialAd(PluginCall call) {
        String placementId = call.getString("placementId");
        JSONArray testDevices = call.getArray("testDevices");
        if (placementId == null) {
            call.reject("Placement ID is required");
            return;
        }

        // AdSettings.addTestDevice("c23f7790-1359-4af9-8c25-e6534c0de049");

        // Add test devices if provided
        if (testDevices != null) {
            for (int i = 0; i < testDevices.length(); i++) {
                try {
                    String deviceId = testDevices.getString(i);
                    AdSettings.addTestDevice(deviceId);
                } catch (JSONException e) {
                    Log.e("MetaAudiencePlugin", "Error parsing test devices: " + e.getMessage());
                }
            }
        }
        

        InterstitialAd interstitialAd = new InterstitialAd(this.getContext(), placementId);

        interstitialAd.loadAd(
            interstitialAd.buildLoadAdConfig()
                .withAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        // Interstitial displayed
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed
                        call.resolve();
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        call.reject(adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Show the ad once it's loaded
                        interstitialAd.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Impression logged
                    }
                })
                .build()
            );
        }
    }
