package com.noon.listview.xbmcremotecontrol;

/**
 * @author Adam Noon
 *
 */
public class Movie {
	
	/**
	 * The title of the movie
	 */
	protected String title;
	/**
	 * Build a new object representing a single movie
	 * 
	 * @param title The title of the movie
	 */
	public Movie(String title) {
		this.title = title;
	}
	
	/**
	 * Return the string to be rendered
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.title;
	}
	
	public String getTitle() {
		return this.title;
	}
}
