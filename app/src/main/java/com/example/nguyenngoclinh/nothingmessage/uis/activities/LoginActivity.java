package com.example.nguyenngoclinh.nothingmessage.uis.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguyenngoclinh.nothingmessage.R;
import com.example.nguyenngoclinh.nothingmessage.model.User;
import com.example.nguyenngoclinh.nothingmessage.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText editTextID;
    private EditText editTextPass;
    private Button buttonLogin;
    private Button buttonRegister;
    private String ID;
    private String pass;
    private DatabaseReference reference;
    private Toolbar toolbar;
    private List<User> listUser;
    private int isCheck = 0;
    public static final String notificationMessage = "Hello, you have a new message";

    private SharedPreferences preferences;
    private boolean isLogin;

    private int i = 0;
    private boolean isOk;

    private NotificationManager manager;
    private NotificationCompat.Builder builder;


    private DatabaseReference referenceTest;


    @Override
    public int injectLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        editTextID = findViewById(R.id.editTextIdAccount);
        editTextPass = findViewById(R.id.editTextPassAccount);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

    }

    @Override
    public void initVariable() {
//        buttonLogin.setEnabled(false);
        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        isLogin = false;
        ID = preferences.getString("id", "");
        pass = preferences.getString("pass", "");
        isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin == true) {
            Intent intent = new Intent(this, ActivityFriend.class);
            startActivity(intent);
        }


//        testLogin();
//        ID = editTextID.getText().toString().trim();
//        pass = editTextPass.getText().toString().trim();
//        if (ID.length() > 0 && pass.length() > 0){
//            buttonLogin.setEnabled(true);
//        }


//        isOk = false;
        reference = FirebaseDatabase.getInstance().getReference("users");
//        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
//        isLogin = false;
//        ID = preferences.getString("id", "");
//        pass = preferences.getString("pass", "");
//        isLogin = preferences.getBoolean("isLogin", false);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User user = snapshot.getValue(User.class);
//                    listUser.add(user);
//
//                }
//                for (User user : listUser) {
////                    Log.e("TAG", "userName : " + user.getID());
////                    Log.e("TAG", "id : " + ID);
////                    Log.e("TAG", "pass : " + pass);
////                    Log.e("TAG", "isLogin : " +isLogin);
//                    if ((isLogin == true) && (Integer.parseInt(ID) == (user.getID())) &&
//                            (pass.equals(user.getPass()))) {
//                        isOk = true;
//                        Log.e("TAG", "isOk : " + isOk);
//                        break;
//                    }
//                }
//                Log.e("TAG", "isOk" + isOk);
//                if (isOk == true) {
//                    Intent intent = new Intent(LoginActivity.this, ActivityFriend.class);
//                    intent.putExtra("id", ID);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        referenceTest = FirebaseDatabase.getInstance().getReference("test");
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        listUser = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    listUser.add(user);

                }
//                for (User user : listUser) {
////                    Log.e("TAG", "userName : " + user.getID());
////                    Log.e("TAG", "id : " + ID);
////                    Log.e("TAG", "pass : " + pass);
////                    Log.e("TAG", "isLogin : " +isLogin);
//                    if ((isLogin == true) && (Integer.parseInt(ID) == (user.getID())) &&
//                            (pass.equals(user.getPass()))) {
//                        isOk = true;
//                        Log.e("TAG", "isOk : " + isOk);
//                        break;
//                    }
//                }
//                Log.e("TAG", "isOk" + isOk);
//                if (isOk == true) {
//                    Intent intent = new Intent(LoginActivity.this, ActivityFriend.class);
//                    intent.putExtra("id", ID);
//                    startActivity(intent);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        for (User user : listUser) {
            Log.e("TAG", "userName : " + user.getID());
        }
        editTextID.setBackgroundResource(R.drawable.bg_message);
        buttonLogin.setTextColor(Color.BLACK);
        editTextPass.setEnabled(false);
        editTextPass.setBackgroundResource(R.drawable.bg_message);
        buttonLogin.setEnabled(false);
        buttonLogin.setBackgroundResource(R.drawable.bg_message);
        editTextID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String a = editTextID.getText().toString().trim();
                if (a.length() > 0) {
                    editTextPass.setEnabled(true);
                } else {
                    editTextPass.setEnabled(false);
                }
            }
        });

        editTextPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String a = editTextID.getText().toString().trim();
                String b = editTextPass.getText().toString().trim();
                if (a.length() > 0 && b.length() > 0) {
                    buttonLogin.setBackgroundResource(R.drawable.bg_image_view_click);
                    buttonLogin.setEnabled(true);
                    buttonLogin.setTextColor(Color.WHITE);
                } else {
                    buttonLogin.setBackgroundResource(R.drawable.bg_message);
                    buttonLogin.setEnabled(false);
                    buttonLogin.setTextColor(Color.BLACK);
                }
            }
        });

    }

    private void testLogin() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        isLogin = false;
        ID = preferences.getString("id", "");
        pass = preferences.getString("pass", "");
        isLogin = preferences.getBoolean("isLogin", false);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    listUser.add(user);

                }
                for (User user : listUser) {
//                    Log.e("TAG", "userName : " + user.getID());
//                    Log.e("TAG", "id : " + ID);
//                    Log.e("TAG", "pass : " + pass);
//                    Log.e("TAG", "isLogin : " +isLogin);
                    if ((isLogin == true) && (Integer.parseInt(ID) == (user.getID())) &&
                            (pass.equals(user.getPass()))) {
                        isOk = true;
                        Log.e("TAG", "isOk : " + isOk);
                        break;
                    }
                }
                Log.e("TAG", "isOk" + isOk);
                if (isOk == true) {
                    Intent intent = new Intent(LoginActivity.this, ActivityFriend.class);
                    intent.putExtra("id", ID);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin: {
                ID = editTextID.getText().toString().trim();
                pass = editTextPass.getText().toString().trim();
                if (ID.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, "Your information is empty", Toast.LENGTH_SHORT).show();
                } else {
                    for (User user1 : listUser) {
                        if ((user1.getID() == Integer.parseInt(ID)) &&
                                (user1.getPass().equals(pass))) {
                            isCheck = 1;
                            break;
                        }
                        if (user1.getID() == Integer.parseInt(ID) &&
                                (user1.getPass().equals(pass) == false)) {
                            isCheck = 2;
                            break;
                        }
                        if (user1.getID() != Integer.parseInt(ID)) {
                            isCheck = 3;
                        }
                    }
                    switch (isCheck) {
                        case 1: {
                            isLogin = true;
                            preferences.edit().putString("id", ID).commit();
                            preferences.edit().putString("pass", pass).commit();
                            preferences.edit().putBoolean("isLogin", isLogin).commit();

                            Intent intent = new Intent(LoginActivity.this, ActivityFriend.class);
//                            intent.putExtra("id", ID);
                            startActivity(intent);
                            overridePendingTransition(R.anim.new_animation_left, R.anim.fade_out);

                            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 2: {
                            Toast.makeText(this, "Your password is wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 3: {
                            Toast.makeText(this, "ID not available", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
//                    if(isCheck == 1){
//                        Intent intent = new Intent(LoginActivity.this, ActivityFriend.class);
//                        intent.putExtra("id", ID);
//                        startActivity(intent);
//                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show(); break;
//                    }else{
//                        Toast.makeText(this, "Infomation error", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
            break;
            case R.id.buttonRegister: {
                Intent intent = new Intent(LoginActivity.this, ActivityRegister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.fade_out);
            }
            break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            referenceTest.child("2").child("pass").setValue("");
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            referenceTest.child("2").child("pass").setValue("");
//            handler.handleMessage();
            handler.sendEmptyMessage(0);
//            handler.postDelayed(this, 2000);
        }
    };
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            referenceTest.child("2").child("pass").setValue("");
//            handler.postDelayed(this, 2000)
//
//        }
//    };


    @Override
    public void onBackPressed() {
        finish();

    }


}
