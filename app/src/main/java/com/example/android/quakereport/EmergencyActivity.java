package com.example.android.quakereport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EmergencyActivity extends AppCompatActivity{
    String LOG_TAG=EmergencyActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_activity);
        TextView content=findViewById(R.id.content);
        content.setText(R.string.emergency_info);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG,"OnCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i(LOG_TAG,"OnOptionsitemselected");
        if (id == R.id.action_emergency) {
            MenuItem menuItem=findViewById(R.id.action_emergency);
            menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.outline_toggle_on_black_18dp));
            Intent settingsIntent = new Intent(this, EmergencyActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
