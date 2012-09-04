package com.noon.xbmcremotecontrol;

import java.io.InputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.noon.xbmcremotecontrol.api.VideoLibrary;
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
					
					if (movie.getImage() == null) {
						new GetMovieDetailsTask(movie).execute();
					} else {
						showMovieDetails(movie);
					}
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void showMovieDetails(Movie movie) {
		Intent intent = new Intent(getApplicationContext(), MediaInfoActivity.class);
		intent.putExtra(MediaInfoActivity.MEDIA_TITLE, movie.toString());
		startActivity(intent);
	}

	private class GetMoviesTask extends AsyncTask<Void, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Void... params) {
			return (new VideoLibrary().getMovies());
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			updateMovieList(result);
		}
	}
	
	private class GetMovieDetailsTask extends AsyncTask<Void, Void, JSONObject> {
		
		private Movie movie;
		
		public GetMovieDetailsTask(Movie movie) {
			this.movie = movie;
		}
		
		@Override
		protected JSONObject doInBackground(Void... params) {
			return (new VideoLibrary().getMovieDetails(movie.getId()));
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			String thumbUrl;
			Bitmap image = null;
			try {
				thumbUrl = result.getJSONObject("result").getJSONObject("moviedetails").getString("thumbnail");
				InputStream in = new URL(thumbUrl).openStream();
				image = BitmapFactory.decodeStream(in);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.movie.setImage(image);
			
			showMovieDetails(this.movie);
		}
		
	}
}
