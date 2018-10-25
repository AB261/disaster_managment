package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class CycloneLoader extends AsyncTaskLoader<List<Cyclone>> {
    private static final String LOG_TAG = CycloneLoader.class.getName();
    private String mUrl;


    public CycloneLoader(Context context, String url) {
        super(context);
        mUrl = url;


    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Cyclone> loadInBackground() {
        if (mUrl == null) {
            Log.i("Cyclone loader", "url not sent");
            return null;
        } else {
            Log.i("Cyclone loader", "url sent to query");
            List<Cyclone> cyclones = QueryUtils.fetchCycloneData(mUrl);

            return cyclones;
        }
    }
}
