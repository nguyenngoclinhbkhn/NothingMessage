package com.example.nguyenngoclinh.nothingmessage.uis.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.nothingmessage.R;
import com.example.nguyenngoclinh.nothingmessage.adapter.AdapterMessage;
import com.example.nguyenngoclinh.nothingmessage.model.Message;
import com.example.nguyenngoclinh.nothingmessage.model.Person;
import com.example.nguyenngoclinh.nothingmessage.service.IntentServiceDemo;
import com.example.nguyenngoclinh.nothingmessage.service.IntentServiceSendMessage;
import com.example.nguyenngoclinh.nothingmessage.service.ServiceDeleteMessage;
import com.example.nguyenngoclinh.nothingmessage.service.ServiceNotification;
import com.example.nguyenngoclinh.nothingmessage.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ActivityMessage extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbarMessage;
    private ImageButton imageButtonBackMessage;
    private ImageView imageViewActionSend;
    private EditText editTextMessage;
    private RecyclerView recyclerViewMessage;
    private String messageContent;

    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    private Thread threadShutDownService;

    private TextView textViewIdMessage;
    private DatabaseReference reference;

    private List<Message> messageList;
    private List<Message> messageListReceiver;

    private AdapterMessage adapterMessage;
    private TextView textViewMessageFriend;

    private String idSenderAndReceiver;
    private String idRequestFriend;
    private String statusFriend;
    private int statusMessage;
    private int idSenderRequestFriend;
    private String idReceiverRequestFriend;
    private String idTest;
    private Handler handler;
    private String time;
    private double timeSecond;

    private ArrayList<String> idSenderAndReceiverList;

    private ScheduledExecutorService service;

    private List<String> idList;
    private List<Person> personList;

    private DatabaseReference referencePerson;
    private int i;

    private boolean isCheck = false;
    private DatabaseReference reference1;
    private DatabaseReference referenceFriend;

    private DatabaseReference referenceNotification;

    private List<Message> messageListToDelete;
    private List<Message> messageNotificationList;

    private int statusFriend1, idReceiver1;

    private List<Message> finalList;

    private String id1;
    private SharedPreferences preferences;

    private List<Message> list12;

    private Date today;
    private SimpleDateFormat timeFormat;

    private DatabaseReference referenceDeleteMessage;
    private DatabaseReference referenceToDelete;
    private List<String> listIntent;
    private DatabaseReference referenceFinal;
    private long a;

    private long current;

    private List<String> idToDelete;


    private Person a123;
    private DatabaseReference getReferencePerson1;

    private DatabaseReference referenceTest;
    private List<String> listID;
    private DatabaseReference referenceDelete1;


    // statusMessage = 1 da doc
    // statusMessage = 0 chua doc

    @Override
    public int injectLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        toolbarMessage = findViewById(R.id.toobarMessage);
        imageButtonBackMessage = findViewById(R.id.imageButtonBackMessage);
        imageViewActionSend = findViewById(R.id.imageViewActionSendMessage);
        editTextMessage = findViewById(R.id.editTextMessage);
        recyclerViewMessage = findViewById(R.id.recyclerViewMessage);
        textViewMessageFriend = findViewById(R.id.textViewMessageFriendConversation);
        textViewIdMessage = findViewById(R.id.textViewMessageFriendConversation);

    }

    @Override
    public void initVariable() {
        Intent intent123 = new Intent(this, ServiceNotification.class);
        stopService(intent123);
        personList = new ArrayList<>();
        finalList = new ArrayList<>();
        a123 = null;
        listID = new ArrayList<>();
        idToDelete = new ArrayList<>();

//        timeSecond = SystemClock.currentThreadTimeMillis();


        preferences = getSharedPreferences("Account", MODE_PRIVATE);
        id1 = preferences.getString("id", "");
//        id1 = ActivityFriend.ID;

//        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        builder = new NotificationCompat.Builder(this, "channelId");
//
//        builder.setSmallIcon(android.R.drawable.ic_dialog_email);
//        builder.setContentTitle("New message");
////        builder.setContentText("Where are you from");
//        builder.setDefaults(Notification.DEFAULT_ALL);
//        builder.setPriority(1);
//
//        Intent intentNotification = new Intent(this, ActivityMessage.class);
//        // gui intent tu ngoai app
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0,
//                intentNotification,
//                0);
//        builder.setContentIntent(pendingIntent);


        idSenderAndReceiverList = new ArrayList<>();
        messageListToDelete = new ArrayList<>();
        messageNotificationList = new ArrayList<>();
        list12 = new ArrayList<>();
        listIntent = new ArrayList<>();

        final SlideInUpAnimator animator = new SlideInUpAnimator();
        final Animation animation12 = AnimationUtils.loadAnimation(this, R.anim.new_animation_left);

        i = 0;
        setSupportActionBar(toolbarMessage);
        imageViewActionSend.setOnClickListener(this);
        imageButtonBackMessage.setOnClickListener(this);
        Intent intent = getIntent();
        messageList = new ArrayList<>();
        messageListReceiver = new ArrayList<>();
        idList = new ArrayList<>();


//        idTest = intent.getStringExtra("idTest");


        //service quan trong
        idSenderAndReceiver = intent.getStringExtra("idSenderAndRecevier");

        // service setValue  = 1
        Intent intent12 = new Intent(this, IntentServiceDemo.class);
        intent12.putExtra("idSenderAndReceiver", idSenderAndReceiver);
        startService(intent12);


        referenceTest = FirebaseDatabase.getInstance().getReference("Message").
                child(idSenderAndReceiver).child("contentMessage");

        referenceDelete1 = FirebaseDatabase.getInstance().getReference("Message").
                child(idSenderAndReceiver).child("contentMessage");

        Intent intent1 = new Intent(this, ServiceDeleteMessage.class);
        intent1.putExtra("idSenderAndReceiver", idSenderAndReceiver);
        startService(intent1);


        textViewIdMessage.setText(getIdMessage(idSenderAndReceiver));
//        idReceiverRequestFriend = intent.getStringExtra("idReceiver");
//        statusFriend = intent.getStringExtra("statusFriend");
//        Log.e("TAG", "idSenderAndReceiver :" + idSenderAndReceiver);
        referenceToDelete = FirebaseDatabase.getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");

        referenceFinal = FirebaseDatabase.getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");

//        Bundle extras = getIntent().getExtras();
//
//        if (extras != null) {
//            idSenderAndReceiver = extras.getString("idSenderAndRecevier");
//            Log.e("TAGasdfsadf", " Notification : " + idSenderAndReceiver);
//        } else {
//            idSenderAndReceiver = intent.getStringExtra("idSenderAndRecevier");
//            Log.e("TAGsadfsafas", "Activity : " + idSenderAndReceiver);
//        }
//        if (idSenderAndReceiver.length() == 0) {
//            onNewIntent(getIntent());
//        }


        referencePerson = FirebaseDatabase.
                getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("time");

        current = System.currentTimeMillis();
        Person person = new Person(id1, current, 0);
        referencePerson.child(id1).setValue(person);

        getReferencePerson1 = FirebaseDatabase.
                getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("time");

//        referencePerson.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                personList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Person person = snapshot.getValue(Person.class);
//                    if (person != null) {
//                        personList.add(person);
//                    }
//                    for (Person person1 : personList){
//                        if (!person1.getId().equals(id1)){
//                            long b = System.currentTimeMillis();
//                            Person person2 = new Person(person1.getId(), b, 0);
//                            getReferencePerson1.child(person1.getId()).setValue(person2); break;
//                        }
//                    }
//                }
////                final Handler handler1 = new Handler() {
////                    @Override
////                    public void handleMessage(android.os.Message msg) {
////                        super.handleMessage(msg);
////                        for (Message message2 : messageList) {
////                            if (message2.getStatusMessage() == 0) {
////                                reference.child(message2.getIdMessage()).child("statusMessage").setValue(1);
////                            }
////                        }
////                    }
////                };
////
////                final Runnable runnable = new Runnable() {
////                    @Override
////                    public void run() {
////                        handler1.sendEmptyMessage(0);
////                    }
////                };
////                for (Person person : personList) {
////                    if (!person.getId().equals(id1)) {
////                        if (a > person.getInRoom() && person.getOutRoom() == 0) {
////                            Toast.makeText(ActivityMessage.this, "thang " + person.getId() + " dang trong phong", Toast.LENGTH_SHORT).show();
////                            handler1.postDelayed(runnable, 10000);
////                        }
////                        if (a > person.getOutRoom()) {
////                            Toast.makeText(ActivityMessage.this, "Thang " + person.getId() + " ra roi", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                }
////                a123 = null;
////                for (Person person : personList) {
////                    if (person.getId().equals(id1)) {
////                        a123 = person;
////                        break;
////                    }
////                }
////                referenceToDelete.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////                            Message message = dataSnapshot1.getValue(Message.class);
////                            if (message.getId_senderSendRequestAddFriend() != Integer.parseInt(id1)) {
////                                if (a123.getInRoom() > message.getTime()) {
////                                    referenceFinal.child(message.getIdMessage()).removeValue();
////                                    listIntent.add(message.getIdMessage());
////                                }
////                            }
////                        }
//////
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                    }
////                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        referenceToDelete.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                listIntent.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Message mes = snapshot.getValue(Message.class);
//                    for (Person person : personList) {
//                        Log.e("TAG", " : " + person.getId());
////                        Toast.makeText(ActivityMessage.this, "hihi " + person.getInRoom(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        referenceTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listID.clear();
                for (DataSnapshot sna : dataSnapshot.getChildren()){
                    Message message = sna.getValue(Message.class);
                    if (message.getStatusMessage() == 1) {
                        listID.add(message.getIdMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference1 = FirebaseDatabase.getInstance().getReference("friends");
//        reference1.child(idSenderAndReceiver).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list12.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Message message = snapshot.getValue(Message.class);
//                    list12.add(message);
//                }
//                for (Message message : list12){
//                    if (message.getId_receiverRequestAddFriend() == Integer.parseInt(id1)){
//                        textViewIdMessage.setText(String.valueOf(message.getId_senderSendRequestAddFriend()));
//                    }else {
//                        textViewIdMessage.setText(String.valueOf(message.getId_receiverRequestAddFriend()));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        // de xoa message
//        referenceDeleteMessage = FirebaseDatabase.
//                getInstance().
//                getReference("Message").
//                child(idSenderAndReceiver).
//                child("contentMessage");
//        referenceDeleteMessage.child(idSenderAndReceiver).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                finalList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Message message = snapshot.getValue(Message.class);
//                    if (message.getStatusMessage() == 1){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        referenceNotification = FirebaseDatabase.
                getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");
        reference = FirebaseDatabase.
                getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");

        referenceDeleteMessage = FirebaseDatabase.getInstance().
                getReference("Message").
                child(idSenderAndReceiver).
                child("contentMessage");
        referenceFriend = FirebaseDatabase.getInstance().getReference("friends");
//        DatabaseReference reference12 = FirebaseDatabase.getInstance().getReference("contentMessage");
//        Query bottom = reference12.child(idSenderAndReceiver).orderByKey().limitToLast(1);

        final List<Message> list = new ArrayList<>();
        referenceFriend.child(idSenderAndReceiver).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    list.add(message);
                }
                if (list.size() > 0) {
                    statusFriend1 = list.get(list.size() - 1).getStatusFriend();
                    if (list.get(list.size() - 1).getId_senderSendRequestAddFriend() != Integer.parseInt(id1)) {
                        idReceiver1 = list.get(list.size() - 1).getId_senderSendRequestAddFriend();
                    } else {
                        idReceiver1 = list.get(list.size() - 1).getId_receiverRequestAddFriend();
                    }
//                    Log.e("TAG", "hihi " + statusFriend1 + " : " + idReceiver1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        referenceFriend.child(idSenderAndReceiver).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        referenceNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if ((message.getStatusMessage()) == 0) {
                        messageNotificationList.add(message);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Log.e("TAG", "idSenderAndReceiver : " + idSenderAndReceiver);

//        bottom.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                messageListToDelete.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Message message = snapshot.getValue(Message.class);
//                    messageListToDelete.add(message);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        reference1.child(idSenderAndReceiver).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Message message = dataSnapshot.getValue(Message.class);
//                if (idSenderAndReceiver == message.getIdSenderAndReceiver()) {
//                    idSenderAndReceiver = message.getIdSenderAndReceiver();
//                    idReceiverRequestFriend = message.getId_receiverRequestAddFriend();
//                    idSenderRequestFriend = message.getId_senderSendRequestAddFriend();
//                    statusFriend = message.getStatusFriend();
//                    Log.e("TAG", "idSenderAndReceiver la :" + message.getIdSenderAndReceiver());
//                    Log.e("TAG", "idReceiver : " + message.getId_receiverRequestAddFriend());
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                messageListReceiver.clear();
                idToDelete.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Message message = dataSnapshot1.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message);

                    }
                }
                if (messageList.size() > 0){
                    for (int i = messageList.size() - 1; i >= 0; i--){
                        if (messageList.get(i).getStatusMessage() == 1){
                            idToDelete.add(messageList.get(i).getIdMessage());
                        }
                    }
                }

//                if (messageList.size() > 0) {
//                    for (int i = messageList.size() - 1; i >= 0; i--) {
//                        if (Integer.parseInt(messageList.get(i).getStatusMessage()) == 1) {
//                            messageList.remove(messageList.get(i));
//                            adapterMessage.notifyItemRemoved(i);
//                        }
//                    }
//                }

//                for (Message message : messageList) {
//                    if (message != null) {
//                        if (message.getStatusMessage() == 1) {
//                            referenceDeleteMessage.child(message.getIdMessage()).removeValue();
//
//       }
//                    }
//                }

//                final Handler h = new Handler(){
//                    @Override
//                    public void handleMessage(android.os.Message msg) {
//                        super.handleMessage(msg);
//                        for (int i = messageList.size() - 1; i >= 0; i--){
//                            messageList.remove(messageList.get(i));
//                            adapterMessage.notifyItemRemoved(i);
//                        }
//                    }
//                };
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        h.sendEmptyMessage(0);
//                    }
//                };
//                h.postDelayed(runnable, 10000);


//                recyclerViewMessage.startAnimation(animation12);

//                for (int i = messageList.size() - 1; i >= 0; i--) {
//                    if (messageList.get(i).getStatusMessage() == 1) {
//                        messageList.remove(messageList.get(i));
////                        adapterMessage.notifyItemRemoved(i);
//                    }
//                }


                adapterMessage = new AdapterMessage(ActivityMessage.this, messageList, id1);
                recyclerViewMessage.setAdapter(adapterMessage);
                adapterMessage.notifyDataSetChanged();
//                adapterMessage.notifyItemChanged(messageList.size() - 1);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(ActivityMessage.this);
                recyclerViewMessage.setLayoutManager(manager);
//                adapterMessage.notifyDataSetChanged();
                if (messageList.size() > 0) {
                    recyclerViewMessage.scrollToPosition(messageList.size() - 1);
                }
                recyclerViewMessage.hasFixedSize();
                recyclerViewMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = messageList.get(i).getMessage();
                        Toast.makeText(ActivityMessage.this, "a " + a, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityMessage.this, "", Toast.LENGTH_SHORT).show();
            }
        });

        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transalte_right);
        final Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transalte_right);


        animation1.setDuration(500);
        imageViewActionSend.setVisibility(View.GONE);

        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.length() > 0) {
                messageContent = editTextMessage.getText().toString().trim();
//                if (checkString(messageContent) == true){
//                    Toast.makeText(ActivityMessage.this, "Khoong", Toast.LENGTH_SHORT).show();
//                }
                if (messageContent.length() > 0) {
                    imageViewActionSend.setVisibility(View.VISIBLE);
//                    imageViewActionSend.setAnimation(animation1);
                } else {
                    imageViewActionSend.setVisibility(View.GONE);
//                    imageViewActionSend.setAnimation(animation);
                    imageViewActionSend.startAnimation(animation1);
//                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        DatabaseReference referenceNotification1 = FirebaseDatabase.getInstance().getReference("Message");
        a = System.currentTimeMillis();
        today = new Date(a);
//        for 1
//        timeFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
//        time = timeFormat.format(today);

        switch (v.getId()) {
            case R.id.imageViewActionSendMessage: {
                messageContent = editTextMessage.getText().toString().trim();
                if (messageContent.length() > 0) {
                    String id = referenceNotification1.push().getKey();
                    String messageContent2 = messageContent + "=>" + id1 + "_" + "1";
                    final Message message = new Message(messageContent2,
                            0,
                            Integer.parseInt(id1),
                            Integer.valueOf(idReceiver1),
                            0,
                            Integer.valueOf(statusFriend1),
                            "",
                            idSenderAndReceiver,
                            id,
                            a);
                    String total = messageContent2 + "=<" +
                            0 + "=<" +
                            id1 + "=<" +
                            idReceiver1 + "=<" +
                            0 + "=<" +
                            statusFriend1 + "=<" +
                            "q" + "=<" +
                            idSenderAndReceiver + "=<" +
                            id + "=<" +
                            a;
                    messageList.add(message);
//                    if (messageList.size() > 0) {
//                        adapterMessage.notifyItemInserted(messageList.size() - 1);
//                    }
//                    adapterMessage.notifyDataSetChanged();
                    Intent intent = new Intent(ActivityMessage.this, IntentServiceSendMessage.class);
                    intent.putExtra("name", total);
                    startService(intent);

//                    referenceNotification1.
//                            child(idSenderAndReceiver).
//                            child("contentMessage").
//                            child(id).
//                            setValue(message);
//                    String notification = messageContent;
//                    if (messageContent.length() > 20) {
//                        notification = messageContent.substring(0, 20) + " . . . ";
//                    }
//                    reference1.child(idSenderAndReceiver).child("message").setValue(notification + "=>" + id1 + "_" + "1");
//                    reference1.child(idSenderAndReceiver).child("time").setValue(a);

//                    Toast.makeText(this,  "idsenderAndReceiver " + idSenderAndReceiver +
//                                    "id1 " + id1 +
//                                    "idReceiver1 " + idReceiver1 +
//                                    "statusFriend : " + statusFriend1 +
//                                    "messageContent : " + messageContent
//                            ,
//                            Toast.LENGTH_LONG).show();
//                    final ArrayList<Message> messageArrayList = new ArrayList<>();
//                    DatabaseReference referenceNotification1 = FirebaseDatabase.getInstance().getReference("contentMessage");
//                    referenceNotification1.child(idSenderAndReceiver).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            messageArrayList.clear();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                Message message1 = snapshot.getValue(Message.class);
//                                if ((message1.getStatusMessage() == 0) &&
//                                        (message1.getId_senderSendRequestAddFriend() == Integer.parseInt(ActivityFriend.ID))){
//                                    messageArrayList.add(message1);
//                                }
//                            }
//                            if (messageArrayList.size() > 0) {
//                                builder.setContentText(messageArrayList.get(messageArrayList.size() - 1).getMessage());
//                                manager.notify(1357, builder.build());
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
                    Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
                    editTextMessage.setText("");
//                    for (Person person : personList) {
//                        if (!person.getId().equals(id1)) {
//                            a123 = person;
//                            break;
//                        }
//                    }

//                    final Handler handler = new Handler() {
//                        @Override
//                        public void handleMessage(android.os.Message msg) {
//                            super.handleMessage(msg);
//                            for (Message message2 : messageList) {
//                                if ((message2.getStatusMessage() == 0) &&
//                                        (message2.getTime() > a123.getInRoom() &&
//                                                (a123.getOutRoom() == 0))) {
//                                    Log.e("TAG", " a123" + a123.getOutRoom());
//                                    reference.child(message2.getIdMessage()).child("statusMessage").setValue(1);
//                                }
//                            }
//                        }
//                    };
//
//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            handler.sendEmptyMessage(0);
//                        }
//                    };

//                    if (!a123.getId().equals(id1)) {
//                        if (a > a123.getInRoom() && a123.getOutRoom() == 0) {
//                            Toast.makeText(this, "thang " + a123.getId() + " dang trong phong", Toast.LENGTH_SHORT).show();
//                            handler.postDelayed(runnable, 10000);
//                        }
//                        if ((a > a123.getOutRoom()) && (a123.getOutRoom() != 0)) {
//                            Toast.makeText(this, "Thang " + a123.getId() + " da ra ngoai", Toast.LENGTH_SHORT).show();
//                        }
//                    }


//                    if (messageNotificationList.size() > 0) {
//                        for (int i = messageNotificationList.size() - 1; i >= 0; i--) {
//                            if ((messageNotificationList.get(i).getId_senderSendRequestAddFriend() != Integer.parseInt(id1)) &&
//                                    (messageNotificationList.get(i).getId_receiverRequestAddFriend() == Integer.parseInt(id1))){
//                                referenceNotification.child(messageNotificationList.get(i).getIdSenderAndReceiver()).
//                                        child("message").
//                                        setValue(messageNotificationList.get(i).getMessage());
//                                break;
//                            }
//                        }
//                    }
//                } else {
//                    referenceNotification.child(idSenderAndReceiver).child("message").setValue("....................");
//                }


//                    for (Message message1 : messageListToDelete) {
//                        Log.e("TAG", "IdSender : " + message1.getId_senderSendRequestAddFriend());
//                        Log.e("TAG", "message : " + message1.getMessage());
//                        Log.e("TAG", "ID : " + id1);
//                        if ((Integer.parseInt(id1) == (message1.getId_senderSendRequestAddFriend()))) {
//                            isCheckMessage = true;
//                        } else {
//                            isCheckMessage = false;
//                        }
//                    }
//                    Log.e("TAG", "test " + messageList.get(messageList.size() - 1).getMessage());


//                    if (isCheckMessage == false) {
////                        for (final Message message1 : messageList) {
//                            handler = new Handler(){
//                                @Override
//                                public void handleMessage(android.os.Message msg) {
//                                    super.handleMessage(msg);
//                                    for (Message message2 : messageList) {
//                                        if (message2.getId_senderSendRequestAddFriend() != Integer.parseInt(id1)) {
//                                            reference.child(idSenderAndReceiver).child(message2.getIdMessage()).child("statusMessage").setValue(1);
//                                        }
//                                    }
//                                    for (int i = messageList.size() - 1; i >= 0; i--){
//                                        if (messageList.get(i).getStatusMessage() == 1){
//                                            messageList.remove(messageList.get(i));
//                                            adapterMessage.notifyItemRemoved(i);
//                                        }
//                                    }
//                                }
//                            };
//
////                            reference.child(idSenderAndReceiver).child(message1.getIdMessage()).child("statusMessage").setValue(1);
//
////                                    reference.child(idSenderAndReceiver).child(message1.getIdMessage()).removeValue()
////                        }
//
//
////                            reference.child(idSenderAndReceiver).child(message1.getIdMessage()).child("statusMessage").setValue(1);
//
//
////                        Log.e("TAG", "IdMessage : " + message1.getIdMessage());
////                        handler = new Handler() {
////                            @Override
////                            public void handleMessage(android.os.Message msg) {
////                                super.handleMessage(msg);
////                                for (int i = 0; i < messageList.size() - 1; i++) {
////                                    reference.child(idSenderAndReceiver).child(messageList.get(i).getIdMessage()).child("statusMessage").setValue(1);
////                                }
////                            }
////                        };
////                            reference.child(idSenderAndReceiver).child(message1.getIdMessage()).child("statusMessage").setValue(1);
//
//
//                    }


                    // ham xoa tin nhan sau 30s
//                    final Handler handler1 = new Handler() {
//                        @Override
//                        public void handleMessage(android.os.Message msg) {
//                            super.handleMessage(msg);
//                            for (int i = messageList.size() - 1; i >= 0; i--) {
//                                if (messageList.get(i).getStatusMessage() == 1) {
//                                    Log.e("TAG", " IdMessage : " + messageList.get(i).getIdMessage());
////                                    messageList.remove(messageList.get(i));
////                                    adapterMessage.notifyItemRemoved(i);
//                                    messageList.remove(messageList.get(i));
//                                    adapterMessage.notifyItemRemoved(i);
//                                }
//                            }
//
//                        }
//                    };

//                    Runnable rn = new Runnable() {
//                        @Override
//                        public void run() {
//                            handler1.sendEmptyMessage(0);
//                        }
//                    };

//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            handler.sendEmptyMessage(0);
//                        }
//                    };
//                    Log.e("TAG", "isCheck : " + isCheck);
//                    if (isCheckMessage == false) {
//                        handler.postDelayed(runnable, 30000);
////                        handler1.postDelayed(rn, 10000);
//                    }


//                    Log.e("asdfasdf", "isCheckMessage : " + isCheckMessage);
//                    isCheckMessage = false;


//                    final Intent intent1 = new Intent(ActivityMessage.this, ServiceDeleteMessage.class);
//                    intent1.putExtra("idSenderAndReceiver", idSenderAndReceiver);
//                    intent1.putExtra("idMessage", id);


//                    final List<Intent> intentList = new ArrayList<>();
//                    intentList.add(intent1);
//                    for (Intent intent12 : intentList){
//                        startService(intent12);
//                    }
//                    startService(intent1);
//                    threadShutDownService = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(35000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            for (Intent intent : intentList) {
//                                stopService(intent);
//                            }
//                        }
//                    });
//                    threadShutDownService.start();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(30000);
//                                stopService(intent1);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//                    service = Executors.newSingleThreadScheduledExecutor();
//                    service.scheduleWithFixedDelay(new Runnable() {
//                        @Override
//                        public void run() {
//                            for (int i = 0; i < idList.size(); i++){
//                                Log.e("TAG", "idList : " + idList.get(i));
////                                if (message.getIdMessage().equals(idList.get(i))){
////                                    messageList.remove(idList.get(i));
//////                                    adapterMessage = new AdapterMessage(ActivityMessage.this, messageList, ActivityFriend.ID);
////                                    adapterMessage.remove(i);
////                                    recyclerViewMessage.getItemAnimator().setRemoveDuration(2000);
////                                }
//                                reference.child(idSenderAndReceiver).child(idList.get(i)).removeValue();
//                            }
////                            for (String s : idList) {
////                                if (message.getIdMessage().equals(s)){
////                                    messageList.remove(message);
////                                    adapterMessage = new AdapterMessage(ActivityMessage.this, messageList, ActivityFriend.ID);
////                                    adapterMessage.notifyItemRemoved();
////                                    recyclerViewMessage.getItemAnimator().setRemoveDuration(2000);
////                                }
////
////                                reference.child(idSenderAndReceiver).child(s).removeValue();
////
////                            }
//                        }
//                    }, 30, 30, TimeUnit.SECONDS);
//                    threadShutDownService = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(35000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            service.shutdown();
//                        }
//                    });
//                    threadShutDownService.start();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(35000);
//                                service.shutdown();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }fdsf
//                    }).start();

                } else {
                    Toast.makeText(this, "Your message is empty", Toast.LENGTH_SHORT).show();
                }


            }
            break;
            case R.id.imageButtonBackMessage: {
                Intent intent = new Intent(ActivityMessage.this, ActivityFriend.class);
//                stopService(intent1);
                Intent intent1 = new Intent(this, ServiceDeleteMessage.class);
                stopService(intent1);
                long outRoom = System.currentTimeMillis();
                referencePerson.child(id1).child("outRoom").setValue(outRoom);
//                Intent intent1 = new Intent(ActivityMessage.this, IntentServiceDemo.class);
//                intent1.putExtra("idSenderAndReceiver", idSenderAndReceiver);
                intent.putExtra("id", id1);
//                threadShutDownService.stop();
//                startService(intent1);
//                for (String m : listID){
//                    referenceDelete1.child(m).removeValue();
//                }

                startActivity(intent);
                overridePendingTransition(R.anim.rotate_fade, R.anim.fade_out);
//                finish();
            }
            break;
        }

    }

    private boolean check(String a, String b) {
        return a.equals(b);
    }

    private String charToString(char[] array) {
        String b = "";
        for (int i = 0; i < array.length; i++) {
            b += array[i];
        }
        return b;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityMessage.this, ActivityFriend.class);
        Intent intent1 = new Intent(this, ServiceDeleteMessage.class);
        stopService(intent1);
        long outRoom = System.currentTimeMillis();
        referencePerson.child(id1).child("outRoom").setValue(outRoom);
//        Intent intent1 = new Intent(ActivityMessage.this, IntentServiceDemo.class);
//        intent1.putExtra("idSenderAndReceiver", idSenderAndReceiver);
//                stopService(intent1);
        intent.putExtra("id", id1);
//                threadShutDownService.stop();
//        startService(intent1);
        startActivity(intent);
        overridePendingTransition(R.anim.rotate_fade, R.anim.fade_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        long outRoom = System.currentTimeMillis();
        referencePerson.child(id1).child("outRoom").setValue(outRoom);
        Intent intent = new Intent(this, ServiceNotification.class);
        Intent intent1 = new Intent(this, IntentServiceDemo.class);
        stopService(intent1);
//        intent.putExtra(idSenderAndReceiver, idSenderAndReceiver);
        startService(intent);
    }


    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, ServiceNotification.class);
////        intent.putExtra(idSenderAndReceiver, idSenderAndReceiver);
//        stopService(intent);
//    }
    //
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = new Intent(this, ServiceNotification.class);
//        stopService(intent);
//    }
//    private void setupWindowAnimations() {
//        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.fade);
//        getWindow().setEnterTransition(fade);
//    }
//    private void setupWindowAnimations() {
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);
//
//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        getWindow().setReturnTransition(slide);
//    }

    private String getIdMessage(String a) {
        String idMessage = "";
        String id = "";
        String[] a1 = a.split("_");
        if (a1[0].equals(id1)) return a1[1];
        return a1[0];
    }




    // kiem tra ky tu trong tin nhan
    private boolean checkString(String s) {
        String a = "=>";
        String b = "=<";
        String c = "";
        for (int i = 0; i < s.length() - 1; i++) {
            c = "";
            c = s.charAt(i) + s.charAt(i + 1) + "";
            if (c.trim().equals(a) || (c.trim().equals(b))) {
                return true;
            }
        }
        return false;
    }


}
