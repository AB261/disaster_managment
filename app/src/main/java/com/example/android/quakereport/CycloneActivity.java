/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CycloneActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Cyclone>>   {

    private static final String LOG_TAG = CycloneActivity.class.getName();


    /**
     *
    /**
     * Constant value for the Cyclone loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private final String AERIS_REQUEST_URL =
            "https://api.aerisapi.com/tropicalcyclones?";
    private final String SERVER_URL="http://192.168.43.48:8000";
    String resp_string="";

    /**
     * Adapter for the list of cyclones
     */
    private CycloneAdapter mAdapter;
    MenuItem menuItem;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    private NetworkInfo networkInfo;
    private ListView CycloneListView;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        Log.i(LOG_TAG,"OnCreateStarted");

        // Find a reference to the {@link ListView} in the layout
        CycloneListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        CycloneListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of cyclones as input
        mAdapter = new CycloneAdapter(this, new ArrayList<Cyclone>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        CycloneListView.setAdapter(mAdapter);





        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        if(connMgr.getActiveNetwork()!=null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }




    public String SendServerReq(String lat,String longi,String curr_wind,String press)
    {

        RequestQueue queue = Volley.newRequestQueue(this);


    // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i(LOG_TAG,response);
                        resp_string=response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(LOG_TAG,"ERROR" + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("lat","10");
                hashMap.put("long","11");
                hashMap.put("wind","124");

                return hashMap;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return resp_string;
    }


    @Override
    public Loader<List<Cyclone>> onCreateLoader(int i, Bundle bundle) {
        String basin = "al";
        String invests = "invests" + "%5C%2C%20" + "al";

        Uri baseUri = Uri.parse(AERIS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("filter", basin);
        uriBuilder.appendQueryParameter("filter", invests);
        uriBuilder.appendQueryParameter("client_id", getString(R.string.aeris_client_id));
        uriBuilder.appendQueryParameter("client_secret", getString(R.string.aeris_secret_key));
        Log.i("EarthQuake Activity", uriBuilder.toString());

        Log.i(LOG_TAG,"OnCreateLoader");
        return new CycloneLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Cyclone>> loader, List<Cyclone> cyclones) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);
        Log.i(LOG_TAG,"OnLoadFinished");

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (cyclones != null && !cyclones.isEmpty()) {
            //mAdapter.addAll(earthquakes);

            List<Cyclone> new_cyclones =new ArrayList<>();

            for(int i=0;i<cyclones.size();i++)
            {
                Cyclone up_cyclone=cyclones.get(i);
                String resp=SendServerReq(String.valueOf(up_cyclone.getMlatitude()),String.valueOf(up_cyclone.getMlongitude()),String.valueOf(up_cyclone.getmCurr_windspeed()),String.valueOf(up_cyclone.getPressure()));
                String name=up_cyclone.getName();
                String basin=up_cyclone.getmBasin();
                long time=up_cyclone.getmTimeInMilliseconds();
                int longi=up_cyclone.getMlongitude();
                int lati=up_cyclone.getMlatitude();
                int curr_wind=up_cyclone.getmCurr_windspeed();
                int press=up_cyclone.getPressure();
                Cyclone temp;
                if(resp!=null) {
                     temp= new Cyclone(name, basin, time, longi, lati, resp, curr_wind, press);
                }
                else
                {
                    temp = new Cyclone(name, basin, time, longi, lati,curr_wind, press);
                }
                new_cyclones.add(temp);
            }
            //cyclones.clear();
            //updateUi(new_cyclones);
            updateUi(new_cyclones);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Cyclone>> loader) {
        Log.i(LOG_TAG,"OnLoadreset");
        mAdapter.clear();

    }
    public void loadCyclones()
    {

    }


    private void updateUi(List<Cyclone> earthquakes) {
        Log.i(LOG_TAG,"updateUI");
        mAdapter.clear();
        mAdapter.addAll(earthquakes);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG,"OnCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem=findViewById(R.id.action_emergency);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.i(LOG_TAG,"OnOptionsitemselected");
        if (id == R.id.action_emergency) {

            //menuItem.setIcon(getResources().getDrawable(R.drawable.outline_toggle_on_black_18dp));
            Intent settingsIntent = new Intent(this, EmergencyActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

