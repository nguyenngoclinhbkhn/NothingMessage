package com.example.nguyenngoclinh.nothingmessage.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nguyenngoclinh.nothingmessage.model.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IntentServiceSendMessage extends IntentService {
    private DatabaseReference referenceMessage;
    private DatabaseReference referenceFriends;
    private String total;

    public IntentServiceSendMessage() {
        super("IntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        referenceFriends = FirebaseDatabase.getInstance().getReference("friends");
        referenceMessage = FirebaseDatabase.getInstance().getReference("Message");
        total = intent.getStringExtra("name");
        String[] a = total.split("=<");
        String idSenderAndReceiver = a[7];
        String key = a[8];
        Message message = new Message(a[0],
                0,
                Integer.parseInt(a[2]),
                Integer.parseInt(a[3]),
                0,
                Integer.parseInt(a[5]),
                "",
                a[7],
                a[8],
                Long.parseLong(a[9]));
        Log.e("TAG", " " + key);
        referenceMessage.child(idSenderAndReceiver).child("contentMessage").child(key).setValue(message);
        String notification = a[0];
        if (a[0].length() > 20) {
            notification = a[0].substring(0, 20) + " . . . ";
        }
        referenceFriends.child(idSenderAndReceiver).child("message").setValue(notification);
        referenceFriends.child(idSenderAndReceiver).child("time").setValue(Long.parseLong(a[9]));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "Service is destroy");
    }
}
