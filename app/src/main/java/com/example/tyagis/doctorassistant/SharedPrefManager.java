package com.example.tyagis.doctorassistant;

import android.content.Context;
import android.content.SharedPreferences;
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

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;

    private static final String DOCTOR_SHARED_PREF="doctor_shared_pref";
    private static final String KEY_DOCTOR_ID="doctor_id";
    private static final String KEY_DOCTOR_NAME="doctor_name";
    private static final String KEY_PHONE="doctor_phoneno";
    private static final String KEY_CITY="city";
    private static final String KEY_DOCTOR_QUALIFICATION="qualification";
    private static final String KEY_DOCTOR_SPECIALIZATION="Specialization";
    private static final String KEY_MORNING_SHIFT="morning_shift";
    private static final String KEY_EVENING_SHIFT="evening_shift";
    private static final String KEY_MAX_PATIENT="max_patient";
    private static final String KEY_FEES="fees";

    private static final String EARNING_SHARED_PREF="earning_shared_pref";
    private static final String KEY_TODAY_PATIENTS="patients_today";
    private static final String KEY_TODAY_EARNING="earning_today";
    private static final String KEY_TOTAL_PATIENT="total_patients";
    private static final String KEY_TOTAL_EARNING="total_earning";

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int doctor_id,String doctor_name,String doctor_phoneno,String city,String qualification,String Specialization,int morning_shift,int evening_shift,int max_patient,int fees){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);//mode private so that only this app can use this shared prefrence
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_DOCTOR_ID,doctor_id);
        editor.putString(KEY_DOCTOR_NAME,doctor_name);
        editor.putString(KEY_PHONE,doctor_phoneno);
        editor.putString(KEY_CITY,city);
        editor.putString(KEY_DOCTOR_QUALIFICATION,qualification);
        editor.putString(KEY_DOCTOR_SPECIALIZATION,Specialization);
        editor.putInt(KEY_MORNING_SHIFT,morning_shift);
        editor.putInt(KEY_EVENING_SHIFT,evening_shift);
        editor.putInt(KEY_MAX_PATIENT,max_patient);
        editor.putInt(KEY_FEES,fees);
        editor.apply();
        return true;
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_DOCTOR_NAME,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        SharedPreferences sharedPreferences2=ctx.getSharedPreferences(EARNING_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2=sharedPreferences2.edit();
        editor2.clear();
        editor2.apply();
        return true;
    }
    public String getDoctorCity(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CITY,null);
    }
    public  String getDoctorPhone(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONE,null);
    }
    public int getDoctorId(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_DOCTOR_ID,0);
    }
    public int getMorningShift(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_MORNING_SHIFT,0);
    }
    public int getEveningShift(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_EVENING_SHIFT,0);
    }
    public String getDoctorName(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DOCTOR_NAME,null);
    }
    public String getDoctorqualifications(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DOCTOR_QUALIFICATION,null);
    }
    public String getDoctorSpecialization(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DOCTOR_SPECIALIZATION,null);
    }
    public int getMaxPatient(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_MAX_PATIENT,0);
    }
    public int getFees(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(DOCTOR_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_FEES,0);
    }

    public void earning(long today_patients,long earning_today,long total_patients,long total_earning){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(EARNING_SHARED_PREF,Context.MODE_PRIVATE);//mode private so that only this app can use this shared prefrence
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putLong(KEY_TODAY_PATIENTS,today_patients);
        editor.putLong(KEY_TODAY_EARNING,earning_today);
        editor.putLong(KEY_TOTAL_PATIENT,total_patients);
        editor.putLong(KEY_TOTAL_EARNING,total_earning);
        editor.apply();
        load();
    }

    public long getTodayPatients(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(EARNING_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(KEY_TODAY_PATIENTS,0);
    }

    public long getEarningToday(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(EARNING_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(KEY_TODAY_EARNING,0);
    }

    public long getTotalPatients(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(EARNING_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(KEY_TOTAL_PATIENT,0);
    }

    public long getTotalEarning(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(EARNING_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(KEY_TOTAL_EARNING,0);
    }
    public void load(){
        final String date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_VIEW_EARNING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    SharedPrefManager.getInstance(ctx).earning(
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
                Toast.makeText(ctx,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("doctor_id", String.valueOf(getDoctorId()));
                params.put("today_date", date);
                return params;
            }

        };
        RequestHandler.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
