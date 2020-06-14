package com.example.tyagis.doctorassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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

public class HomeFragment extends Fragment {
    @Nullable
    CardView requested,diagnosed,history,morning,evening,privacy;
    TextView name,today_patients;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment,container,false);
        load();
        requested=(CardView)view.findViewById(R.id.requested);
        diagnosed=(CardView)view.findViewById(R.id.diagnosed);
        history=(CardView)view.findViewById(R.id.history);
        name=(TextView)view.findViewById(R.id.name);
        today_patients=(TextView)view.findViewById(R.id.patients_today);
        morning=(CardView)view.findViewById(R.id.morning_appointments);
        evening=(CardView)view.findViewById(R.id.evening_appointments);
        privacy=(CardView)view.findViewById(R.id.privacy_policies);
        name.setText("Dr. "+SharedPrefManager.getInstance(view.getContext()).getDoctorName());
        today_patients.setText("No. patients diagnosed today : "+SharedPrefManager.getInstance(getContext()).getTodayPatients());
        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Requested.class));
            }
        });
        diagnosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),DiagnosedToday.class));
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),History.class));
            }
        });
        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Morning.class));
            }
        });
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Evening.class));
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PrivacyPolicy.class));
            }
        });
        return view;
    }
    public void load(){
        final String date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_VIEW_EARNING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    SharedPrefManager.getInstance(getContext()).earning(
                            jsonObject.getLong("patients_today"),
                            jsonObject.getLong("earning_today"),
                            jsonObject.getLong("total_patient"),
                            jsonObject.getLong("total_earning")
                    );
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("doctor_id", String.valueOf(SharedPrefManager.getInstance(getContext()).getDoctorId()));
                params.put("today_date", date);
                return params;
            }

        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
