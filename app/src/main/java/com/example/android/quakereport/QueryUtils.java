package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    public static List<Cyclone> fetchCycloneData(String url) {
        URL murl = createUrl(url);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(murl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s

        // Return the list of {@link Cyclones}s
        return extractfeaturesfromJson(jsonResponse);
    }

    private static List<Cyclone> extractfeaturesfromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Cyclone> cyclones = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray response = baseJsonResponse.getJSONArray("response");


            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < response.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentCyclone = response.getJSONObject(i);
                JSONObject profile = currentCyclone.getJSONObject("profile");
                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.

                String name = profile.getString("name");

                // Extract the value for the key called "mag"
                String basin = profile.getString("basinCurrent");

                // Extract the value for the key called "place"
                JSONObject position = currentCyclone.getJSONObject("position");
                JSONObject location = position.getJSONObject("loc");
                JSONObject lifespan = profile.getJSONObject("lifespan");
                int longitude = location.getInt("long");
                int latitude = location.getInt("lat");

                // Extract the value for the key called "time"

                long time = lifespan.getLong("startTimestamp");

                // Extract the value for the key called "url"
                Cyclone cyclone = new Cyclone(name, basin, time, longitude, latitude);
                // Create a new {@link Earthquake} object with the magnitude, location, time
                cyclones.add(cyclone);

                // Add the new {@link Earthquake} to the list of earthquakes.

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return cyclones;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringurl) {
        URL murl = null;
        try {
            murl = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error making url from string", e);
        }
        return murl;
    }
}
