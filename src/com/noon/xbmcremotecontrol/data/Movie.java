package com.noon.xbmcremotecontrol.data;

/**
 * @author Adam Noon
 *
 */
public class Movie extends MediaAbstract {
	
	/**
	 * Construct a Movie
	 * 
	 * @param id ID within XBMC
	 * @param title Title of the Movie
	 */
	public Movie(int id, String title) {
		super(id, title);
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
}
