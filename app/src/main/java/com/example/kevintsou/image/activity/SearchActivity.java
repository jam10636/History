package com.example.kevintsou.image.activity;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.model.Event;
import com.example.kevintsou.image.model.Members;
import com.example.kevintsou.image.model.Model;
import com.example.kevintsou.image.model.Person;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.io.Serializable;
import java.util.ArrayList;

import shared.model.EventMod;
import shared.model.PersonMod;

/**
 * Created by KevinTsou on 3/28/2018.
 */

public class SearchActivity extends AppCompatActivity {
    EditText search;
    RecyclerView searchbar;
    ArrayList<PersonMod>members=new ArrayList<>();
    ArrayList<EventMod>events=new ArrayList<>();
    LinearLayoutManager peoplemanager;
    MyAdapter myAdapter;
    Drawable androidGenderIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search=(EditText)findViewById(R.id.search_bar);
        searchbar=(RecyclerView)findViewById(R.id.searchrecycle) ;
        peoplemanager=new LinearLayoutManager(this);
        searchbar.setLayoutManager(peoplemanager);
        myAdapter=new MyAdapter(members,events);
        searchbar.setAdapter(myAdapter);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

                String text=search.getText().toString();
                search(text);
                myAdapter=new MyAdapter(members,events);
                if(text.equals(""))
                {
                    members.clear();
                    events.clear();
                }
                searchbar.setAdapter(myAdapter);
            }

        });
    }
    public void search(String text)
    {
        members.clear();
        events.clear();
        for(EventMod e:Model.instance().getEvents())
        {
            if(e.getEventType().toLowerCase().contains(text.toLowerCase())||e.getCountry().toLowerCase().contains(text.toLowerCase())||
                    e.getCity().toLowerCase().contains(text.toLowerCase())||
                    e.getYear().toLowerCase().contains(text.toLowerCase()))
            {
                events.add(e);
            }
        }
        for(PersonMod p:Model.instance().getPeoples()) {
            if (p.getFirstName().toLowerCase().contains(text.toLowerCase()) || p.getLastName().toLowerCase().contains(text.toLowerCase()))
            {
                members.add(p);
            }
        }
    }
    public class eventHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            String eventid=events.get(getAdapterPosition()-members.size()).getEventID();
            Intent intent = new Intent (SearchActivity.this, MapActivity.class);
            intent.putExtra("eventid", eventid);
            startActivity(intent);
        }

        protected ImageView icon;
        protected TextView uppertext;
        protected TextView lowertext;
        public eventHolder(View v)
        {
            super(v);
            icon=(ImageView)v.findViewById(R.id.imageinview);
            uppertext=(TextView)v.findViewById(R.id.uppertext);
            lowertext=(TextView)v.findViewById(R.id.lowertext);
            v.setOnClickListener(this);
        }
        public void populate(EventMod eventMod){
            String in=eventMod.getEventType()+": "+eventMod.getCity()+","+eventMod.getCountry()+"("+eventMod.getYear()+")";
            uppertext.setText(in);
            PersonMod p=Model.instance().getPerson().get(eventMod.getPersonID());
            in=p.getFirstName()+" "+p.getLastName();
            Log.v("here",in);
            lowertext.setText(in);
            androidGenderIcon = new IconDrawable(SearchActivity.this,  FontAwesomeIcons.fa_map_marker)
                    .colorRes(R.color.colorAccent).sizeDp(40);
            icon.setImageDrawable(androidGenderIcon);
        }
    }
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        protected ImageView icon;
    protected TextView uppertext;
    protected TextView lowertext;
    public viewHolder(View v)
    {
        super(v);
        icon=(ImageView)v.findViewById(R.id.imageinview);
        uppertext=(TextView)v.findViewById(R.id.uppertext);
        lowertext=(TextView)v.findViewById(R.id.lowertext);
        v.setOnClickListener(this);
    }
        public void populate(PersonMod personMod){
            String in=personMod.getFirstName()+" "+personMod.getLastName();
            uppertext.setText(in);
            lowertext.setText("");
            if(personMod.getGender().equals("m"))
            {
                androidGenderIcon = new IconDrawable(SearchActivity.this,  FontAwesomeIcons.fa_male)
                        .colorRes(R.color.colorAccent).sizeDp(40);
            }
            else
            {
                androidGenderIcon = new IconDrawable(SearchActivity.this,  FontAwesomeIcons.fa_female)
                        .colorRes(R.color.colorAccent).sizeDp(40);
            }
            icon.setImageDrawable(androidGenderIcon);
        }

    @Override
    public void onClick(View view) {
        String personid=members.get(getAdapterPosition()).getPersonID();
        PersonMod person=Model.instance().getPerson().get(personid);
        Intent intent = new Intent (SearchActivity.this, PersonActivity.class);
        intent.putExtra("Person", (Serializable) person);
        startActivity(intent);
    }
}
    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<PersonMod> fmembers;
        private ArrayList<EventMod> searchevents;
        final int person_type = 0;
        final int event_type = 1;

        public MyAdapter(ArrayList<PersonMod> memberin, ArrayList<EventMod> eventin) {
            fmembers = memberin;
            searchevents = eventin;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder, parent, false);
            if(viewType == person_type){
                return new viewHolder(v);
            }

            if(viewType == event_type){
                return new eventHolder(v);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            if(viewHolder instanceof viewHolder){
                ((viewHolder) viewHolder).populate(fmembers.get(position));
            }

            if(viewHolder instanceof eventHolder){
                ((eventHolder) viewHolder).populate(searchevents.get(position - fmembers.size()));
            }
        }
        @Override
        public int getItemCount() {
            return fmembers.size()+searchevents.size();
        }
        @Override
        public int getItemViewType(int position){
            if(position < fmembers.size()){
                return person_type;
            }

            if(position - fmembers.size() < searchevents.size()){
                return event_type;
            }

            return -1;
        }
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
