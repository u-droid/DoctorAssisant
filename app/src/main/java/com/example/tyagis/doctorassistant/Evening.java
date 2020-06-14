package com.example.tyagis.doctorassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Evening extends AppCompatActivity {
    List<Appointment> appointmentList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evening);
        recyclerView=findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentList = new ArrayList<>();
        loadAppointments();
    }

    private void loadAppointments() {
        final String date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_EVENING_APPOINTMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);

                    for (int i=0;i<array.length();i++){
                        JSONObject appointment= array.getJSONObject(i);
                        appointmentList.add(new Appointment(appointment.getInt("appointment_id"),appointment.getString("patient_name"),appointment.getString("gender"),appointment.getInt("age"),appointment.getInt("token"),appointment.getString("description")));
                        EveningAdapter adapter=new EveningAdapter(Evening.this,appointmentList);
                        recyclerView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("doctor_id",String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getDoctorId()));
                params.put("date",date);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
