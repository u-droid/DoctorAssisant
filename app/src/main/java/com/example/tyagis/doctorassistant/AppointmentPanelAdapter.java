package com.example.tyagis.doctorassistant;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AppointmentPanelAdapter extends RecyclerView.Adapter<AppointmentPanelAdapter.AppointmentPanelViewHolder>{

    private Context ctx;
    private List<Appointment> appointmentList;

    public AppointmentPanelAdapter(Context ctx,List<Appointment> appointmentList){
        this.ctx=ctx;
        this.appointmentList=appointmentList;
    }

    @NonNull
    @Override
    public AppointmentPanelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.patient_list,null);
        return new AppointmentPanelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentPanelViewHolder holder, int i) {
        final Appointment appointment=appointmentList.get(i);
        holder.name.setText(appointment.getPatient_name());
        holder.gender.setText(appointment.getGender());
        holder.age.setText(String.valueOf(appointment.getAge()));
        holder.token.setText(String.valueOf(appointment.getToken()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,ViewAndDiagnose.class);
                intent.putExtra("name",appointment.getPatient_name());
                intent.putExtra("age",appointment.getAge());
                intent.putExtra("gender",appointment.getGender());
                intent.putExtra("token",appointment.getToken());
                intent.putExtra("activityno",1);
                intent.putExtra("appointment_id",appointment.getAppointment_id());
                intent.putExtra("description",appointment.getDescription());
                ctx.startActivity(intent);
            }
        });
        holder.notavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"NA",Toast.LENGTH_SHORT).show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_DELETE_APPOINTMENT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(ctx,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            ctx.startActivity(new Intent(ctx,Morning.class));
                            ((Morning)ctx).finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("appointment_id",String.valueOf(appointment.getAppointment_id()));
                        return params;
                    }
                };
                RequestHandler.getInstance(ctx).addToRequestQueue(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    class AppointmentPanelViewHolder extends RecyclerView.ViewHolder{
        TextView name,gender,age,token;
        Button view,notavailable;
        public AppointmentPanelViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            gender=itemView.findViewById(R.id.sex);
            age=itemView.findViewById(R.id.years);
            token=itemView.findViewById(R.id.token);
            view=itemView.findViewById(R.id.view);
            notavailable=itemView.findViewById(R.id.notavailable);
        }
    }
}
