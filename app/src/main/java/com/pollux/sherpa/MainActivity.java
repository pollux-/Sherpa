package com.pollux.sherpa;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.pollux.sherpa.fragment.FlightFragment;
import com.pollux.sherpa.fragment.PlaceFragment;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapPopOutMiniApp;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int PLACE = 100;
    public static final int FLIGHT = 200;
    public static long miniAppId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);


        replaceFragment(PLACE);

        Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(MainActivity.this, intent);
        miniApp.contentTitle = "Sherpa";
        miniApp.notificationText = "Hello! I'm Your Sherpa";
        miniApp.bubbleBackgroundColor = 0x78FFFFFF;
        Tooleap tooleap = Tooleap.getInstance(MainActivity.this);
        tooleap.removeAllMiniApps();
        miniAppId = tooleap.addMiniApp(miniApp);




    }



    private void replaceFragment(int type){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment =null;
        switch (type){

            case PLACE:
                fragment = new PlaceFragment();
                break;
            case FLIGHT:
                fragment = new FlightFragment();
                break;
        }

        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }


}
