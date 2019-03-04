package com.example.nguyenngoclinh.nothingmessage.model;

public class User {
    private long ID;
    private String pass;

    public User(long ID, String pass) {
        this.ID = ID;
        this.pass = pass;
    }
    public User(){}

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
