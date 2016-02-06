package com.pollux.sharpa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pollux.sharpa.utils.Constants;
import com.pollux.sharpa.utils.Helper;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapPopOutMiniApp;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.service_switch1) Button serviceSwitch;
    public static long miniAppId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(Helper.isAccessibilityEnabled(Constants.serviceId))
        {
            serviceSwitch.setText("Service : ON");
        }
        else
        {
            serviceSwitch.setText("Service : ON");
        }
        serviceSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS),0);
            }
        });
        Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(MainActivity.this, intent);
        miniApp.contentTitle = "Sherpa";
        miniApp.notificationText = "Hello! I'm Your Sherpa";
        miniApp.bubbleBackgroundColor = 0x78FFFFFF;
        Tooleap tooleap = Tooleap.getInstance(MainActivity.this);
        tooleap.removeAllMiniApps();
        miniAppId = tooleap.addMiniApp(miniApp);

    }
}
