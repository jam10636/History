package com.example.kevintsou.image.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.view.*;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kevintsou.image.R;
import com.example.kevintsou.image.activity.MainActivity;
import com.example.kevintsou.image.model.Model;
import com.example.kevintsou.image.proxy.Proxy;

import org.xml.sax.helpers.LocatorImpl;

import java.io.IOException;
import shared.request.LoginRequest;
import shared.request.RegisterRequest;
import shared.result.LoginResult;
import shared.result.RegisterResult;

/**
 * Created by KevinTsou on 3/12/2018.
 */

public class LoginFragment extends Fragment {
    private String serverhost;
    private String serverport;
    private EditText username;
    private EditText passowrd;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private RadioButton femalebutt;
    private RadioButton malebutt;
    private Button signin;
    private Button register;
    private String gender=null;
    Proxy proxy = new Proxy();
    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        serverhost = "10.0.2.2";
        serverport = "3000";
        username = (EditText) v.findViewById(R.id.username);
        passowrd = (EditText) v.findViewById(R.id.password);
        firstname = (EditText) v.findViewById(R.id.firstname);
        lastname = (EditText) v.findViewById(R.id.lastname);
        email = (EditText) v.findViewById(R.id.email);
        signin = (Button) v.findViewById(R.id.button);
        register = (Button) v.findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registertask task=new registertask();
                task.execute();
            }
        });
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                logintask task=new logintask();
                task.execute();
            }
        });
        RadioGroup radioGroup=(RadioGroup) v.findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.female:
                    {
                        gender="f";
                    }
                        break;
                    case R.id.male:
                    {
                        gender="m";
                    }
                        break;
                }
            }
        });
        return v;
    }

    private class logintask extends AsyncTask<RegisterRequest, Void, LoginResult> {

        @Override
        protected LoginResult doInBackground(RegisterRequest... params) {
            LoginRequest request = new LoginRequest(username.getText().toString(), passowrd.getText().toString());
            LoginResult loginResult=null;
            try {
                 loginResult = proxy.loginresult(serverhost, serverport, request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return loginResult;
        }
        @Override
        public void onPostExecute(LoginResult result)
        {
            if(result==null)
            {
                Toast.makeText(getActivity(), "Fail",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Model.instance().setall(result.getPersonid(),result.getAuthtoken());
                familytask task=new familytask();
                task.execute();
            }
        }
    }
    private class registertask extends AsyncTask<RegisterRequest, Void, RegisterResult> {

        @Override
        protected RegisterResult doInBackground(RegisterRequest... params) {
            RegisterRequest request = new RegisterRequest(username.getText().toString(), passowrd.getText().toString(),email.getText().toString(),
                    firstname.getText().toString(),lastname.getText().toString(),gender);
            RegisterResult registerResult=null;
            if(empty()==false)
            {
                return null;
            }
            try {
                 registerResult = proxy.registerResult(serverhost, serverport, request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return registerResult;
        }
        @Override
        public void onPostExecute(RegisterResult result)
        {
            if(result==null)
            {
                Toast.makeText(getActivity(), "Fail",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Model.instance().setall(result.getPersonid(),result.getAuthtoken());
                familytask task=new familytask();
                task.execute();
            }
        }
        public Boolean empty()
        {
           if(username.getText().toString().equals("")||passowrd.getText().toString().equals("")||firstname.getText().toString().equals("")
                   ||lastname.getText().toString().equals("")||gender==null)
           {
               return false;
           }
           return true;
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
            Toast.makeText(getActivity(), Model.instance().getPersonMod().getFirstName()+" "+Model.instance().getPersonMod().getLastName(),
                    Toast.LENGTH_LONG).show();
            ((MainActivity)getActivity()).startmap();
        }
    }

}

