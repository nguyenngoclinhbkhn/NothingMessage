package com.example.nguyenngoclinh.nothingmessage.uis.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ActivityRegister extends BaseActivity {
    private Button buttonRegisterAndLoginAccount;
    private EditText editTextPassRegister;
    private int ID;
    private DatabaseReference reference;
    private String pass;
    private ArrayList<Long> listID;
    private Toolbar toolbar;
    private ImageButton imageButtonBack;
    private SharedPreferences preferences;
    private String id;
    private boolean isCheck;
    private DatabaseReference referenceRegister;

    @Override
    public int injectLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        buttonRegisterAndLoginAccount = findViewById(R.id.buttonRegisterAndLoginAccount);
        editTextPassRegister = findViewById(R.id.editTextPassRegisterAccount);
        toolbar = findViewById(R.id.toolbarRegister);
        imageButtonBack = findViewById(R.id.imageButtonBackRegisterAccount);

    }

    @Override
    public void initVariable() {
        isCheck = false;
        setSupportActionBar(toolbar);
        listID = new ArrayList<>();
        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        reference = FirebaseDatabase.getInstance().getReference("users");
        referenceRegister = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listID.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    listID.add(user.getID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonRegisterAndLoginAccount.setEnabled(false);
        buttonRegisterAndLoginAccount.setBackgroundResource(R.drawable.bg_message);
        buttonRegisterAndLoginAccount.setTextColor(Color.BLACK);
        editTextPassRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String a = editTextPassRegister.getText().toString().trim();
                if (a.length() > 0) {
                    buttonRegisterAndLoginAccount.setEnabled(true);
                    buttonRegisterAndLoginAccount.setTextColor(Color.WHITE);
                    buttonRegisterAndLoginAccount.setBackgroundResource(R.drawable.bg_image_view_click);
                } else {
                    buttonRegisterAndLoginAccount.setEnabled(false);
                    buttonRegisterAndLoginAccount.setTextColor(Color.BLACK);
                    buttonRegisterAndLoginAccount.setBackgroundResource(R.drawable.bg_message);
                }
            }
        });

        buttonRegisterAndLoginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = editTextPassRegister.getText().toString().trim();
                if ((!TextUtils.isEmpty(pass))) {
                    Long id = getIdMax(listID);
                    reference.child(String.valueOf(id + 1)).setValue(new User(id + 1, pass));
                    preferences.edit().putString("id", String.valueOf(id + 1)).commit();
                    preferences.edit().putBoolean("isLogin", true).commit();
                    Toast.makeText(ActivityRegister.this, "Id" + id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityRegister.this, ActivityFriend.class);
                    startActivity(intent);
                    editTextPassRegister.setText("");
                    Toast.makeText(ActivityRegister.this, "Register success", Toast.LENGTH_SHORT).show();
//                    reference.runTransaction(new Transaction.Handler() {
//                        @NonNull
//                        @Override
//                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
//                            if (mutableData.getValue() == null){
//                                mutableData.setValue(1);
//                                User user1 = new User(1, pass);
//                                referenceRegister.child("1").setValue(user1);
//                                Log.e("TAG", "hi da chinh sua" + 1);
//                                preferences.edit().putString("id", String.valueOf(user1.getID())).commit();
//                                preferences.edit().putBoolean("isLogin", true).commit();
//                                Intent intent = new Intent(ActivityRegister.this, ActivityFriend.class);
//                                startActivity(intent);
//                            }else {
//                                Long a = (mutableData.getValue(Long.class) + 1);
//                                mutableData.setValue(a);
//                                User user1 = new User(a, pass);
//                                referenceRegister.child(String.valueOf(a)).setValue(user1);
//                                preferences.edit().putString("id", String.valueOf(a)).commit();
//                                preferences.edit().putString("pass", String.valueOf(user1.getPass())).commit();
//                                preferences.edit().putBoolean("isLogin", true);
//
//                                Log.e("TAG", " hi " + a);
//                            }
//                            return Transaction.success(mutableData);
//
////                            preferences.edit().putString("pass", pass).commit();
////                            preferences.edit().putBoolean("isLogin", true).commit();
//
//                        }
//
//                        @Override
//                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
//                        }
//                    });
//
                }else{
                    Toast.makeText(ActivityRegister.this, "Empty password", Toast.LENGTH_SHORT).show();
                }


            }
        });


        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegister.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private long getIdMax(ArrayList<Long> a) {
        long max = 1;
        for (int i = 0; i < a.size(); i++) {
            if (max <= a.get(i)) {
                max = a.get(i);
            }
        }
        return max;
    }
}
