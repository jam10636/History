package com.example.kevintsou.image.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.fragment.LoginFragment;
import com.example.kevintsou.image.fragment.MapFragment;
import com.example.kevintsou.image.model.Model;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
        if(Model.instance().getAuthtoken()==null)
        {
            fragment=new LoginFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        else
        {
            startmap();
        }
    }
    public void startmap()
    {
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
        fragment=new MapFragment();
        fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}

