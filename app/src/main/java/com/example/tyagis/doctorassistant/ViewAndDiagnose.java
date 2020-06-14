package com.example.tyagis.doctorassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewAndDiagnose extends AppCompatActivity {
    TextView name,age,gender,description,token;
    Button prescription;
    String appointment_id;
    int activityno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_diagnose);

        name=(TextView)findViewById(R.id.patient_name);
        age=(TextView)findViewById(R.id.age);
        gender=(TextView)findViewById(R.id.gender);
        token=(TextView)findViewById(R.id.token);
        description=(TextView)findViewById(R.id.description);
        prescription=(Button)findViewById(R.id.prescription);
        Intent intent=getIntent();
        Bundle bd=intent.getExtras();
        if(bd!=null){
            name.setText(bd.get("name").toString());
            age.setText(bd.get("age").toString());
            gender.setText(bd.get("gender").toString());
            description.setText(bd.get("description").toString());
            token.setText(bd.get("token").toString());
            appointment_id=bd.get("appointment_id").toString();
            activityno=bd.getInt("activityno");
        }
        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ViewAndDiagnose.this,Prescription.class);
                intent1.putExtra("appointment_id",appointment_id);
                intent1.putExtra("activityno",activityno);
                startActivity(intent1);
            }
        });
    }
}
