package com.pollux.sherpa;

import android.os.Bundle;
import android.widget.TextView;

import com.pollux.sherpa.messages.UserMessage;
import com.tooleap.sdk.TooleapActivities;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Shabaz on 06-Feb-16.
 */
public class FloatingActivity extends TooleapActivities.Activity
{
    @Bind(R.id.user_messge) TextView userMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }
    public void onEvent(UserMessage msg)
    {
        userMessage.setText(msg.getMessage());

    }
}
