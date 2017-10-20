package com.example.usuariopc.lm3_studygroup;

/**
 * Created by UsuarioPC on 20/10/2017.
 */

public class Student {

    private String name;
    private String phoneNumber;

    public Student(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString(){
        return this.getName() + " (" + this.getPhoneNumber() + ")";
    }



}
