package com.example.bdafahim.driver;

public class Reporter_Info {
    private String email,phone,area,profession,name;

    public Reporter_Info(String email, String phone, String area, String profession) {
        this.email = email;
        this.phone = phone;
        this.area = area;
        this.profession = profession;
    }

    public Reporter_Info(String email, String phone, String area, String profession, String name) {
        this.email = email;
        this.phone = phone;
        this.area = area;
        this.profession = profession;
        this.name = name;
    }

    public Reporter_Info() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
