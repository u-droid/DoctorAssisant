package com.example.tyagis.doctorassistant;

public class Appointment {
    int appointment_id,age,token;
    String patient_name,date,time,gender,description;

    public Appointment(int appointment_id,String patient_name,String date,int time,String gender,int age){
        this.appointment_id=appointment_id;
        this.age=age;
        this.date=date;
        this.gender=gender;
        this.patient_name=patient_name;
        if(time>12)
            this.time=String.valueOf(time-12)+"pm";
        else
            if(time==12)
                this.time=String.valueOf(time)+"pm";
        else
            this.time=String.valueOf(time)+"am";
    }
    public Appointment(int appointment_id,String patient_name,String gender,int age,int token,String description){
        this.appointment_id=appointment_id;
        this.age=age;
        this.gender=gender;
        this.patient_name=patient_name;
        this.description=description;
        this.token=token;
    }

    public Appointment(int appointment_id,String patient_name,String gender,int age){
        this.appointment_id=appointment_id;
        this.age=age;
        this.gender=gender;
        this.patient_name=patient_name;
    }

    public String getDescription(){
        return description;
    }
    public int getToken(){
        return token;
    }
    public int getAppointment_id() {
        return appointment_id;
    }

    public int getAge() {
        return age;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getGender() {
        return gender;
    }
}
