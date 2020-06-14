package com.example.tyagis.doctorassistant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,phoneno,password,city,qualification,specialisation,morning,evening,max_patient,fees;
    Button signup;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Context context=getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if(isConnected==false){   //when user is not connected to internet
            AlertDialog.Builder myalert = new AlertDialog.Builder(MainActivity.this);
            myalert.setTitle("Error");
            myalert.setMessage("Internet Connection Not Found");
            myalert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            myalert.setNegativeButton("Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS),0);
                }
            });
            myalert.setCancelable(false);
            myalert.show();
        }
        else{
            if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                finish();
                startActivity(new Intent(getApplicationContext(), Profile.class));
                return;
            }
            name = (EditText) findViewById(R.id.name);
            phoneno = (EditText) findViewById(R.id.phone_no);
            password = (EditText) findViewById(R.id.password);
            city = (EditText) findViewById(R.id.city);
            qualification = (EditText) findViewById(R.id.qualification);
            specialisation=(EditText)findViewById(R.id.specialisation);
            morning = (EditText) findViewById(R.id.morning);
            evening = (EditText) findViewById(R.id.evening);
            max_patient=(EditText)findViewById(R.id.max_patient);
            signup = (Button) findViewById(R.id.signup);
            login = (TextView) findViewById(R.id.login);
            fees=(EditText)findViewById(R.id.fees);
            ProgressDialog progressDialog=new ProgressDialog(this);
            signup.setOnClickListener(this);
            login.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View v) {
        if (v==signup)
            Signup();
        if(v==login)
            login();
    }

    private void login() {
        startActivity(new Intent(this,login.class));
    }

    private void Signup() {
        Toast.makeText(getApplicationContext(), "signup", Toast.LENGTH_SHORT).show();
        final String date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        final String Name = name.getText().toString().trim();
        final String Phoneno = phoneno.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        final String City = city.getText().toString().trim();
        final String Qualification = qualification.getText().toString().trim();
        final String Specialization = specialisation.getText().toString().trim();
        final String Morning = morning.getText().toString().trim();
        final String Evening = evening.getText().toString().trim();
        final String Max_patient = max_patient.getText().toString().trim();
        final String Fees=fees.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        if (validate(Name,Phoneno,Password,City,Qualification,Specialization,Morning,Evening,Max_patient)) {
            progressDialog.setMessage("Registering you");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //progressDialog.dismiss();
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("doctor_name", Name);
                    params.put("doctor_phoneno", Phoneno);
                    params.put("password", Password);
                    params.put("city", City);
                    params.put("qualification", Qualification);
                    params.put("Specialization", Specialization);
                    params.put("morning_shift", Morning);
                    params.put("evening_shift", Evening);
                    params.put("max_patient", Max_patient);
                    params.put("fees",Fees);
                    params.put("date",date);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
    private boolean validate(String name,String username,String password,String location,String qualification,String Specialization,String morning,String evening,String Max_patiient){

        String passwordRegex="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        Pattern passwordPattern=Pattern.compile(passwordRegex);
        if(name.length()==0){
            Toast.makeText(getApplicationContext(),"Enter Name",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(username.length()!=10){
            Toast.makeText(getApplicationContext(),"Invalid Phone number",Toast.LENGTH_SHORT).show();
            return false;
        }


        if(!passwordPattern.matcher(password).matches()){
            Toast.makeText(getApplicationContext(),"Password must contain at least one uppercase character, one lowercase character, one digit, one special character @#$% and at 6-20 characters long",Toast.LENGTH_LONG).show();
            return false;
        }
        if(location.length()==0){
            Toast.makeText(getApplicationContext(),"Enter City",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(qualification.length()==0){
            Toast.makeText(getApplicationContext(),"Enter Qualification",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Specialization.length()==0){
            Toast.makeText(getApplicationContext(),"Enter specialization, if not then enter none",Toast.LENGTH_LONG).show();
            return false;
        }
        /*
        if(Integer.parseInt(morning)>0 && Integer.parseInt(morning)<=12){
            Toast.makeText(getApplicationContext(),"Morning Time should be in between 1-12",Toast.LENGTH_LONG).show();
            return false;
        }
        if(Integer.parseInt(evening)>0 && Integer.parseInt(evening)<=12){
            Toast.makeText(getApplicationContext(),"Evening Time should be in between 1-12",Toast.LENGTH_LONG).show();
            return false;
        }
        if(Integer.parseInt(Max_patiient)==0){
            Toast.makeText(getApplicationContext(),"Maximum patients should be greater than 0",Toast.LENGTH_LONG).show();
            return false;
        }
        */
        return true;
    }
}
