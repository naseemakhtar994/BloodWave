package com.example.bloodwave;

public class CreateDonerModel {

    public  String name;
    public  String email;
    public  String gender;
    public  String bloodgroup;
    public  String date;

    public CreateDonerModel(String name, String email, String phone,String gender,String bloodgroup) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
    }


    public CreateDonerModel(String name, String email, String phone,String gender,String bloodgroup,String  date) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.date = date;
    }

    public CreateDonerModel(String name, String email, String phone,String gender,String bloodgroup,String  date,String  requesterEmail) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.date = date;
        this.requesterEmail = requesterEmail;
    }

    public  String phone;
    public  String requesterEmail;


}
