package com.example.tyagis.doctorassistant;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements View.OnClickListener {
    EditText phoneno,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,Profile.class));
            return;
        }
        phoneno=(EditText)findViewById(R.id.phoneno);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==login)
            logIn();
    }

    private void logIn() {
        final String Phoneno=phoneno.getText().toString().trim();
        final String Password=password.getText().toString().trim();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(!jsonObject.getBoolean("error")){
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogin(jsonObject.getInt("doctor_id"),jsonObject.getString("doctor_name"),jsonObject.getString("doctor_phoneno"),jsonObject.getString("city"),jsonObject.getString("qualification"),jsonObject.getString("Specialization"),jsonObject.getInt("morning_shift"),jsonObject.getInt("evening_shift"),jsonObject.getInt("Max_patient"),jsonObject.getInt("fees"));
                       // Toast.makeText(getApplicationContext(),SharedPrefManager.getInstance(getApplicationContext()).getDoctorName(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(login.this,Profile.class));
                        finish(); //so that when user press back button he shouldn't be on login activity
                    }
                    else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("doctor_phoneno", Phoneno);
                params.put("password", Password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
