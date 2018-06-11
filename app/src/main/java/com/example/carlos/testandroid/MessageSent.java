package com.example.carlos.testandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MessageSent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_sent);
    }

    public void OnBackBtnClick(View v)
    {
        Intent i = new Intent(getBaseContext(), MainActivity.class);

        i.putExtra("tab", 0);

        startActivity(i);
        finish();
    }

    public void OnNewMsgBtnClick(View v)
    {
        Intent i = new Intent(getBaseContext(), MainActivity.class);

        i.putExtra("tab", 1);

        startActivity(i);
        finish();
    }
}
