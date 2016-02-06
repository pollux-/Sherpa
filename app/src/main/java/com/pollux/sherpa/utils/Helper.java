package com.pollux.sherpa.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

/**
 * Created by Shabaz on 06-Feb-16.
 */
public class Helper
{
    public static final String TAG = Helper.class.getSimpleName();
    public static Boolean isAccessibilityEnabled(String id)
    {
        AccessibilityManager mAccessibilityManager = (AccessibilityManager) MyApplication.context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        Log.d(TAG,"Checking for: "+id);
        List<AccessibilityServiceInfo> runningServices = mAccessibilityManager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        Log.d(TAG, "size of ruuning services : " + runningServices.size());
        for (AccessibilityServiceInfo service : runningServices)
        {
            Log.d(TAG, service.getId());
            if (id.equals(service.getId()))
            {
                return true;
            }
        }

        return false;
    }
}
