package com.example.carlos.testandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private static String url = "https://floating-mountain-50292.herokuapp.com/cells.json";

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Intent intentExtras = getIntent();
        int tabToActivate = intentExtras.getIntExtra("tab", 0);

        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("invest").setIndicator("Investimento", null),
                InvestimentTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("contact").setIndicator("Contato", null),
                ContactTab.class, null);

        mTabHost.setCurrentTab(tabToActivate);

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            v.setBackgroundResource(R.drawable.tab_red);

            TextView tv = mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }

    }
}
