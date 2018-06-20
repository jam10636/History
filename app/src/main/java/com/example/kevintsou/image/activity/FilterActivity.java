package com.example.kevintsou.image.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.model.Members;
import com.example.kevintsou.image.model.Model;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import shared.model.EventMod;
import shared.model.PersonMod;

/**
 * Created by KevinTsou on 3/24/2018.
 */

public class FilterActivity extends AppCompatActivity {
    TextView upper;
    TextView lower;
    RecyclerView filterview;
    LinearLayoutManager filtermanager;
    myAdapter myAdapter;
    Switch mySwitch;
    ArrayList<String> Filterlist;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Filterlist=new ArrayList<>(Model.instance().getFilter().getEventfilter().keySet());
        mySwitch=(Switch)findViewById(R.id.swtiches);
        upper=(TextView) findViewById(R.id.upperfilter);
        lower=(TextView) findViewById(R.id.lowerfilter);
        filterview=(RecyclerView)findViewById(R.id.filterrecycler) ;
        filtermanager=new LinearLayoutManager(this);
        filterview.setLayoutManager(filtermanager);
        myAdapter=new myAdapter(Filterlist);
        filterview.setAdapter(myAdapter);
        Model.instance().getside();
    }
    public class viewHolder extends RecyclerView.ViewHolder
    {
        protected Switch aSwitch;
        protected TextView uppertext;
        protected TextView lowertext;
        public viewHolder(View v)
        {
            super(v);

            aSwitch=(Switch) v.findViewById(R.id.swtiches);
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        Model.instance().getFilter().getEventfilter().put(Filterlist.get(getAdapterPosition()),true);
                        Log.v("h","checked");
                    }
                    else
                    {
                        Log.v("s","unchecked");
                        Model.instance().getFilter().getEventfilter().put(Filterlist.get(getAdapterPosition()),false);
                    }
                }
            });
            uppertext=(TextView)v.findViewById(R.id.upperfilter);
            lowertext=(TextView)v.findViewById(R.id.lowerfilter);
        }
    }
    public class myAdapter extends RecyclerView.Adapter<viewHolder> {
        private ArrayList<String>fmembers;
        public myAdapter(ArrayList<String> memberin) {
            fmembers = memberin;
        }
        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.filter_viewholder, parent, false);
            viewHolder vh = new viewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {
            String in=fmembers.get(position);
            if(Model.instance().getFilter().getEventfilter().get(fmembers.get(position))==true)
            {
                viewHolder.aSwitch.setChecked(true);
            }
            else
            {
                viewHolder.aSwitch.setChecked(false);
            }
            viewHolder.uppertext.setText(in);
            viewHolder.lowertext.setText("Filter by "+in);
        }

        @Override
        public int getItemCount()
        {
            return fmembers.size();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                Model.instance().getEventes().clear();
                Model.instance().getFilter().setfilter();
                ActionUp.startTopActivity(this,true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
