package com.noon.xbmcremotecontrol.api;

import org.json.JSONObject;

public class VideoLibrary extends APIBase {
	
	public JSONObject getMovies() {
		return this.execute("VideoLibrary.GetMovies");
	}
	
	public JSONObject getTVShows() {
		return this.execute("VideoLibrary.GetTVShows");
	}
}
