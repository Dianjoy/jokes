package com.jokes.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetRequest {
	public static String connectToURL(String url, String params) {
		return connectToURL(url, params, 60* 1000, 40 * 1000);//6 * 1000, 4 * 1000
	}

	private static String connectToURL(String url, String params, int conOutT, int readOutT) {
		String httpResponse = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			String requestURL = url + params;
			requestURL = requestURL.replaceAll(" ", "%20");
			URL noeUrl = new URL(requestURL);
			connection = (HttpURLConnection) noeUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setConnectTimeout(conOutT);
			connection.setReadTimeout(readOutT);
			if (connection.usingProxy()) {
			}
			if (connection.getResponseCode() < 400) {

				reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				for (String line; (line = reader.readLine()) != null;) {
					sb.append(line);
				}
				httpResponse = sb.toString();
			}
		} catch (Exception e) {
			httpResponse="";
			//e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}

		return httpResponse;
	}

	String connectToURLPost(String url, String params, String post) {
		return connectToURLPost(url, params, post, 5 * 1000, 3 * 1000);
	}

	String connectToURLPost(String url, String params, String post,
			int conOutT, int readOutT) {
		String httpResponse = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			String requestURL = url + params;
			requestURL = requestURL.replaceAll(" ", "%20");
			URL noeUrl = new URL(requestURL);
			connection = (HttpURLConnection) noeUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setConnectTimeout(conOutT);
			connection.setReadTimeout(readOutT);

			Writer writer = null;
			OutputStream out = connection.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(post);
			writer.flush();

			if (connection.usingProxy()) {
			}
			if (connection.getResponseCode() < 400) {

				reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				for (String line; (line = reader.readLine()) != null;) {
					sb.append(line);
				}
				httpResponse = sb.toString();
			}
		} catch (Exception e) {
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}

		return httpResponse;
	}

}
