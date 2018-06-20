package com.example.kevintsou.image.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.fragment.LoginFragment;
import com.example.kevintsou.image.fragment.MapFragment;
import com.example.kevintsou.image.model.Model;
import com.example.kevintsou.image.proxy.Proxy;

import java.io.IOException;

/**
 * Created by KevinTsou on 3/24/2018.
 */

public class SettingActivity extends AppCompatActivity{
    Spinner lifespinner;
    Switch lifeswitch;
    Spinner familyspinner;
    Switch familyswitch;
    Spinner spousespinner;
    Switch spouseswitch;
    Spinner mapspinner;
    LinearLayout resync;
    LinearLayout logout;
    Proxy proxy=new Proxy();
    String serverhost= "10.0.2.2";
    String serverport = "3000";
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lifespinner=(Spinner)findViewById(R.id.lifespinner);
        lifespinner.setAdapter(adapter);
        lifeswitch=(Switch)findViewById(R.id.lifeswitch);
        lifeswitch.setChecked(Model.instance().getSettings().getSwitches().get("lifestory"));
        lifeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Model.instance().getSettings().getSwitches().put("lifestory",true);
                }
                else
                {
                    Model.instance().getSettings().getSwitches().put("lifestory",false);
                }
            }
        });
        int spinnerPosition = adapter.getPosition(Model.instance().getSettings().getColortype().get("lifestory"));
        lifespinner.setSelection(spinnerPosition);
        lifespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = lifespinner.getSelectedItem().toString();
                Model.instance().getSettings().getColortype().put("lifestory",text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        familyspinner=(Spinner)findViewById(R.id.familytreespinner);
        familyswitch=(Switch)findViewById(R.id.familytreeswitch);
        familyswitch.setChecked(Model.instance().getSettings().getSwitches().get("familystory"));
        familyswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Model.instance().getSettings().getSwitches().put("familystory",true);
                }
                else
                {
                    Model.instance().getSettings().getSwitches().put("familystory",false);
                }
            }
        });
        familyspinner.setAdapter(adapter);
        spinnerPosition = adapter.getPosition(Model.instance().getSettings().getColortype().get("familystory"));
        familyspinner.setSelection(spinnerPosition);
        familyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = familyspinner.getSelectedItem().toString();
                Model.instance().getSettings().getColortype().put("familystory",text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        spousespinner=(Spinner)findViewById(R.id.spousespinner);
        spouseswitch=(Switch)findViewById(R.id.spouseswitch);
        spouseswitch.setChecked(Model.instance().getSettings().getSwitches().get("spousestory"));
        spouseswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Model.instance().getSettings().getSwitches().put("spousestory",true);
                }
                else
                {
                    Model.instance().getSettings().getSwitches().put("spousestory",false);
                }
            }
        });
        spousespinner.setAdapter(adapter);
        spinnerPosition = adapter.getPosition(Model.instance().getSettings().getColortype().get("spousestory"));
        spousespinner.setSelection(spinnerPosition);
        spousespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = spousespinner.getSelectedItem().toString();
                Model.instance().getSettings().getColortype().put("spousestory",text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        adapter=ArrayAdapter.createFromResource(this,R.array.map_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapspinner=(Spinner)findViewById(R.id.mapspinner);
        mapspinner.setAdapter(adapter);
         spinnerPosition = adapter.getPosition(Model.instance().getSettings().getDemaptype());
        mapspinner.setSelection(spinnerPosition);
        mapspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = mapspinner.getSelectedItem().toString();
                Model.instance().getSettings().setDemaptype(text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });


        resync=(LinearLayout)findViewById(R.id.resyncdata);
        resync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().clear();
                familytask task=new familytask();
                task.execute();
                ActionUp.startTopActivity(SettingActivity.this,true);
            }
        });

        logout=(LinearLayout)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().clear();
                Model.instance().setAuthtoken(null);
                ActionUp.startTopActivity(SettingActivity.this,true);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                Model.instance().getEventes().clear();
                ActionUp.startTopActivity(this,true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private class familytask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Model.instance().setPersonMod(proxy.personResult(serverhost,serverport,Model.instance().getAuthtoken(),
                        Model.instance().getPersonID()).getPersonMod());
                Model.instance().setPeoples(proxy.peopleResult(serverhost,serverport,Model.instance().getAuthtoken()).getPeople());
                Model.instance().setEvents(proxy.eventResult(serverhost,serverport,Model.instance().getAuthtoken()).getEventslist());
                Model.instance().getFilter().doevent();
                Model.instance().getFilter().setfilter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onPostExecute(Void v)
        {
        }
    }
}
