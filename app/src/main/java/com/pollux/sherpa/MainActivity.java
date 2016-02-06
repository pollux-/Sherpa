package com.pollux.sherpa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.pollux.sherpa.fragment.FlightFragment;
import com.pollux.sherpa.fragment.PlaceFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int PLACE = 100;
    public static final int FLIGHT = 200;
    public static long miniAppId = -1;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);


        replaceFragment(PLACE);
        setListeners();


       /* Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(MainActivity.this, intent);
        miniApp.contentTitle = "Sherpa";
        miniApp.notificationText = "Hello! I'm Your Sherpa";
        miniApp.bubbleBackgroundColor = 0x78FFFFFF;
        Tooleap tooleap = Tooleap.getInstance(MainActivity.this);
        tooleap.removeAllMiniApps();
        miniAppId = tooleap.addMiniApp(miniApp);
        */




    }

    private void setListeners() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

        // Checked change Listener for RadioGroup 1
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.places:
                        replaceFragment(PLACE);
                        break;
                    case R.id.flights:
                        replaceFragment(FLIGHT);
                        break;
                    default:
                        break;
                }
            }
        });

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
