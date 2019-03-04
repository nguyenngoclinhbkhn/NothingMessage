package com.example.nguyenngoclinh.nothingmessage.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nguyenngoclinh.nothingmessage.model.Message;
import com.example.nguyenngoclinh.nothingmessage.model.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class IntentServiceDemo extends Service {
    private ArrayList<String> arrayIdSenderAndReceiver;
    private DatabaseReference reference;
    private String id;
    private String idSenderAndReceiver;
    private SharedPreferences preferences;
    private DatabaseReference reference1;
    private DatabaseReference referenceBottom;
    private List<Message> list;
    private List<Message> messageListToDelete;

    private List<Person> personList;
    private ArrayList<String> idList;
    private DatabaseReference referenceMessage;
    private String idSenderAndReceiver1;
    private Timer timer;
    private DatabaseReference referencePerson;
    private Person person;
    private DatabaseReference referenceFriend;
    private List<Message> friendList;
    private DatabaseReference referenceDelete;
    private DatabaseReference referenceFriend1;


    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");
        idList = new ArrayList<>();
        personList = new ArrayList<>();
        timer = new Timer();
        person = null;
        referenceFriend = FirebaseDatabase.getInstance().
                getReference("friends");
        referenceDelete = FirebaseDatabase.getInstance().getReference("friends");
        list = new ArrayList<>();
        friendList = new ArrayList<>();
        reference1 = FirebaseDatabase.getInstance().getReference("friends");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        idSenderAndReceiver = intent.getStringExtra("idSenderAndReceiver");
        referenceFriend = FirebaseDatabase.getInstance().getReference("friends").child(idSenderAndReceiver);
        reference = FirebaseDatabase.
                getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");
        referenceMessage = FirebaseDatabase.
                getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");

        referencePerson = FirebaseDatabase.
                getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("time").
                child(id);
        referencePerson.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                person = dataSnapshot.getValue(Person.class);
                if (person != null) {
                    Log.e("TAG", "Service " + "person " + person.getId() +
                            "\n" + "outRoom " + person.getOutRoom());
//                    Toast.makeText(IntentServiceDemo.this, "id " + id, Toast.LENGTH_SHORT).show();
                    referenceMessage.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            idList.clear();
                            list.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Message message = snapshot.getValue(Message.class);
                                Log.e("TAG", " " + message.getMessage());
                                list.add(message);
//                                Toast.makeText(IntentServiceDemo.this, "" + message.getMessage(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(IntentServiceDemo.this, "person " + person.getOutRoom(), Toast.LENGTH_SHORT).show();
                                if (message.getId_senderSendRequestAddFriend() != Integer.parseInt(id)) {
                                    if ((person.getOutRoom() == 0) && (message.getTime() > person.getInRoom()) &&
                                            ((message.getStatusMessage()) == 0)) {
                                        idList.add(message.getIdMessage());
//                                        list.add(message);
                                    }
                                    if ((person.getOutRoom() == 0) && (message.getTime() < person.getInRoom()) &&
                                            ((message.getStatusMessage()) == 0)) {
                                        idList.add(message.getIdMessage());
//                                        list.add(message);
                                    }
                                }
                            }
                            for (final String s : idList) {
                                Log.e("TAG", "hihi " + s);

                                reference.child(s).child("statusMessage").setValue(1);

                            }


                            // de setValue cho nut friends
//                            for (int i = list.size() - 1; i >= 0; i--){
//                                if (list.get(i).getStatusMessage() == 1){
//                                    String content = list.get(i).getMessage();
//                                    long time = list.get(i).getTime();
//                                    referenceFriend1.child(idSenderAndReceiver).child("message").setValue(content);
//                                    referenceFriend1.child(idSenderAndReceiver).child("time").setValue(time);
//                                }
//                            }
//                            if (list.size() > 0){
//                                String content = list.get(list.size() - 1).getMessage();
//                                long time = list.get(list.size() - 1).getTime();
//                                referenceFriend1.child(idSenderAndReceiver).child("message").setValue(content);
//                                referenceFriend1.child(idSenderAndReceiver).child("time").setValue(time);
//                            }
                            if (list.size() == 0) {
                                referenceFriend.child("message").setValue("...........................=>.........");
                            }

//                            referenceFriend.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    friendList.clear();
//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                        Message message = snapshot.getValue(Message.class);
//                                        friendList.add(message);
//                                        if (list.size() > 0) {
//                                            Message a = list.get(list.size() - 1);
//                                            Toast.makeText(IntentServiceDemo.this, ": " + a.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                    if (list.size() > 0) {
//                                        Message a = list.get(list.size() - 1);
//                                        Toast.makeText(IntentServiceDemo.this, ": "+ a.getMessage(), Toast.LENGTH_SHORT).show();
//                                        if (a != null) {
//                                            for (Message message1 : friendList) {
//                                                if (a.getMessage().trim().equals(message1.getMessage().trim()) &&
//                                                        (a.getStatusMessage() == 1)) {
//                                                    referenceDelete.child(message1.getIdSenderAndReceiver()).
//                                                            child("message").setValue("...........................=>.............");
//                                                    referenceDelete.child(message1.getIdSenderAndReceiver()).
//                                                            child("time").setValue(0);
//                                                    break;
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.e("TAG", " person" + person);
        return START_STICKY;
    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {


//            if (s != null) {
//                referenceMessage.child(s).child("statusMessage").setValue(1);
//            }

//        list = new ArrayList<>();
//        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
//        id = preferences.getString("id", "");
//        referenceBottom = FirebaseDatabase.getInstance().getReference("contentMessage");
//
//        idSenderAndReceiver = intent.getStringExtra("idSenderAndReceiver");
//        reference = FirebaseDatabase.getInstance().getReference("Message").child(idSenderAndReceiver);
//        reference1 = FirebaseDatabase.getInstance().getReference("friends");
//        Query bottom = referenceBottom.child(idSenderAndReceiver).orderByKey().limitToLast(1);
//        reference.child("contentMessage").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Message message = snapshot.getValue(Message.class);
//                    if (message.getStatusMessage() == 1) {
//                        list.add(message);
//                    }
//                }
//                for (Message message : list) {
//                    reference.child(idSenderAndReceiver).child(message.getIdMessage()).removeValue();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        messageListToDelete = new ArrayList<>();
//
//        bottom.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                messageListToDelete.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Message message = snapshot.getValue(Message.class);
//                    messageListToDelete.add(message);
//                }
//                for (Message mes : messageListToDelete) {
//                    if (mes.getStatusMessage() == 1) {
//                        reference1.child(idSenderAndReceiver).child("time").setValue("");
//                        reference1.child(idSenderAndReceiver).child("message").setValue(".........................=>.........");
//                        reference1.child(idSenderAndReceiver).child("imageResource").setValue(0);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        reference1.child(idSenderAndReceiver).child("time").setValue("");
//        reference1.child(idSenderAndReceiver).child("message").setValue(".........................=>.........");
//        reference1.child(idSenderAndReceiver).child("imageResource").setValue(0);
//        Toast.makeText(this, "Hello IntentService " + idSenderAndReceiver, Toast.LENGTH_SHORT).show();
//        reference.child(idSenderAndReceiver).removeValue();
//    }

    private String getIdMessage(String a, String id1) {
        String idMessage = "";
        String[] a1 = a.split("_");
        if (a1[0].equals(id1)) return a1[1];
        return a1[0];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
