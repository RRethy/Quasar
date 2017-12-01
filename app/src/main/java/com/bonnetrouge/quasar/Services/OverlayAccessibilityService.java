package com.bonnetrouge.quasar.Services;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

public class OverlayAccessibilityService extends AccessibilityService {

    private static final int EVENT_TYPE_ACTION_WINDOW = 32;
    public static final int ACCESSIBILITY_REQUEST_CODE = 1867;
    public static final String PACKAGE_NAME = "com.bonnetrouge.quasar.services";
    public static final String ACCESSIBILITY_ID = PACKAGE_NAME + "/.Services.OverlayAccessibilityService";
    public static final String ACTION_DISABLE_FLOATING_VIDEO = "Disable Overlay";
    public static final String ACTION_ENABLE_FLOATING_VIDEO = "Enable Overlay";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        if (eventType == EVENT_TYPE_ACTION_WINDOW) {
            try {
                String packageName = (String) event.getPackageName();
                String className = (String) event.getClassName();
                if (!packageName.equals(PACKAGE_NAME)) {
                    if (packageName.equals("com.google.android.packageinstaller")
                            || packageName.equals("com.android.packageinstaller")
                            || packageName.equals("com.android.backupconfirm")
                            || packageName.equals("com.android.settings.cyanogenmod.superuser.MultitaskSuRequestActivity")
                            || ((packageName.equals("com.android.systemui") && className.equals("com.android.systemui.media.MediaProjectionPermissionActivity"))
                            || ((Build.VERSION.SDK_INT < 24 && packageName.equals("com.android.systemui") && className.equals("android.app.AlertDialog"))
                            || (packageName.equals("com.android.settings") && className.equals("android.app.AlertDialog"))))) {
                        Intent intent = new Intent(ACTION_DISABLE_FLOATING_VIDEO);
                        sendBroadcast(intent);
                    } else {
                        Intent intent = new Intent(ACTION_ENABLE_FLOATING_VIDEO);
                        sendBroadcast(intent);
                    }
                }
            } catch (Exception e) {
                // Nothing needs to be done if it fails
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
