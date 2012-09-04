package com.noon.xbmcremotecontrol.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoLibrary extends APIBase {
	
	public JSONObject getMovies() {
		return this.execute("VideoLibrary.GetMovies", null);
	}
	
	public JSONObject getMovieDetails(int movieId) {
		
		JSONObject params = new JSONObject();
		try {
			params.put("movieid", movieId);
			JSONArray properties = new JSONArray();
			properties.put("thumbnail");
			params.put("properties", properties);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return this.execute("VideoLibrary.GetMovieDetails", params);
	}
	
	public JSONObject getTVShows() {
		return this.execute("VideoLibrary.GetTVShows", null);
	}
}
