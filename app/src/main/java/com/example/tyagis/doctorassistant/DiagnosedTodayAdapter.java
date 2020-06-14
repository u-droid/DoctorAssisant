package com.example.tyagis.doctorassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DiagnosedTodayAdapter extends RecyclerView.Adapter<DiagnosedTodayAdapter.DiagnosedTodayViewHolder> {
    Context ctx;
    List<Appointment> appointmentList;
    DiagnosedTodayAdapter(Context ctx,List<Appointment> appointmentList){
        this.ctx=ctx;
        this.appointmentList=appointmentList;
    }
    @NonNull
    @Override
    public DiagnosedTodayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.diagnosed_today_list,null);
        return new DiagnosedTodayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosedTodayViewHolder holder, int i) {
        final Appointment appointment=appointmentList.get(i);
        holder.name.setText(appointment.getPatient_name());
        holder.gender.setText(appointment.getGender());
        holder.age.setText(String.valueOf(appointment.getAge()));
        holder.id.setText(String.valueOf(appointment.getAppointment_id()));
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    class DiagnosedTodayViewHolder extends RecyclerView.ViewHolder{
        TextView name,gender,age,id;
        public DiagnosedTodayViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.patient_name);
            gender=itemView.findViewById(R.id.gender);
            age=itemView.findViewById(R.id.age);
            id=itemView.findViewById(R.id.id);
        }
    }
}
