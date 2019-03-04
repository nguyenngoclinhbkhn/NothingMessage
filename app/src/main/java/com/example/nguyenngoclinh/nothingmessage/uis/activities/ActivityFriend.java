package com.example.nguyenngoclinh.nothingmessage.uis.activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.nothingmessage.R;
import com.example.nguyenngoclinh.nothingmessage.adapter.AdapterFriend;
import com.example.nguyenngoclinh.nothingmessage.model.Message;
import com.example.nguyenngoclinh.nothingmessage.model.User;
import com.example.nguyenngoclinh.nothingmessage.service.IntentServiceDemo;
import com.example.nguyenngoclinh.nothingmessage.service.ServiceNotification;
import com.example.nguyenngoclinh.nothingmessage.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityFriend extends BaseActivity {

    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    private Message messageNotification;

    private int RECEIVER;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private TextView textViewIdAccount;
    private FloatingActionButton buttonAdd;
    public static String ID;
    private String idRequestFriend;
    private ArrayList<Long> idList;
    private DatabaseReference reference;
    private DatabaseReference reference1;
    private AdapterFriend adapterFriend;
    private List<Message> messageList;
    private int notification = R.drawable.security;
    private boolean isCheck;
    private TextView textViewId;
    private ArrayList<String> idSenderAndReceiverList;
    private ImageView imageViewActionSearch;
    private DatabaseReference referencePass;

    private SharedPreferences preferences;

    private DatabaseReference referenceMessage;

    private boolean isLogin;
    private DatabaseReference referenceNotification;

    private DatabaseReference referenceFriend;

    private List<Message> contentMessageList;
    private DatabaseReference getReferenceNotification1;

    private SharedPreferences preferences2;
    private List<Message> list;

    private DatabaseReference referenceDeleteMessage;
    private ArrayList<String> listIntent;
    private DatabaseReference referencePerson;
    private Handler handler;
    private Runnable runnable;
    private SharedPreferences getPreferences;
    private String m;
    private boolean is;
    private String a;
    private DatabaseReference referenceTest;
    private String pass;
    private boolean isLogin1;


    //    private
    @Override
    public int injectLayout() {
        return R.layout.activity_friend;
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbarFriend);
        drawerLayout = findViewById(R.id.drawerLayoutMain);
        navigationView = findViewById(R.id.navigationViewFriend);
        recyclerView = findViewById(R.id.recyclerViewFriend);
        textViewIdAccount = navigationView.getHeaderView(0).findViewById(R.id.textViewIdAccount);
        buttonAdd = findViewById(R.id.floatingActionButtonAdd);
        imageViewActionSearch = findViewById(R.id.imageViewActionSearch);
    }

    @Override
    public void initVariable() {
        is = false;
        list = new ArrayList<>();
        preferences2 = getSharedPreferences("Account", Context.MODE_PRIVATE);
        getPreferences = getSharedPreferences("id1", Context.MODE_PRIVATE);

        ID = preferences2.getString("id", "");
        pass = preferences2.getString("pass", "");
        isLogin1 = preferences2.getBoolean("isLogin", false);
        referenceNotification = FirebaseDatabase.getInstance().getReference("contentMessage");
        referenceFriend = FirebaseDatabase.getInstance().getReference("friends");
        getReferenceNotification1 = FirebaseDatabase.getInstance().getReference("contentMessage");
//        if (isLogin1 == true) {
//            referencePass = FirebaseDatabase.getInstance().getReference("users").child(ID);
//            referencePass.child("pass").setValue(pass);
//        }
        Intent intent12 = new Intent(this, ServiceNotification.class);
        startService(intent12);

        referenceTest = FirebaseDatabase.getInstance().getReference("friends");

//        referenceTest.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Message message = snapshot.getValue(Message.class);
//                    if (((message.getStatusFriend() == 1) && (message.getId_senderSendRequestAddFriend() == Integer.parseInt(ID)))) {
//                        list.add(message);
//                    }
//                    if ((message.getStatusFriend() == 1) && (message.getId_receiverRequestAddFriend() == Integer.parseInt(ID))) {
////                        idTest = ID + message.getId_senderSendRequestAddFriend();
//                        list.add(message);
//                    }
//
//                }
//                for (Message mes : list) {
//                    Log.e("TAG", " : " + mes.getIdSenderAndReceiver());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        referencePerson = FirebaseDatabase.
                getInstance().
                getReference("Message");


//        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        builder = new NotificationCompat.Builder(this,"channelId");
//
//        builder.setSmallIcon(android.R.drawable.ic_dialog_email);
//        builder.setContentTitle("New message");
//        builder.setContentText("Where are you from");
//        builder.setDefaults(Notification.DEFAULT_ALL);
//        builder.setPriority(1);

//        Intent service = new Intent(this, ServiceNotification.class);
//        service.putExtra("id", ID);
//        startService(service);

//        Intent intentNotification = new Intent(this, ActivityFriend.class);
//        // gui intent tu ngoai app
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0,
//                intentNotification,
//                0);
//        builder.setContentIntent(pendingIntent);

        // khai bao mang chua cac idSenderAndReceiver

        final ArrayList<String> arrayListIdSenderAndReceiver = new ArrayList<>();
        final ArrayList<Message> arrayListContentMessage = new ArrayList<>();
        listIntent = new ArrayList<>();
        final ArrayList<Message> arrayListTest = new ArrayList<>();

//        Log.e("TAG", "ID APP : " + ActivityFriend.ID);
//        referenceFriend.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayListIdSenderAndReceiver.clear();
//
//                arrayListTest.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Message message1 = snapshot.getValue(Message.class);
//                    arrayListTest.add(message1);
//
//                    if ((message1.getId_senderSendRequestAddFriend() == Integer.parseInt(ActivityFriend.ID)) ||
//                            (message1.getId_receiverRequestAddFriend() == Integer.parseInt(ActivityFriend.ID))) {
//                        arrayListIdSenderAndReceiver.add(message1.getIdSenderAndReceiver());
//                    }
//                    Log.e("TAG ID APP", " " + ActivityFriend.ID);
//                    for (Message s : arrayListTest){
//                        Log.e("TAG idSender : ", "" + s.getId_senderSendRequestAddFriend());
//                    }
//                }
//
//                // khai bao mang de lay cac message chua doc
//                final ArrayList<String> arrayListMessage = new ArrayList<>();
//                for (String s : arrayListIdSenderAndReceiver){
//                    getReferenceNotification1.child(s).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            arrayListContentMessage.clear();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                Message message1 = snapshot.getValue(Message.class);
//                                if ((message1.getStatusMessage() == 0) ){
//                                    arrayListContentMessage.add(message1);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                    for (int i = arrayListContentMessage.size() - 1; i >= 0; i--){
//                        if (arrayListContentMessage.get(i).getId_senderSendRequestAddFriend() != Integer.parseInt(ActivityFriend.ID)){
//                            builder.setContentText(arrayListContentMessage.get(i).getMessage());
//                            manager.notify(1357, builder.build()); break;
//                        }
//                    }
//                }
////                if ()
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        referenceDeleteMessage = FirebaseDatabase.getInstance().
                getReference("Message");

        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);


        isLogin = false;
        idRequestFriend = "0";
        Log.e("TAG", ":" + notification);
        idSenderAndReceiverList = new ArrayList<>();
        contentMessageList = new ArrayList<>();
//        idTest = "";
        idList = new ArrayList<>();
        setSupportActionBar(toolbar);
//        final Intent intent = getIntent();
//        ID = intent.getStringExtra("id");
//        Log.e("kakaka", " : " + intent.getStringExtra("idSenderAndReceiver"));
        textViewIdAccount.setText(ID);
        buttonAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        messageList = new ArrayList<>();

        final Intent intentNotification = new Intent(this, LoginActivity.class);

        reference1 = FirebaseDatabase.getInstance().getReference("friends");


        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                idSenderAndReceiverList.clear();
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null) {
                        if (((message.getStatusFriend() == 1) && (message.getId_senderSendRequestAddFriend() == Integer.parseInt(ID)))) {
                            messageList.add(message);
                            list.add(message);
                            idSenderAndReceiverList.add(message.getIdSenderAndReceiver());
                        }
                        if ((message.getStatusFriend() == 1) && (message.getId_receiverRequestAddFriend() == Integer.parseInt(ID))) {
//                        idTest = ID + message.getId_senderSendRequestAddFriend();
                            messageList.add(message);
                            list.add(message);
                            idSenderAndReceiverList.add(message.getIdSenderAndReceiver());
                        }


                        for (Message m : messageList) {
                            Log.i("TAG", "time " + m.getTime());

                        }
//                    messageList.add(message);


                    }
//                for (final String s : idSenderAndReceiverList) {
//                    referenceNotification.child(s).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                contentMessageList.clear();
//                                Message message = snapshot.getValue(Message.class);
//                                if (message.getStatusMessage() == 0) {
//                                    contentMessageList.add(message);
//                                }
//                            }
////                            if (contentMessageList.size() > 0) {
////                                if ((contentMessageList.get(contentMessageList.size() - 1).getId_senderSendRequestAddFriend() != Integer.parseInt(ID)) &&
////                                        (contentMessageList.get(contentMessageList.size() - 1).getId_receiverRequestAddFriend() == Integer.parseInt(ID))) {
//////                                if (Integer.parseInt(ID) != contentMessageList.get(contentMessageList.size() - 1).getId_senderSendRequestAddFriend()){
////                                    referenceFriend.child(contentMessageList.get(contentMessageList.size() - 1).getIdSenderAndReceiver()).child("message").
////                                            setValue(contentMessageList.get(contentMessageList.size() - 1).getMessage());
////                                    referenceFriend.child(contentMessageList.get(contentMessageList.size() - 1).getIdSenderAndReceiver()).child("time").
////                                            setValue(contentMessageList.get(contentMessageList.size() - 1).getTime());
////                                    Log.e("TAG", "contentMessageSize : " + contentMessageList.size());
//////                                }
////                                }
////                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//                }
                    for (Message m : messageList) {
                        Log.i("TAG", "message " + m.getIdSenderAndReceiver() + ":" + m.getMessage() + "\n");
                    }
                    Collections.sort(messageList, new Comparator<Message>() {
                        @Override
                        public int compare(Message o1, Message o2) {
                            if (o1.getTime() > o2.getTime()) return -1;
                            else return 1;
                        }
                    });

                    adapterFriend = new AdapterFriend(ActivityFriend.this, messageList, Integer.parseInt(ID));
                    recyclerView.setAdapter(adapterFriend);
                    adapterFriend.notifyDataSetChanged();


                    RecyclerView.LayoutManager manager = new LinearLayoutManager(ActivityFriend.this);
                    recyclerView.hasFixedSize();
                    recyclerView.setLayoutManager(manager);


                    adapterFriend.setOnItemClickedListener(new AdapterFriend.OnItemClickedListener() {
                        @Override
                        public void OnItemClicked(View view, int position) {
//                        Intent intentNotification = new Intent(builder, ActivityMessage.class);
//                        // gui intent tu ngoai app
//                        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                                0,
//                                intentNotification,
//                                0);
//                        builder.setContentIntent(pendingIntent);
                            Message message = messageList.get(position);
                            messageNotification = messageList.get(position);
//                            for (Message s : list) {
//                                Log.e("TAG", "s : " + s.getIdSenderAndReceiver());
////                            if (s.getIdSenderAndReceiver() == messageNotification.getIdSenderAndReceiver()) {
////                                is = true;
////                                break;
////                            }
//                            }
//                            if (is == true) {
//                                a = messageNotification.getIdSenderAndReceiver();
//                                Log.e("TAG", "isOk true " + a);
//                            } else {
//                                a = replaceString(messageNotification.getIdSenderAndReceiver());
//                                Log.e("TAG", "isOk false " + a);
//
//                            }
//                            Toast.makeText(ActivityFriend.this, "hhihi " + a, Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(ActivityFriend.this, ActivityMessage.class);

//                        preferences2.edit().putString(messageNotification.getIdSenderAndReceiver(),messageNotification.getIdSenderAndReceiver()).commit();
//                        intentNotification.putExtra("idSenderAndRecevier", messageNotification.getIdSenderAndReceiver());
//                        intentNotification.putExtra("idRequest", idRequestFriend);
//                        intentNotification.putExtra("statusFriend", messageNotification.getStatusMessage());

                            intent1.putExtra("idSenderAndRecevier", messageNotification.getIdSenderAndReceiver());
//                        final long inRoom = System.currentTimeMillis();
//                        Person person = new Person(ID, inRoom, 0);
//                        referencePerson.
//                                child(idSenderAndReceiver).
//                                child("time").
//                                child(ID).
//                                setValue(person);

//                        referenceDeleteMessage.child(idSenderAndReceiver).
//                                child("contentMessage").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                listIntent.clear();
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                    Message message = snapshot.getValue(Message.class);
//                                    if (message.getId_senderSendRequestAddFriend() != Integer.parseInt(ID)){
//                                        if (inRoom > message.getTime()){
//                                            listIntent.add(message.getIdMessage());
//                                        }
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });


                            intent1.putExtra("idRequest", idRequestFriend);
                            if (Integer.parseInt(ID) == messageNotification.getId_senderSendRequestAddFriend()) {
                                intent1.putExtra("idReceiver", String.valueOf(messageNotification.getId_receiverRequestAddFriend()));
//                            intentNotification.putExtra("idReceiver", messageNotification.getId_receiverRequestAddFriend());
                            } else {
                                intent1.putExtra("idReceiver", String.valueOf(messageNotification.getId_senderSendRequestAddFriend()));
//                            intentNotification.putExtra("idReceiver", messageNotification.getId_senderSendRequestAddFriend());
                            }
                            intent1.putExtra("statusFriend", String.valueOf(messageNotification.getStatusFriend()));
                            intent1.putExtra("statusMessage", String.valueOf(messageNotification.getStatusMessage()));
                            intent1.putStringArrayListExtra("idSenderAndReceiverList", idSenderAndReceiverList);

//                        intent1.putExtra("")
//                        intent1.putExtra("idTest", idTest);
//                        startService(intent2);

                            startActivity(intent1);
                            overridePendingTransition(R.anim.new_animation_left, R.anim.fade_out);


//                        showAlertDialong();
                            Log.e("TAG", "idReceiver :" + messageNotification.getId_senderSendRequestAddFriend());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        for (String s : idSenderAndReceiverList) {
            Log.e("idSenderAndReceiver ", " kekeke " + s);
        }

        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    idList.add(user.getID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // gui intent tu ngoai app
//        intentNotification.putExtra()
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0,
//                intentNotification,
//                0);
//        builder.setContentIntent(pendingIntent);
        toolbar.setNavigationIcon(R.drawable.ic_format_list_bulleted);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.itemListFriend: {
                        adapterFriend.notifyDataSetChanged();
                        Toast.makeText(ActivityFriend.this, "ListFriend", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case R.id.itemLogout: {
                        Intent intent1 = new Intent(ActivityFriend.this, ServiceNotification.class);
                        stopService(intent1);
                        Intent intent123 = new Intent(ActivityFriend.this, IntentServiceDemo.class);
                        stopService(intent123);
                        preferences.edit().putBoolean("isLogin", false).commit();
                        Intent intent = new Intent(ActivityFriend.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
//                        ActivityFriend.this.finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.rotate_fade, R.anim.fade_out);
                    }
                    break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                return false;
            }
        });
        imageViewActionSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                showCustumDialogSearch();
                openDialog();
            }
        });

//        Intent intent12 = new Intent(this, IntentServiceDemo.class);
//        intent12.putStringArrayListExtra("idSenderAndReceiverList", idSenderAndReceiverList);
//        startService(intent12);

    }

    private void openDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialong_test);
        dialog.setTitle(null);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Button btnOk = dialog.getWindow().findViewById(R.id.btnTestOk);
        Button btnCancel = dialog.getWindow().findViewById(R.id.btnTestCancel);
        final EditText editText = dialog.getWindow().findViewById(R.id.editTextTestIdSearch);
        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String idFriend = editText.getText().toString().trim();
                if (idFriend.length() > 0) {
                    Intent intent = new Intent(ActivityFriend.this, ActivitySearchFriendId.class);
                    intent.putExtra("idSearch", idFriend);
                    startActivity(intent);
                    dialog.dismiss();
                    Toast.makeText(ActivityFriend.this, "Searched", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityFriend.this, "Your id's friend is empty", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(ActivityFriend.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();



    }

    public void showCustumDialogSearch() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog_search_friend_by_id, null);
        viewDialog.startAnimation(a);
        builder.setView(viewDialog);
        final EditText editTextSearchFriend = viewDialog.findViewById(R.id.editTextSearchIDFriendDialog);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idFriend = editTextSearchFriend.getText().toString().trim();
                if (idFriend.length() > 0) {
                    Intent intent = new Intent(ActivityFriend.this, ActivitySearchFriendId.class);
                    intent.putExtra("idSearch", idFriend);
                    startActivity(intent);
                    Toast.makeText(ActivityFriend.this, "Searched", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityFriend.this, "Your id's friend is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ActivityFriend.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void showCustomDialog() {
        // buoc 1 : dinh nghia alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // b2 : thiet lap thong tin, title, body(view), button
//        builder.setTitle("Find friend by id");
        // tao 1 view de gan cho body
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog_add_friend, null);
        builder.setView(viewDialog);
        final EditText editTextId = viewDialog.findViewById(R.id.editTextSearchID);
        builder.setPositiveButton("Add friend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idRequestFriend = editTextId.getText().toString().trim();
//                RECEIVER = Integer.parseInt(idRequestFriend);
                if (TextUtils.isEmpty(idRequestFriend)) {
                    Toast.makeText(ActivityFriend.this, "Friend's ID is empty", Toast.LENGTH_SHORT).show();
                } else if (idRequestFriend.equals(ID)) {
                    Toast.makeText(ActivityFriend.this, "Don't add friend yourselft", Toast.LENGTH_SHORT).show();
                } else {

                    String id = ID + "_" + idRequestFriend;
                    boolean isCheck1 = false;
                    for (String s : idSenderAndReceiverList) {
                        if ((id.equals(s)) || (s.equals(replaceString(id)))) {
                            isCheck1 = true;
                            break;
                        } else {
                            isCheck1 = false;
                        }
                    }
                    if (searchID(Integer.parseInt(idRequestFriend), idList) == 0) {
                        Toast.makeText(ActivityFriend.this, "Not find result", Toast.LENGTH_SHORT).show();
                    }
                    if ((searchID(Integer.parseInt(idRequestFriend), idList) != 0) && isCheck1 == false) {
                        Message message = new Message("............................=> ..........",
                                0,
                                Integer.valueOf(ID),
                                Integer.parseInt(idRequestFriend),
                                0,
                                1,
                                "",
                                id,
                                "",
                                0);
                        reference1.child(id).setValue(message);
                        Toast.makeText(ActivityFriend.this, "Added " + id, Toast.LENGTH_SHORT).show();
                    }
                    if (isCheck1 == true) {
                        Toast.makeText(ActivityFriend.this, "Your friend is added ago long time", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ActivityFriend.this, "cancel", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }
//    public void showAlertDialong() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        //b2 : thiet lap thong tin , title, message, button
//        builder.setTitle("Title");
//        builder.setMessage("Do you want to delete item ?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                reference1.child(ID + idClicked).child("statusFriend").setValue(0);
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(ActivityFriend.this, "Khong lam gi ca", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.show();
//    }

    private int searchID(int a1, ArrayList<Long> a) {
        for (int i = 0; i < a.size(); i++) {
            if (a1 == a.get(i)) {
                return a1;
            }
        }
        return 0;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemDecrementById: {
                if (messageList.size() > 0) {
                    for (Message message : messageList) {
                        if (!ActivityFriend.ID.equals(findID(message.getIdSenderAndReceiver()))) {
                            String idTest = replaceString(message.getIdSenderAndReceiver());
                            message.setImageResource(idTest);
                        } else {
                            message.setImageResource(message.getIdSenderAndReceiver());
                        }
                    }
                    Collections.sort(messageList, new Comparator<Message>() {
                        @Override
                        public int compare(Message o1, Message o2) {
                            return o2.getImageResource().compareTo(o1.getImageResource());
                        }
                    });
                    adapterFriend.notifyDataSetChanged();
                }
            }
            break;
            case R.id.itemIncrementByID: {
                if (messageList.size() > 0) {
                    for (Message message : messageList) {
                        if (!ActivityFriend.ID.equals(findID(message.getIdSenderAndReceiver()))) {
                            String idTest = replaceString(message.getIdSenderAndReceiver());
                            message.setImageResource(idTest);
                        } else {
                            message.setImageResource(message.getIdSenderAndReceiver());
                        }
                    }
                    Collections.sort(messageList, new Comparator<Message>() {
                        @Override
                        public int compare(Message o1, Message o2) {
                            return o1.getImageResource().compareTo(o2.getImageResource());
                        }
                    });

//                adapterFriend = new AdapterFriend(this, messageList, Integer.parseInt(ID));
//                recyclerView.setAdapter(adapterFriend);
                    adapterFriend.notifyDataSetChanged();
                }
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String findID(String c) {
        String a = "";
        String b = "";
        int count = 0;
        for (int i = 0; i < c.length(); i++) {
            if (c.charAt(i) == '_') {
                count = i;
                break;
            }
        }
        a = c.substring(0, count);
        return a;
    }

    public String replaceString(String c) {
        String a = "";
        String b = "";
        int countUnderLine = 0;
        for (int i = 0; i < c.length(); i++) {
            if (c.charAt(i) == '_') {
                countUnderLine = i;
                break;
            }
        }
        for (int i = 0; i < countUnderLine; i++) {
            a += c.charAt(i);
        }
        for (int i = countUnderLine + 1; i < c.length(); i++) {
            b += c.charAt(i);
        }
        String s = b + "_" + a;
        return s;
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Intent intent = new Intent(this, ServiceNotification.class);
//        startService(intent);
//    }

    //    @Override
//    protected void onDestroy() {
//        Intent intent = new Intent(this, ServiceNotification.class);
//        startService(intent);
//        super.onDestroy();
//
//    }
//    private void setupWindowAnimations() {
//        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.fade);
//        getWindow().setEnterTransition(fade);
//    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent1 = new Intent(ActivityFriend.this, ServiceNotification.class);
//        stopService(intent1);
//        preferences.edit().putBoolean("isLogin", false).commit();
//        Intent intent = new Intent(ActivityFriend.this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
////                        ActivityFriend.this.finish();
//        startActivity(intent);
//        overridePendingTransition(R.anim.transalte_right, R.anim.transalte_right);
    }


//    private void setupWindowAnimations() {
//        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.slide);
//        getWindow().setExitTransition(slide);
//    }


}
