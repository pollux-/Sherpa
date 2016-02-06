package com.pollux.sherpa.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.widget.TextView;

import com.pollux.sherpa.MainActivity;
import com.pollux.sherpa.messages.UserMessage;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapMiniApp;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Shabaz on 06-Feb-16.
 */
public class SherpaAccesibility extends AccessibilityService
{
    final static String  tag= SherpaAccesibility.class.getSimpleName();
    final static int WHATSAPP=1,MESSENGER = 3, SMS = 2;
    @Override
    public void onInterrupt() {
    }
    private String getEventType(AccessibilityEvent event)
    {
        switch (event.getEventType())
        {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                return "TYPE_VIEW_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                return "TYPE_VIEW_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                return "TYPE_VIEW_LONG_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_VIEW_SELECTED";
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                return "TYPE_WINDOW_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                return "TYPE_VIEW_HOVER_ENTER";
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                return "TYPE_VIEW_HOVER_EXIT";
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                return "TYPE_WINDOW_CONTENT_CHANGED";
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                return "TYPE_WINDOWS_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
        }
        return "default not tracked "+event.getEventType();
    }
    ArrayList<String> text = new ArrayList<>();
    int app = -1;
    boolean messageBug = false,repeatEntry = false;
    String currentActivity;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)
    {

        //eventText = eventText + event.getContentDescription();

        String message = "";
        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
        {
            getTextFromNode(event.getSource());
            Log.d("WINDOW_CHANGED",text.toString());
            currentActivity = event.getClassName().toString();
            switch (event.getPackageName().toString())
            {
                case "com.whatsapp":
                    app = WHATSAPP;
                    repeatEntry = false;
                    break;
                case "com.facebook.orca":
                    app = MESSENGER;
                    repeatEntry = false;
                    break;
                case "com.android.mms":
                    app=SMS;
                    if(messageBug )
                    {
                        performGlobalAction(GLOBAL_ACTION_BACK);
                        messageBug = false;
                    }
                    repeatEntry = true;
                    break;
                default:
                    repeatEntry = false;
                    app = -1;

            }
        }
        else
        {
            messageBug = false;
            text = new ArrayList<>();
            if(app==WHATSAPP && event.getEventType() == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED)
            {
                getTextFromNode(event.getSource());
                Log.d(tag, "Whats App Length = " + text.size());
                if(text.size()==3)
                {
                    message = text.get(1);
                }
                else if(text.size()==2)
                {
                    message = text.get(0);
                } else if(text.size()==4)
                {
                    message = text.get(2);
                }
            }

            else if((app==MESSENGER && event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED)
                    && currentActivity.equals("com.facebook.messenger.neue.MainActivity"))
            {
                getTextFromNode(event.getSource());
                Log.d(tag, "Whats App Length = " + text.size());
                if(text.size()>=1)
                    message = text.get(0);
            }
            else if((app==SMS && event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED)
                    && currentActivity.equals("com.android.mms.ui.SingleRecipientConversationActivity"))
            {
                getTextFromNode(event.getSource());
                Log.d(tag, "SMS APP Length = " + text.size());
                if(text.size()>=2)
                {
                    message = text.get(1);
                    messageBug = true;
                }
            }
            else
            {
                String ussd_details = String
                        .format("onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                                getEventType(event), event.getClassName(),
                                event.getPackageName(), event.getEventTime(), text.toString());
                Log.d(tag, ussd_details);
                return;
            }
        }
            String ussd_details = String
                    .format("onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                            getEventType(event), event.getClassName(),
                            event.getPackageName(), event.getEventTime(), text.toString());
            AccessibilityRecord m;
            Log.d(tag, ussd_details);
            Log.d(tag, text.toString());
            Log.d(tag, message);
        Tooleap tooleap = Tooleap.getInstance(this);
        TooleapMiniApp miniApp = tooleap.getMiniApp(MainActivity.miniAppId);
        miniApp.notificationText = message;
        tooleap.updateMiniAppAndNotify(MainActivity.miniAppId, miniApp);
        EventBus.getDefault().post(new UserMessage(message));
    }
    public void onEvent()
    {

    }
    public void getTextFromNode(AccessibilityNodeInfo accessibilityNodeInfo)
    {

        StringBuilder sb = new StringBuilder();
        if (accessibilityNodeInfo == null)
        {
            ////V10Log.d("TEST", "accessibilityNodeInfo is null");
            return ;
        }

        int j = accessibilityNodeInfo.getChildCount();
        Log.d(tag,"Number OObjects = "+j);
        //if(j==3)
        ////V10Log.d("TEST", "number of children = " + j);
        for (int i = 0; i < j; i++)
        {

            AccessibilityNodeInfo ac = accessibilityNodeInfo.getChild(i);

            if (ac == null)
            {
                Log.d(tag,"ac is null");
                continue;
            }
            if (ac.getChildCount() > 0)
            {
                Log.d(tag, "More than one subchild " + ac.getChildCount());
                Log.d(tag, "Text  " + ac.getText());
                getTextFromNode(ac);
            }
            Log.d(tag,ac.getClassName()+"");
            Log.d(tag, "Text  " + ac.getText());
            if (ac.getClassName().equals(TextView.class.getName()))
            {
                if(ac.getText()!=null)
                    text.add(ac.getText().toString());
                ////V10Log.d("TEST", "Number:" + i + "   " + sb);
            }

        }
    }

    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        info.flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
                |AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
                |AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
                |AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;
        info.packageNames = null;
        info.packageNames = new String[]{"com.whatsapp","com.facebook.orca","com.android.mms"};
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |AccessibilityEvent.TYPE_VIEW_LONG_CLICKED|AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        //EventBus.getDefault().register(this);


    }
}
