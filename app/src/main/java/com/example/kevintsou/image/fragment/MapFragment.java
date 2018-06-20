package com.example.kevintsou.image.fragment;
import android.content.Intent;
import android.drm.DrmErrorEvent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.activity.ActionUp;
import com.example.kevintsou.image.activity.FilterActivity;
import com.example.kevintsou.image.activity.MainActivity;
import com.example.kevintsou.image.activity.MapActivity;
import com.example.kevintsou.image.activity.PersonActivity;
import com.example.kevintsou.image.activity.SearchActivity;
import com.example.kevintsou.image.activity.SettingActivity;
import com.example.kevintsou.image.model.Event;
import com.example.kevintsou.image.model.Model;
import com.example.kevintsou.image.model.Person;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import shared.model.EventMod;
import shared.model.PersonMod;

/**
 * Created by KevinTsou on 3/21/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap googleMap;
    private PersonMod curperson;
    private Map<String, EventMod> markers = new TreeMap<>();
    private TextView personname;
    private TextView eventdetail;
    private LinearLayout linearLayout;
    private ImageView imageView;
    Drawable androidGenderIcon;
    EventMod mod;
    EventMod currentmod=new EventMod();
    int size=25;
    ArrayList<Polyline> polylinelist = new ArrayList<>();

    public MapFragment() {
    }

    ;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menubar, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.search).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                        .colorRes(R.color.colorAccent)
                        .actionBarSize());

        menu.findItem(R.id.filter).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter)
                        .colorRes(R.color.colorAccent)
                        .actionBarSize());

        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
                        .colorRes(R.color.colorAccent)
                        .actionBarSize());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.filter:
                intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        Bundle bundle = this.getArguments();
        personname = (TextView) v.findViewById(R.id.personname);
        eventdetail = (TextView) v.findViewById(R.id.locationandyear);
        imageView = (ImageView) v.findViewById(R.id.gendericon);
        androidGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                .colorRes(R.color.colorAccent).sizeDp(40);
        linearLayout = (LinearLayout) v.findViewById(R.id.holder);
        if (bundle != null) {
            String eventid = bundle.getString("eventid");
            mod = Model.instance().getEvent().get(eventid);
            curperson = Model.instance().getPerson().get(mod.getPersonID());
            personname.setText(curperson.getFirstName() + " " +
                    curperson.getLastName());
            eventdetail.setText(mod.getEventType() + ": " + mod.getCity() + ",\n" + mod.getCountry() + " (" + mod.getYear() + ")");
            if (curperson.getGender().equals("m")) {
                androidGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                        .colorRes(R.color.colorAccent).sizeDp(40);
            } else {
                androidGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                        .colorRes(R.color.colorAccent).sizeDp(40);
            }
            imageView.setImageDrawable(androidGenderIcon);
        } else {
            setHasOptionsMenu(true);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                intent.putExtra("Person", (Serializable) curperson);
                startActivity(intent);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapid);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Event event = new Event();
        Person person = new Person();
        person.setpeoplemap();
        event.docolor();
        setmaptype(map);
        googleMap = map;
        if (getActivity() instanceof MainActivity) {
            event.doevent();
        } else {
            for (Polyline line : polylinelist) {
                line.remove();
            }
            currentmod=mod;
            drawspouseline(mod,googleMap);
            drawgradparents(googleMap,mod.getPersonID(),true,mod);
            drawlifeline(mod,googleMap);
            map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(mod.getLatitude()), Double.valueOf(mod.getLongitude()))));

        }
        for (EventMod e : Model.instance().getFilter().getFitleredevetn()) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(e.getLatitude()), Double.parseDouble(e.getLongitude()))).snippet(e.getEventID())
                    .icon(BitmapDescriptorFactory.defaultMarker(event.getColortype().get(e.getEventType().toLowerCase())));
            map.addMarker(marker);
            markers.put(marker.getSnippet(), e);
        }
        map.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        for (Polyline line : polylinelist) {
            line.remove();
        }
        EventMod eventMod = markers.get(marker.getSnippet());
        PersonMod peronMod = Model.instance().getPerson().get(eventMod.getPersonID());
        personname.setText(peronMod.getFirstName() + " " +
                peronMod.getLastName());
        eventdetail.setText(eventMod.getEventType() + ": " + eventMod.getCity() + ",\n" + eventMod.getCountry() + " (" + eventMod.getYear() + ")");
        if (peronMod.getGender().equals("m")) {
            androidGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                    .colorRes(R.color.colorAccent).sizeDp(40);
        } else {
            androidGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                    .colorRes(R.color.colorAccent).sizeDp(40);
        }
        currentmod=eventMod;
        drawspouseline(eventMod, googleMap);
        drawlifeline(eventMod, googleMap);
        drawgradparents(googleMap, eventMod.getPersonID(),true,currentmod);
        size=20;
        imageView.setImageDrawable(androidGenderIcon);
        curperson = peronMod;
        return false;
    }

    public void setmaptype(GoogleMap map) {
        if (Model.instance().getSettings().getDemaptype().equals("Hybrid")) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else if (Model.instance().getSettings().getDemaptype().equals("Satellite")) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else if (Model.instance().getSettings().getDemaptype().equals("Terrain")) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

    }

    public void drawspouseline(EventMod eventMod, GoogleMap map) {
        PersonMod personMod = Model.instance().getPerson().get(eventMod.getPersonID());
        if (Model.instance().getSettings().getSwitches().get("spousestory") == true) {
            if (personMod.getSpouse() != null) {
                ArrayList<EventMod> eventMods = Model.instance().getEventes().get(personMod.getSpouse());
                if (eventMods.size() != 0) {
                    Boolean isexist = false;
                    for (EventMod e : eventMods) {
                        if (e.getEventType().toLowerCase().equals("birth")) {
                            Polyline polyline = map.addPolyline(new PolylineOptions()
                                    .add(new LatLng(Double.valueOf(eventMod.getLatitude()), Double.valueOf(eventMod.getLongitude())),
                                            new LatLng(Double.valueOf(e.getLatitude()), Double.valueOf(e.getLongitude())))
                                    .width(10)
                                    .color(Model.instance().getSettings().getColorhue().get(Model.instance().getSettings().getColortype().get("spousestory"))));
                            polylinelist.add(polyline);
                            isexist = true;
                        }
                    }
                    if (isexist == false) {
                        EventMod curmod = eventMods.get(0);
                        for (EventMod e : eventMods) {
                            if (Integer.parseInt(curmod.getYear()) > Integer.parseInt(e.getYear())) {
                                curmod = e;
                            }
                        }
                        Polyline polyline = map.addPolyline(new PolylineOptions()
                                .add(new LatLng(Double.valueOf(eventMod.getLatitude()), Double.valueOf(eventMod.getLongitude())),
                                        new LatLng(Double.valueOf(curmod.getLatitude()), Double.valueOf(curmod.getLongitude())))
                                .width(10)
                                .color(Model.instance().getSettings().getColorhue().get(Model.instance().getSettings().getColortype().get("spousestory"))));
                        polylinelist.add(polyline);
                    }
                }
            }
        }
    }

    public void drawlifeline(EventMod eventMod, GoogleMap map) {
        if (Model.instance().getSettings().getSwitches().get("lifestory") == true) {
            ArrayList<EventMod> eventlist = Model.instance().getEventes().get(eventMod.getPersonID());
            ArrayList<EventMod> sortedlist=new ArrayList<>();
            for(EventMod e:eventlist)
            {
                if((!e.getEventType().toLowerCase().equals("death"))&&(!e.getEventType().toLowerCase().equals("birth")))
                {
                    sortedlist.add(e);
                }
            }
            EventMod curmod=eventMod;
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
            for(EventMod e:eventlist)
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
            curmod=sortedlist.get(0);
            for (EventMod e : sortedlist) {
                Polyline polyline = map.addPolyline(new PolylineOptions()
                        .add(new LatLng(Double.valueOf(curmod.getLatitude()), Double.valueOf(curmod.getLongitude())),
                                new LatLng(Double.valueOf(e.getLatitude()), Double.valueOf(e.getLongitude())))
                        .width(10)
                        .color(Model.instance().getSettings().getColorhue().get(Model.instance().getSettings().getColortype().get("lifestory"))));
                curmod=e;
                polylinelist.add(polyline);
            }
        }
    }

    public void getline(ArrayList<EventMod> eventMods, EventMod eventMod, GoogleMap map,int size) {
        if (eventMods.size() != 0) {
            Boolean isexist = false;
            for (EventMod e : eventMods) {
                if (e.getEventType().toLowerCase().equals("birth")) {
                    Polyline polyline = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(Double.valueOf(eventMod.getLatitude()), Double.valueOf(eventMod.getLongitude())),
                                    new LatLng(Double.valueOf(e.getLatitude()), Double.valueOf(e.getLongitude())))
                            .width(size)
                            .color(Model.instance().getSettings().getColorhue().get(Model.instance().getSettings().getColortype().get("familystory"))));
                    polylinelist.add(polyline);
                    isexist = true;
                    currentmod=e;
                }
            }
            if (isexist == false) {
                EventMod curmod = eventMods.get(0);
                for (EventMod e : eventMods) {
                    if (Integer.parseInt(curmod.getYear()) > Integer.parseInt(e.getYear())) {
                        curmod = e;
                    }
                }
                Polyline polyline = map.addPolyline(new PolylineOptions()
                        .add(new LatLng(Double.valueOf(curmod.getLatitude()), Double.valueOf(curmod.getLongitude())),
                                new LatLng(Double.valueOf(eventMod.getLatitude()), Double.valueOf(eventMod.getLongitude())))
                        .width(size)
                        .color(Model.instance().getSettings().getColorhue().get(Model.instance().getSettings().getColortype().get("familystory"))));
                polylinelist.add(polyline);
                currentmod=curmod;
            }
        }
    }

    public void drawgradparents(GoogleMap map,String personid,boolean x,EventMod passin) {
        PersonMod personMod = Model.instance().getPerson().get(personid);
        if (Model.instance().getSettings().getSwitches().get("familystory") == true) {
                if(x==false) {
                    size -= 5;
                    ArrayList<EventMod> events = Model.instance().getEventes().get(personMod.getPersonID());
                getline(events, passin, map, size);
                }
            EventMod passinmod=new EventMod(currentmod);
           if(personMod.getFather()!=null)
            {
                drawgradparents(map,personMod.getFather(),false,passinmod);
            }
            if(personMod.getMother()!=null)
            {
                drawgradparents(map,personMod.getMother(),false,passinmod);
            }
            size+=5;
        }
    }
}
