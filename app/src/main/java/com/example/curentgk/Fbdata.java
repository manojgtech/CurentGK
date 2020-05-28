package com.example.curentgk;

public class Fbdata {

    private String fname;
    private String lname;
    private String email;
    private int login;
    private String imgurl;

    public Fbdata(String fname,String lname,String email,int login){
        this.fname=fname;
        this.lname=lname;
        this.email=email;
        this.login=login;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public int getLogin() {
        return login;
    }

    public String getImgurl() {
        return imgurl;
    }
}
