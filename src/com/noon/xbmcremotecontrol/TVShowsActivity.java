package com.noon.xbmcremotecontrol;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TVShowsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tvshows, menu);
        return true;
    }
}
