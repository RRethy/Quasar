package com.bonnetrouge.quasar.Commons;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class Utility {

    /**
     * This method logs the component name of our package.
     * It lists all of the Accessibility Services and we can look at which on is ours.
     * It should only need to be used once since the file path shouldn't change
     * */
    public static void logInstalledAccessiblityServices(Context context) {
        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getInstalledAccessibilityServiceList();
        for (AccessibilityServiceInfo service : runningServices) {
            Log.i("AccessibilityServices", service.getId());
        }
    }

    /**
     * Use the method above to find the id that needs to be passed for this method
     * Currently, we pass: BuildConfig.APPLICATION_ID + "/.Services.SmartyFloatyAccessibilityService"
     * This will only ever change if we move the location of the Accessibility Service
     * */
    public static boolean isAccessibilityEnabled(Context context, String id) {
        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo service : runningServices) {
            if (id.equals(service.getId())) {
                return true;
            }
        }

        return false;
    }
}

