package com.noon.xbmcremotecontrol;

import java.io.IOException;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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

import com.noon.xbmcremotecontrol.data.Movie;

public class MoviesActivity extends Activity {

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			new GetMoviesTask().execute();
			break;
		}

		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_movies, menu);
		return true;
	}

	protected JSONObject getMovieList() {
		HttpClient client = new DefaultHttpClient();

		try {
			HttpPost post = new HttpPost("http://192.168.1.134/jsonrpc");
			post.addHeader("Content-type", "application/json");

			UsernamePasswordCredentials creds = new UsernamePasswordCredentials("xbmc", "xbmc");
			post.addHeader(new BasicScheme().authenticate(creds, post));

			JSONObject payload = new JSONObject();
			try {
				payload.putOpt("id", "1");
				payload.putOpt("jsonrpc", "2.0");
				payload.putOpt("method", "VideoLibrary.GetMovies");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			post.setEntity(new StringEntity(payload.toString()));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				return new JSONObject(client.execute(post, responseHandler));
			} catch (JSONException e) {
				e.printStackTrace();
				return new JSONObject();
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}

		return new JSONObject();
	}

	protected void updateMovieList(JSONObject movieList) {
		try {
			JSONArray movies = movieList.getJSONObject("result").getJSONArray("movies");

			Movie[] movieArray = new Movie[movies.length()];
			for(int i=0; i < movies.length(); i++) {
				JSONObject movie = movies.getJSONObject(i);
				movieArray[i] = new Movie(movie.getInt("movieid"), movie.getString("label"));
			}

			ListView listView = (ListView) findViewById(R.id.listViewMovies);
			listView.setBackgroundColor(Color.BLACK);
			ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(getApplicationContext(), android.R.layout.simple_list_item_1, movieArray);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Movie movie = (Movie) ((ArrayAdapter<Movie>)parent.getAdapter()).getItem(position);
					
					Intent intent = new Intent(getApplicationContext(), MediaInfoActivity.class);
					intent.putExtra(MediaInfoActivity.MEDIA_TITLE, movie.toString());
					startActivity(intent);
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class GetMoviesTask extends AsyncTask<Void, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Void... params) {
			return getMovieList();
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			updateMovieList(result);
		}
	}
}
