package com.noon.xbmcremotecontrol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class MediaInfoActivity extends Activity {

	public final static String MEDIA_TITLE = "com.noon.xbmcremotecontrol.MEDIA_TITLE";
	public final static String MEDIA_IMAGE = "com.noon.xbmcremotecontrol.MEDIA_IMAGE";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_info);
        
        Intent intent = getIntent();
        String title = intent.getStringExtra(MediaInfoActivity.MEDIA_TITLE);
        this.findViewById(R.id.mediaTitle);
        
        TextView textViewTitle = (TextView) findViewById(R.id.mediaTitle);
        textViewTitle.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_media_info, menu);
        return true;
    }
}
