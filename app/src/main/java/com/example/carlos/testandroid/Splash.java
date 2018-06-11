package com.example.carlos.testandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                super.run();

                while(AppController.getInstance().GetContactJson() == null ||
                        AppController.getInstance().GetInvestJson() == null)
                {
                    try {
                        sleep(10);
                    } catch (Exception e) {
                    }
                }

                Intent i = new Intent(getBaseContext(), MainActivity.class);

                i.putExtra("tab", 0);

                startActivity(i);
                finish();
            }
        };
        welcomeThread.start();
    }
}
