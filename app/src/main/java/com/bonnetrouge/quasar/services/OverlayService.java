package com.bonnetrouge.quasar.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bonnetrouge.quasar.R;

public class OverlayService extends Service {

    public static final int OVERLAY_REQUEST_CODE = 1815;

    private final IBinder binder = new OverlayBinder();

    private boolean shouldShow = true;

    private WindowManager windowManager;
    private ImageView overlay;
    private WindowManager.LayoutParams params;
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        setupOverlay();
        return this.binder;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlay != null) {
            if(overlay.getWindowToken() != null) {
                windowManager.removeViewImmediate(overlay);
            }
        }
        windowManager = null;
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e) {
                Log.v(OverlayService.class.getName(), "Accessibility broadcast receiver not registered.");
            }
        }
    }

    private void setupOverlay() {
        this.windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        final LayoutInflater inflater = LayoutInflater.from(this);
        this.overlay = (ImageView) inflater.inflate(R.layout.view_overlay, null, false);

        setupLayoutParams();
        showOverlay();
        setupBroadcastReceiver();
    }

    public void passImageToOverlay(Bitmap bitmap) {
        if (this.overlay != null) {
            this.overlay.setImageBitmap(bitmap);
        }
    }

    private void setupLayoutParams() {
        this.params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
    }

    public void showOverlay() {
        if (!isShowing() && shouldShow) {
            this.windowManager.addView(this.overlay, this.params);
        }
    }

    public void hideOverlay() {
        if (isShowing()) {
            this.windowManager.removeViewImmediate(this.overlay);
        }
    }

    public void enable() {
        this.shouldShow = true;
    }

    public void disable() {
        this.shouldShow = false;
    }

    private void setupBroadcastReceiver() {
        broadcastReceiver = new OverlayBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(OverlayAccessibilityService.ACTION_DISABLE_FLOATING_VIDEO);
        intentFilter.addAction(OverlayAccessibilityService.ACTION_ENABLE_FLOATING_VIDEO);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private boolean isShowing() {
        return this.overlay.getWindowToken() != null;
    }

    public class OverlayBinder extends Binder {
        public OverlayService getService() {
            return OverlayService.this;
        }
    }

    private class OverlayBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(OverlayAccessibilityService.ACTION_DISABLE_FLOATING_VIDEO)) {
                hideOverlay();
            } else if (intent.getAction().equals(OverlayAccessibilityService.ACTION_ENABLE_FLOATING_VIDEO)) {
                showOverlay();
            }
        }
    }
}
