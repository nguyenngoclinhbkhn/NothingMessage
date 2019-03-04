package com.example.nguyenngoclinh.nothingmessage.model;

public class Person {
    private String id;
    private long inRoom;
    private long outRoom;

    public Person(){

    }
    public Person(String id, long inRoom, long outRoom) {
        this.id = id;
        this.inRoom = inRoom;
        this.outRoom = outRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getInRoom() {
        return inRoom;
    }

    public void setInRoom(long inRoom) {
        this.inRoom = inRoom;
    }

    public long getOutRoom() {
        return outRoom;
    }

    public void setOutRoom(long outRoom) {
        this.outRoom = outRoom;
    }
}
