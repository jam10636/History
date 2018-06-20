package com.example.kevintsou.image.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.model.Members;
import com.example.kevintsou.image.model.Model;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.io.Serializable;
import java.util.ArrayList;

import shared.model.EventMod;
import shared.model.PersonMod;

/**
 * Created by KevinTsou on 3/24/2018.
 */

public class PersonActivity extends AppCompatActivity{
    private TextView firstname;
    private TextView lastname;
    private TextView gender;
    private String gen;
    RecyclerView recyclclepeople;
    RecyclerView recycleevent;
    LinearLayoutManager peoplemanager;
    LinearLayoutManager eventmanager;
    FamilyAdapter myAdapter;
    EventAdapter eventAdapter;
    PersonMod person;
    Members members;
    EventMod eventMod;
    ArrayList<Members>memberslist;
    ArrayList<EventMod>eventMods;
    Drawable androidGenderIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        person=(PersonMod)getIntent().getSerializableExtra("Person");
        eventMods=sortevents(Model.instance().getEventes().get(person.getPersonID()));

        memberslist=new ArrayList<>();
        eventMod=new EventMod();
        recyclclepeople=(RecyclerView)findViewById(R.id.peoplelist) ;
        peoplemanager=new LinearLayoutManager(this);
        recyclclepeople.setLayoutManager(peoplemanager);
        myAdapter=new FamilyAdapter(memberslist);
        recyclclepeople.setAdapter(myAdapter);

        recycleevent=(RecyclerView)findViewById(R.id.eventlist);
        eventmanager=new LinearLayoutManager(this);
        recycleevent.setLayoutManager(eventmanager);
        eventAdapter=new EventAdapter(eventMods);
        recycleevent.setAdapter(eventAdapter);

        firstname=(TextView)findViewById(R.id.personfistname);
        lastname=(TextView)findViewById(R.id.personlastname);
        gender=(TextView)findViewById(R.id.persongender);
        firstname.setText(person.getFirstName());
        lastname.setText(person.getLastName());
        if(person.getGender().equals("m"))
        {
            gen="Male";
        }
        else
        {
            gen="Female";
        }
        gender.setText(gen);
        getall();
    }
    public void getall()
    {
        memberslist.clear();
        if(person.getMother()!=null)
        {
            members=new Members();
            members.setinfo(Model.instance().getPerson().get(person.getMother()));
            members.setTie("Mother");
            memberslist.add(members);
        }
        if(person.getFather()!=null)
        {
            members=new Members();
            members.setinfo(Model.instance().getPerson().get(person.getFather()));
            members.setTie("Father");
            memberslist.add(members);
        }
        if(person.getSpouse()!=null)
        {
            members=new Members();
            members.setinfo(Model.instance().getPerson().get(person.getSpouse()));
            members.setTie("Spouse");
            memberslist.add(members);
        }
        for(PersonMod p:Model.instance().getPeoples())
        {

            if(p.getFather()!=null)
            {
                if(p.getFather().equals(person.getPersonID())) {
                    members = new Members();
                    members.setinfo(p);
                    members.setTie("Child");
                    memberslist.add(members);
                }
            }
            if(p.getMother()!=null)
            {
                if(p.getMother().equals(person.getPersonID())) {
                    members = new Members();
                    members.setinfo(p);
                    members.setTie("Child");
                    memberslist.add(members);
                }
            }
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

        @Override
        public void onClick(View view) {
            String personid=memberslist.get(getAdapterPosition()).getPersonid();
            person=Model.instance().getPerson().get(personid);
            Intent intent = new Intent (PersonActivity.this, PersonActivity.class);
            intent.putExtra("Person", (Serializable) person);
            startActivity(intent);
        }
    }
    public class FamilyAdapter extends RecyclerView.Adapter<viewHolder> {
        private ArrayList<Members>fmembers;
        public FamilyAdapter(ArrayList<Members> memberin) {
            fmembers = memberin;
        }
        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            View v =LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder, parent, false);
              viewHolder vh = new viewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {
            members=memberslist.get(position);
            viewHolder.uppertext.setText(members.getFirstname()+" "+members.getLastname());
            viewHolder.lowertext.setText(members.getTie());
            if(members.getGender().equals("m"))
            {
                androidGenderIcon = new IconDrawable(PersonActivity.this,  FontAwesomeIcons.fa_male)
                        .colorRes(R.color.colorAccent).sizeDp(40);
                viewHolder.icon.setImageDrawable(androidGenderIcon);
            }
            else
            {
                androidGenderIcon = new IconDrawable(PersonActivity.this,  FontAwesomeIcons.fa_female)
                        .colorRes(R.color.colorAccent).sizeDp(40);
                viewHolder.icon.setImageDrawable(androidGenderIcon);
            }
        }

        @Override
        public int getItemCount()
        {
            return fmembers.size();
        }
    }
    public class eventviewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        protected ImageView icon;
        protected TextView uppertext;
        protected TextView lowertext;
        public eventviewHolder(View v)
        {
            super(v);
            icon=(ImageView)v.findViewById(R.id.imageinview);
            uppertext=(TextView)v.findViewById(R.id.uppertext);
            lowertext=(TextView)v.findViewById(R.id.lowertext);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String eventid=eventMods.get(getAdapterPosition()).getEventID();
            Intent intent = new Intent (PersonActivity.this, MapActivity.class);
            intent.putExtra("eventid", eventid);
            startActivity(intent);
        }
    }
    public class EventAdapter extends RecyclerView.Adapter<eventviewHolder>
    {
        private ArrayList<EventMod>eventlista;
        public EventAdapter(ArrayList<EventMod>eventMods)
        {
            eventlista=eventMods;
        }
        @Override
        public eventviewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View v =LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder, parent, false);
            eventviewHolder vh = new eventviewHolder(v);
            return vh;
        }
        @Override
        public void onBindViewHolder(eventviewHolder viewHolder, int position) {
            eventMod=eventlista.get(position);
            String in=eventMod.getEventType()+": "+eventMod.getCity()+","+eventMod.getCountry()+"("+eventMod.getYear()+")";
            viewHolder.uppertext.setText(in);
            PersonMod p=Model.instance().getPerson().get(eventMod.getPersonID());
            in=p.getFirstName()+" "+p.getLastName();
            viewHolder.lowertext.setText(in);
                androidGenderIcon = new IconDrawable(PersonActivity.this,  FontAwesomeIcons.fa_map_marker)
                        .colorRes(R.color.colorAccent).sizeDp(40);
                viewHolder.icon.setImageDrawable(androidGenderIcon);
        }

        @Override
        public int getItemCount()
        {
            return eventlista.size();
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
    public ArrayList<EventMod> sortevents(ArrayList<EventMod>events)
    {
        ArrayList<EventMod> sortedlist=new ArrayList<>();
        for(EventMod e:events)
        {
            if((!e.getEventType().toLowerCase().equals("death"))&&(!e.getEventType().toLowerCase().equals("birth")))
            {
                sortedlist.add(e);
            }
        }
        for(int i=0;i<sortedlist.size()-1;i++)
        {
            for(int a=0;a<sortedlist.size()-1;a++) {
                if (Integer.valueOf(sortedlist.get(a).getYear()) > Integer.valueOf(sortedlist.get(a + 1).getYear())) {
                    EventMod sortevent = sortedlist.get(a);
                    sortedlist.set(a, sortedlist.get(a + 1));
                    sortedlist.set(a + 1, sortevent);
                }
                else if(sortedlist.get(a).getYear().equals(sortedlist.get(a+1).getYear()))
                {
                    if(sortedlist.get(a).getEventType().compareTo(sortedlist.get(a+1).getEventType())<0)
                    {
                        EventMod sortevent = sortedlist.get(a);
                        sortedlist.set(a, sortedlist.get(a + 1));
                        sortedlist.set(a + 1, sortevent);
                    }
                }
            }
        }
        for(EventMod e:events)
        {
            if(e.getEventType().toLowerCase().equals("birth"))
            {
                sortedlist.add(0,e);
            }
            if(e.getEventType().toLowerCase().equals("death"))
            {
                sortedlist.add(e);
            }
        }
        return sortedlist;
    }

}
