package com.pollux.sherpa.services;

/**
 * Created by Shabaz on 07-Feb-16.
 */

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.pollux.sherpa.DetailsActivity;
import com.pollux.sherpa.R;
import com.pollux.sherpa.messages.CloseDetails;
import com.pollux.sherpa.messages.UserMessage;
import com.pollux.sherpa.utils.MyApplication;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

import de.greenrobot.event.EventBus;

public class ServiceFloating extends Service implements BubbleLayout.OnBubbleClickListener {

    final static String TAG = ServiceFloating.class.getSimpleName();

    private Boolean enable = false;
    private WindowManager.LayoutParams params;
    private BubbleLayout bubbleView;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        initializeBubblesManager();
        EventBus.getDefault().register(this);
        /*super.onCreate();
        Log.d(TAG,"SERVICE STARTED") ;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);

        chatHead.setImageResource(R.drawable.floating_icon);

        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(chatHead, params);

        try
        {
            chatHead.setOnTouchListener(new View.OnTouchListener()
            {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:

                            // Get current time in nano seconds.
                            long pressTime = System.currentTimeMillis();


                            // If double click...
                            if (pressTime - lastPressTime <= 300)
                            {
                                createNotification();
                                ServiceFloating.this.stopSelf();
                                mHasDoubleClicked = true;
                            } else
                            {     // If not double click....
                                mHasDoubleClicked = false;
                            }
                            lastPressTime = pressTime;
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(chatHead, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
            // TODO: handle exception
        }

        chatHead.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                launchDetails();
                initiatePopupWindow(chatHead);
                //				Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                //				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //				getApplicationContext().startActivity(intent);
            }
        });*/

    }

    private BubblesManager bubblesManager;

    private void addNewBubble() {
        if (bubbleView != null) return; //only one bubble at a time
        bubbleView = (BubbleLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.bubble_layout, null);
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {
                EventBus.getDefault().post(new CloseDetails());
            }
        });
        bubbleView.setOnBubbleClickListener(this);
        bubbleView.setShouldStickToWall(true);

        bubblesManager.addBubble(bubbleView, 0, 20);
        //windowManager.updateViewLayout(bubbleView,params);

    }

    private void initializeBubblesManager() {

        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.x = 0;
        params.y = 20;
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                        addNewBubble();
                    }
                })
                .build();
        bubblesManager.initialize();
    }

    public void onEvent(UserMessage msg) {
        openActivity(msg.getMessage());
    }

    /* private void launchDetails()
     {
         if(enable)
         {
             EventBus.getDefault().post(new CloseDetails());

             enable = false;
         }
         else
         {
             params.gravity = Gravity.TOP | Gravity.LEFT;
             params.x = 0;
             params.y = 100;
             windowManager.updateViewLayout(chatHead, params);
             Intent mIntent = new Intent(MyApplication.context, DetailsActivity.class);
             mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
             startActivity(mIntent);

             enable = true;

         }
     }


     private void initiatePopupWindow(View anchor)
     {
         try
         {
             Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
             ListPopupWindow popup = new ListPopupWindow(this);
             popup.setAnchorView(anchor);
             popup.setWidth((int) (display.getWidth() / (1.5)));
             //ArrayAdapter<String> arrayAdapter =
             //new ArrayAdapter<String>(this,R.layout.list_item, myArray);


         } catch (Exception e)
         {
             e.printStackTrace();
         }
     }

     public void createNotification()
     {
        *//* Intent notificationIntent = new Intent(getApplicationContext(), ServiceFloating.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, notificationIntent, 0);

        Notification notification = new Notification(R.mipmap.floating_icon, "Click to start launcher",System.currentTimeMillis());
        notification.(getApplicationContext(), "Start launcher" ,  "Click to start launcher", pendingIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT;

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(ID_NOTIFICATION,notification);*//*
    }
*/
    @Override
    public void onDestroy() {

        bubblesManager.recycle();
        super.onDestroy();
//        if (chatHead != null) windowManager.removeView(chatHead);
    }

    int oldx = 0, oldy = 20;

    @Override
    public void onBubbleClick(BubbleLayout bubble) {
        Log.d(TAG, "Clicked");
        openActivity("GOA");

    }

    void openActivity(String text) {
        if (enable) {
            EventBus.getDefault().post(new CloseDetails());

            enable = false;
        } else {

            Intent mIntent = new Intent(MyApplication.context, DetailsActivity.class);
            mIntent.putExtra(DetailsActivity.BUNDLE_MESSAGE, text);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(mIntent);
            /*bubbleView.setX(0);
            bubbleView.setX(20);*/
            enable = true;

        }
    }
}