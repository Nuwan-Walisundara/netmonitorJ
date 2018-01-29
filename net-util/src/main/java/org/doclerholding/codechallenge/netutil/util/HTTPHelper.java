package org.doclerholding.codechallenge.netutil.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;

public class HTTPHelper {
	// private static final String USER_AGENT = "Mozilla/5.0";

	private final String url;

	public HTTPHelper(final String url) {
		this.url = url;
	}

	public StringBuilder sendGET(final String url) throws IOException, MalformedURLException {

		URL obj = new URL(url);
		StringBuilder response = new StringBuilder();
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		// con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();

		System.out.println("GET Response Code :: " + responseCode);
		// success
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

		return response;

	}

	public void sendPOST(JsonObject jsonObject) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Content-Type", "application/json");

		con.setRequestProperty("Content-Language", "en-US");

		/**
		 * convert the jsonObject into byte array
		 */
		byte[] messagePayload;
		/*
		 * try(Writer writer = new StringWriter()) {
		 * Json.createWriter(writer).write(jsonObject); messagePayload =
		 * writer.toString().getBytes(); }
		 */

		messagePayload = jsonObject.toString().getBytes("UTF-8");
		con.setRequestProperty("Content-Length", Integer.toString(messagePayload.length));
		con.setDoInput(true);
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(messagePayload);
		os.flush();

		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			os.close();
		} else {
			System.out.println("POST request not worked");
		}
	}

	public void sendPOST(byte[] jsonObject) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Content-Type", "application/json");

		con.setRequestProperty("Content-Language", "en-US");

		/**
		 * convert the jsonObject into byte array
		 */
		con.setRequestProperty("Content-Length", Integer.toString(jsonObject.length));
		con.setDoInput(true);
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(jsonObject);
		os.flush();

		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			os.close();
		} else {
			System.out.println("POST request not worked");
		}
	}

}
