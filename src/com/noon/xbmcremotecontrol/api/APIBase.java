package com.noon.xbmcremotecontrol.api;

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
import org.json.JSONException;
import org.json.JSONObject;

public class APIBase {
	
	protected JSONObject execute(String method, JSONObject params) {
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
				payload.putOpt("method", method);
				payload.putOpt("params", params);
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
}
