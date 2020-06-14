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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestedAppointmentAdapter extends RecyclerView.Adapter<RequestedAppointmentAdapter.RequestedAppointmentViewHolder> {
    private Context ctx;
    private List<Appointment> appointmentList;

    public RequestedAppointmentAdapter(Context ctx,List<Appointment> appointmentList){
        this.ctx=ctx;
        this.appointmentList=appointmentList;
    }
    @NonNull
    @Override
    public RequestedAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.requested_appointment_list,null);
        return new RequestedAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedAppointmentViewHolder holder, int i) {
        final Appointment appointment=appointmentList.get(i);
        holder.textViewName.setText(appointment.getPatient_name());
        holder.textViewGender.setText(appointment.getGender());
        holder.textViewAge.setText(String.valueOf(appointment.getAge()));
        holder.textViewDate.setText(appointment.getDate());
        holder.textViewTime.setText(appointment.getTime());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_ACCEPT_APPOINTMENT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(ctx,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            ctx.startActivity(new Intent(ctx,Requested.class));
                            ((Requested)ctx).finish();
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
                        params.put("appointment_id",String.valueOf(appointment.getAppointment_id()));
                        return params;
                    }
                };
                RequestHandler.getInstance(ctx).addToRequestQueue(stringRequest);
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_DECLINE_APPOINTMENT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(ctx,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            ctx.startActivity(new Intent(ctx,Requested.class));
                            ((Requested)ctx).finish();

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

    class RequestedAppointmentViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName,textViewGender,textViewAge,textViewDate,textViewTime;
        Button accept,decline;

        public RequestedAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName=itemView.findViewById(R.id.patient_name);
            textViewGender=itemView.findViewById(R.id.gender);
            textViewTime=itemView.findViewById(R.id.time);
            textViewDate=itemView.findViewById(R.id.date);
            textViewAge=itemView.findViewById(R.id.age);
            accept=itemView.findViewById(R.id.accept);
            decline=itemView.findViewById(R.id.decline);
        }
    }
}
