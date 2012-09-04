package com.noon.xbmcremotecontrol.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;

public class MediaAbstract {
	/**
	 * Unique identifier within XBMC
	 */
	protected int id;
	
	/**
	 * Title of the Media
	 */
	protected String title;
	
	/**
	 * An image for the Media
	 */
	protected Bitmap image;
	
	public MediaAbstract(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	/**
	 * Get the XBMC ID for this Media
	 * 
	 * @return The XBMC ID
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Get the image associated with this Media
	 * 
	 * @return Image
	 */
	public Bitmap getImage() {
		return this.image;
	}
	
	/**
	 * Set the image associated with this Media
	 * @param image Image
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}
}
