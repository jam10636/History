package com.example.kevintsou.image.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.fragment.LoginFragment;
import com.example.kevintsou.image.fragment.MapFragment;
import com.example.kevintsou.image.model.Model;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by KevinTsou on 3/24/2018.
 */

public class MapActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String eventid=getIntent().getStringExtra("eventid");
        Iconify.with(new FontAwesomeModule());
        Bundle bundle = new Bundle();
        bundle.putString("eventid",eventid);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.activitymapid);
        fragment=new MapFragment();
        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.activitymapid,fragment).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                ActionUp.startTopActivity(this,false);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
