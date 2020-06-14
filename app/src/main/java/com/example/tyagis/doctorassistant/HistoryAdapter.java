package com.example.tyagis.doctorassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    Context ctx;
    List<Appointment> appointmentList;
    HistoryAdapter(Context ctx,List<Appointment> appointmentList){
        this.ctx=ctx;
        this.appointmentList=appointmentList;
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.history_list,null);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int i) {
        final Appointment appointment=appointmentList.get(i);
        holder.name.setText(appointment.getPatient_name());
        holder.gender.setText(appointment.getGender());
        holder.age.setText(String.valueOf(appointment.getAge()));
        holder.id.setText(String.valueOf(appointment.getAppointment_id()));
        holder.time.setText(String.valueOf(appointment.getTime()));
        holder.date.setText(appointment.getDate());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView name,gender,age,date,time,id;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.patient_name);
            gender=itemView.findViewById(R.id.gender);
            age=itemView.findViewById(R.id.age);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            id=itemView.findViewById(R.id.id);
        }
    }
}
