package com.example.tyagis.doctorassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Prescription extends AppCompatActivity {
     String appointment_id;
     int activityno;

     Button done;
     EditText prescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        Intent intent=getIntent();
        Bundle bd=intent.getExtras();
        if(bd!=null){
            appointment_id=bd.get("appointment_id").toString();
            activityno=bd.getInt("activityno");
        }

        done=(Button)findViewById(R.id.done);
        prescription=(EditText)findViewById(R.id.prescription);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT).show();
                final String Prescription=prescription.getText().toString();
                final String date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_DIAGNOSED_PATIENT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            if(activityno==1){
                                finishAffinity();
                                startActivity(new Intent(Prescription.this,Profile.class));
                                startActivity(new Intent(Prescription.this,Morning.class));
                            }
                            else
                                if(activityno==2){
                                    finishAffinity();
                                    startActivity(new Intent(Prescription.this,Profile.class));
                                    startActivity(new Intent(Prescription.this,Evening.class));
                                }
                                else {
                                    finish();
                                    startActivity(new Intent(Prescription.this,Profile.class));
                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("appointment_id",appointment_id);
                        params.put("today_date",date);
                        params.put("prescription",Prescription);
                        params.put("fees",String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getFees()));
                        params.put("doctor_id",String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getDoctorId()));
                        return params;
                    }
                };
                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
    }
}
