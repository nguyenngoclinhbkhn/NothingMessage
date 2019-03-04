package com.example.nguyenngoclinh.nothingmessage.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.nguyenngoclinh.nothingmessage.model.Message;
import com.example.nguyenngoclinh.nothingmessage.model.Person;
import com.example.nguyenngoclinh.nothingmessage.uis.activities.ActivityMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

public class ServiceNotification extends Service {
    private DatabaseReference referenceIdSenderAndReceiver;
    private DatabaseReference referenceContentMessage;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private ArrayList<String> idSenderAndReceiverList;
    private ArrayList<Message> messageArrayListContentMessage;
    private SharedPreferences preferences;
    private boolean isOk = false;
    private String id;
    private String idSenderAndReceiver;
    private Context context;
    private SharedPreferences preferences1;
    private String idNeed;
    private SimpleDateFormat timeFormat;
    private String list;
    private SharedPreferences preferences12;
    private String a;
    private DatabaseReference referencePerson;
    private long today;
    private DatabaseReference databaseReferenceTest;
    private List<Person> personList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        personList = new ArrayList<>();
        referencePerson = FirebaseDatabase.getInstance().getReference("Message");
        databaseReferenceTest = FirebaseDatabase.getInstance().getReference("Message");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this, "channelId");
        idSenderAndReceiverList = new ArrayList<>();
        messageArrayListContentMessage = new ArrayList<>();
        referenceIdSenderAndReceiver = FirebaseDatabase.getInstance().getReference("friends");
        referenceContentMessage = FirebaseDatabase.getInstance().getReference("Message");
        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        preferences12 = getSharedPreferences("id", Context.MODE_PRIVATE);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        today = new Date(System.currentTimeMillis());
        list = preferences12.getString("id1", "");
        Log.e("TAG", " hihihi " + list);
        timeFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
//        time = timeFormat.format(today);
        isOk = preferences.getBoolean("isLogin", false);
//        idSenderAndReceiver = intent.getStringExtra()
        if (isOk == true) {
            id = preferences.getString("id", "");
            Log.e("tag", "id" + id);
        }
//        getIdSenderAndReceiver();
        builder.setSmallIcon(android.R.drawable.ic_dialog_email);
        builder.setContentTitle("New message");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        builder.setAutoCancel(true);
        builder.setVisibility(VISIBILITY_PUBLIC);


//        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0,
//                intentNotification,
//                0);


//        intentNotification.putExtra("idSenderAndRecevier", idSenderAndReceiver);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(),
//                0,
//                intentNotification,
//                0);
//        builder.setContentIntent(pendingIntent);


        referenceIdSenderAndReceiver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idSenderAndReceiverList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null && id != null) {
                        if ((message.getId_senderSendRequestAddFriend() == Integer.parseInt(id)) ||
                                (message.getId_receiverRequestAddFriend() == Integer.parseInt(id))) {
                            idSenderAndReceiverList.add(message.getIdSenderAndReceiver());

                        }
                    }
                }
                for (String s : idSenderAndReceiverList) {

                    referenceContentMessage.child(s).child("contentMessage").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            messageArrayListContentMessage.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Message message1 = snapshot.getValue(Message.class);
                                if (((message1.getStatusMessage()) == 0)) {
                                    messageArrayListContentMessage.add(message1);


                                }
                            }
                            for (int i = messageArrayListContentMessage.size() - 1; i >= 0; i--) {
                                if (messageArrayListContentMessage.get(i).getId_receiverRequestAddFriend() == Integer.parseInt(id)) {
                                    Log.e("TAG", "notification " + messageArrayListContentMessage.get(i).getMessage());
                                    String[] a = messageArrayListContentMessage.get(i).getMessage().split("=>");
                                    String notification = a[0];
                                    if (notification.length() > 15) {
                                        notification = a[0].substring(0, 15) + " . . .";
                                    }
                                    Log.d("TAG", "Content " + notification);
                                    builder.setContentText(notification);
//                            double a = messageArrayListContentMessage.get(i).getTime();
//                            builder.setWhen(messageArrayListContentMessage.get(i).getTime());
                                    idSenderAndReceiver = messageArrayListContentMessage.get(i).getIdSenderAndReceiver();

//                                    manager.notify(1357, builder.build());
                                    startForeground(1357, builder.build());
                                    Log.d("TAG", " idTAO DANG CAN TRONG VONG " + idSenderAndReceiver);
                                    break;
                                }
                            }
                            Intent intentNotification = new Intent(getApplicationContext(), ActivityMessage.class);
                            Log.d("TAG", " idTAO DANG CAN  " + idSenderAndReceiver);
                            intentNotification.putExtra("idSenderAndRecevier", idSenderAndReceiver);
                            intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                    0,
                                    intentNotification,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


//                    Toast.makeText(ServiceNotification.this, "hihi" + idSenderAndReceiver, Toast.LENGTH_SHORT).show();
//                    referencePerson.child(idSenderAndReceiver).child("time");

//                    manager.cancel(1357);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }

//    public String getIdSenderAndReceiver() {
//
//        isOk = preferences.getBoolean("isLogin", false);
////        idSenderAndReceiver = intent.getStringExtra()
//        if (isOk == true) {
//            id = preferences.getString("id", "");
//            Log.e("tag", "id" + id);
//        }
//
//        referenceIdSenderAndReceiver.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                idSenderAndReceiverList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Message message = snapshot.getValue(Message.class);
//                    if ((message.getId_senderSendRequestAddFriend() == Integer.parseInt(id)) ||
//                            (message.getId_receiverRequestAddFriend() == Integer.parseInt(id))) {
//                        idSenderAndReceiverList.add(message.getIdSenderAndReceiver());
//                    }
//                }
//                for (String s : idSenderAndReceiverList) {
//                    referenceContentMessage.child(s).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            messageArrayListContentMessage.clear();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                Message message1 = snapshot.getValue(Message.class);
//                                if ((message1.getStatusMessage() == 0)) {
//                                    messageArrayListContentMessage.add(message1);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                    for (int i = messageArrayListContentMessage.size() - 1; i >= 0; i--) {
//                        if (messageArrayListContentMessage.get(i).getId_senderSendRequestAddFriend() != Integer.parseInt(id)) {
//                            String content = messageArrayListContentMessage.get(i).getMessage();
//                            String[] array = content.split(",");
////                            builder.setContentText(content);
////                            manager.notify(1357, builder.build());
//                            idSenderAndReceiver = messageArrayListContentMessage.get(i).getIdSenderAndReceiver();
////                            preferences = getSharedPreferences(idSenderAndReceiver, MODE_PRIVATE);
////                            idNeed = preferences.getString(idSenderAndReceiver, "");
//                            Log.d("Trong vong ", ":" + idSenderAndReceiver);
//                            a = content + ";" + idSenderAndReceiver;
////                            startForeground(1357,
////                                    builder.build());
//
//                            break;
//                        }
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Log.d("Ngoai vong ", ":" + idNeed);
//
//
//        Log.d("TAG", "idSenderAndReceiverService : " + idSenderAndReceiver);
//
//
//        // gui intent tu ngoai app
//        return a;
//
//    }

    @Override
    public void onDestroy() {
        manager.cancel(1357);

//        stopForeground(true);
        stopSelf();
        super.onDestroy();

    }
}
