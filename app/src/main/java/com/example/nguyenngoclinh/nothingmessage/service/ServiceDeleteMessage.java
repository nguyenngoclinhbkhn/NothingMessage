package com.example.nguyenngoclinh.nothingmessage.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.nguyenngoclinh.nothingmessage.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceDeleteMessage extends Service {
    private DatabaseReference reference;
    private Timer timer;
    private String idSenderAndReceiver;
    private List<Message> messageList;
    private ArrayList<String> idList;
    private Intent intent;
    private ScheduledExecutorService service;




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        idSenderAndReceiver = intent.getStringExtra("idSenderAndReceiver");
        reference = FirebaseDatabase.getInstance().getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final Message message = snapshot.getValue(Message.class);
                    if (message != null) {
                        if ((message.getStatusMessage()) == 1) {
                            timer.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    reference.child(message.getIdMessage()).removeValue();
                                }
                            }, 10000, 10000);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
