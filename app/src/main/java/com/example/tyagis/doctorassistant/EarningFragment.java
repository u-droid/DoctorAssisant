package com.example.tyagis.doctorassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EarningFragment extends Fragment {
    TextView todaypatients,todayearning,totalpatients,totalearning;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.earning_fragment,container,false);
        todaypatients=(TextView)view.findViewById(R.id.todaypatients);
        todayearning=(TextView)view.findViewById(R.id.earningtoday);
        totalpatients=(TextView)view.findViewById(R.id.totalpatients);
        totalearning=(TextView)view.findViewById(R.id.totalearning);
        //load();
        todaypatients.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getTodayPatients()));
        todayearning.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getEarningToday()));
        totalpatients.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getTotalPatients()));
        totalearning.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getTotalEarning()));
        return view;
    }


}
