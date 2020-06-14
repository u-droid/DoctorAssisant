package com.example.tyagis.doctorassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AccountFragment extends Fragment {
    @Nullable
    TextView name,phone,city,qualification,specialisation,morning,evening,max,fees;
    Button logout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.account_fragment,container,false);
        name=(TextView)view.findViewById(R.id.name);
        phone=(TextView)view.findViewById(R.id.phone_no);
        city=(TextView)view.findViewById(R.id.city);
        qualification=(TextView)view.findViewById(R.id.qualification);
        specialisation=(TextView)view.findViewById(R.id.specialisation);
        morning=(TextView)view.findViewById(R.id.morning);
        evening=(TextView)view.findViewById(R.id.evening);
        max=(TextView)view.findViewById(R.id.max_patient);
        fees=(TextView)view.findViewById(R.id.fees);
        logout=(Button)view.findViewById(R.id.logout);

        name.setText(SharedPrefManager.getInstance(view.getContext()).getDoctorName());
        phone.setText(SharedPrefManager.getInstance(view.getContext()).getDoctorPhone());
        city.setText(SharedPrefManager.getInstance(view.getContext()).getDoctorCity());
        qualification.setText(SharedPrefManager.getInstance(view.getContext()).getDoctorqualifications());
        specialisation.setText(SharedPrefManager.getInstance(view.getContext()).getDoctorSpecialization());
        morning.setText(String.valueOf(SharedPrefManager.getInstance(view.getContext()).getMorningShift()));
        evening.setText(String.valueOf(SharedPrefManager.getInstance(view.getContext()).getEveningShift()));
        max.setText(String.valueOf(SharedPrefManager.getInstance(view.getContext()).getMaxPatient()));
        fees.setText(String.valueOf(SharedPrefManager.getInstance(view.getContext()).getFees()));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getContext()).logout();
                getActivity().finishAffinity();
                startActivity(new Intent(view.getContext(),login.class));

            }
        });

        return view;
    }
}
