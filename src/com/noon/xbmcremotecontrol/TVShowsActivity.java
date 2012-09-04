package com.noon.xbmcremotecontrol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.noon.xbmcremotecontrol.api.VideoLibrary;
import com.noon.xbmcremotecontrol.data.TVShow;

public class TVShowsActivity extends Activity {

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			new GetTVShowsTask().execute();
			break;
		}

		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvshows);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_tvshows, menu);
		return true;
	}

	protected void updateTVShowList(JSONObject tvShowList) {
		try {
			JSONArray tvShows = tvShowList.getJSONObject("result").getJSONArray("tvshows");

			TVShow[] tvShowArray = new TVShow[tvShows.length()];
			for(int i=0; i < tvShows.length(); i++) {
				JSONObject tvShow = tvShows.getJSONObject(i);
				tvShowArray[i] = new TVShow(tvShow.getInt("tvshowid"), tvShow.getString("label"));
			}

			ListView listView = (ListView) findViewById(R.id.listViewTVShows);
			listView.setBackgroundColor(Color.BLACK);
			ArrayAdapter<TVShow> adapter = new ArrayAdapter<TVShow>(getApplicationContext(), android.R.layout.simple_list_item_1, tvShowArray);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TVShow tvShow = (TVShow) ((ArrayAdapter<TVShow>)parent.getAdapter()).getItem(position);
					Toast.makeText(getApplicationContext(), tvShow.toString(), Toast.LENGTH_SHORT).show();
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class GetTVShowsTask extends AsyncTask<Void, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Void... params) {
			return (new VideoLibrary().getTVShows());
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			updateTVShowList(result);
		}
	}
}
