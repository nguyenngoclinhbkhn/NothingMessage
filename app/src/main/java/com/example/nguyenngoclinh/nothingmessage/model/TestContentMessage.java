package com.example.nguyenngoclinh.nothingmessage.model;

public class TestContentMessage {
    private String idAccount;
    private String contentMessage;
    boolean isMessage;

    public TestContentMessage(String idAccount, String contentMessage, boolean isMessage){
        this.idAccount = idAccount;
        this.contentMessage = contentMessage;
        this.isMessage = isMessage;
    }
    public void setIdAccount(String idAccount){
        this.idAccount = idAccount;
    }
    public String getIdAccount(){
        return idAccount;
    }
    public void setContentMessage(String contentMessage){
        this.contentMessage = contentMessage;
    }
    public String getContentMessage(){
        return contentMessage;
    }
    public void setMessage(boolean isMessage){
        this.isMessage = isMessage;
    }
    public boolean getIsMessage(){
        return isMessage;
    }
}
