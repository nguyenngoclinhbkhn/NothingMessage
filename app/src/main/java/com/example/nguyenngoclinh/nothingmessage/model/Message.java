package com.example.nguyenngoclinh.nothingmessage.model;

public class Message {
    private String message;
    private int statusMessage;
    private int id_senderSendRequestAddFriend;
    private int serial;
    private int statusFriend;
    private String imageResource;
    private int id_receiverRequestAddFriend;
    private String idSenderAndReceiver;
    private String idMessage;
    private long time;

    public Message(String message,
                   int statusMessage,
                   int id_senderSendRequestAddFriend,
                   int id_receiverRequestAddFriend,
                   int serial,
                   int statusFriend,
                   String imageResource,
                   String idSenderAndReceiver,
                   String idMessage,
                   long time) {
        this.message = message;
        this.statusMessage = statusMessage;
        this.id_senderSendRequestAddFriend = id_senderSendRequestAddFriend;
        this.serial = serial;
        this.statusFriend = statusFriend;
        this.imageResource = imageResource;
        this.id_receiverRequestAddFriend = id_receiverRequestAddFriend;
        this.idSenderAndReceiver = idSenderAndReceiver;
        this.idMessage = idMessage;
        this.time = time;
    }
    public Message(){

    }
    public void setTime(long time){
        this.time = time;
    }
    public long getTime(){
        return time;
    }

    public void setIdMessage(String idMessage){
        this.idMessage = idMessage;
    }
    public String getIdMessage(){
        return idMessage;
    }
    public void setIdSenderAndReceiver(String idSenderAndReceiver){
        this.idSenderAndReceiver = idSenderAndReceiver;
    }
    public String getIdSenderAndReceiver(){
        return idSenderAndReceiver;
    }
    public void setId_receiverRequestAddFriend(int id_receiverRequestAddFriend){
        this.id_receiverRequestAddFriend = id_receiverRequestAddFriend;
    }
    public int getId_receiverRequestAddFriend(){
        return id_receiverRequestAddFriend;
    }
    public void setImageResource(String imageResource){
        this.imageResource = imageResource;
    }
    public String getImageResource(){
        return imageResource;
    }
    public void setStatusFriend(int statusFriend){
        this.statusFriend = statusFriend;
    }
    public int getStatusFriend(){
        return statusFriend;
    }
    public void setSerial(int serial){
        this.serial = serial;
    }
    public int getSerial(){
        return serial;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(int statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getId_senderSendRequestAddFriend() {
        return id_senderSendRequestAddFriend;
    }

    public void setId_senderSendRequestAddFriend(int id_senderSendRequestAddFriend) {
        this.id_senderSendRequestAddFriend = id_senderSendRequestAddFriend;
    }
}
