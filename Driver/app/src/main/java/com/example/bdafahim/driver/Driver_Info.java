package com.example.bdafahim.driver;

public class Driver_Info {

    private String name,area,type,age,plateno,phoneNo,mail;
    private int points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Driver_Info(String name, String area, String type, String age, String plateno, String phoneNo, String mail, int points) {
        this.name = name;
        this.area = area;
        this.type = type;
        this.age = age;
        this.plateno = plateno;
        this.phoneNo = phoneNo;
        this.mail = mail;
        this.points = points;

    }

    public Driver_Info() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
