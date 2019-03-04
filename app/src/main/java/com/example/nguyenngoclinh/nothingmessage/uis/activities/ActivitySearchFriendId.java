package com.example.nguyenngoclinh.nothingmessage.uis.activities;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.nothingmessage.R;
import com.example.nguyenngoclinh.nothingmessage.model.Message;
import com.example.nguyenngoclinh.nothingmessage.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.IDN;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivitySearchFriendId extends BaseActivity{
    private TextView textViewIdAccountFriendSearch;
    private TextView textViewNotificationAccountFriendSearched;
    private ImageView imageViewNotificationSearched;
    private String idSearch;
    private TextView textViewResultFinded;
    private LinearLayout linearLayout;
    private DatabaseReference reference;
    private boolean isCheck;
    private String idSenderAndReceiver;
    private String statusFriend;
    private String idRecevier;
    private String notification;
    private ImageView imageViewActionBack;
    private TextView textViewTime;
    private String time123;
    @Override
    public int injectLayout() {
        return R.layout.activity_search_friend_by_id;
    }

    @Override
    public void initView() {
        textViewIdAccountFriendSearch = findViewById(R.id.textViewIdMessageFriendSearched);
        textViewNotificationAccountFriendSearched = findViewById(R.id.textViewNotificationMessageSearched);
        linearLayout = findViewById(R.id.linearLayoutFriendSearch);
        textViewResultFinded = findViewById(R.id.textViewResultFinded);
        imageViewActionBack = findViewById(R.id.imageViewActionBackResultIdFriend);
        textViewTime = findViewById(R.id.textViewTimeNotification);

    }

    @Override
    public void initVariable() {
        textViewResultFinded.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        isCheck = false;
        final Intent intent = getIntent();
        idSearch = intent.getStringExtra("idSearch");
        reference = FirebaseDatabase.getInstance().getReference("friends");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message message = snapshot.getValue(Message.class);
                    Log.e("TAG", "id " +message.getId_senderSendRequestAddFriend() + " : " + message.getId_receiverRequestAddFriend());
                    if ((String.valueOf(message.getId_senderSendRequestAddFriend()).equals(idSearch) ||
                            (String.valueOf(message.getId_receiverRequestAddFriend()).equals(idSearch))) &&
                            ((String.valueOf(message.getId_senderSendRequestAddFriend()).equals(ActivityFriend.ID))||
                                    (String.valueOf(message.getId_receiverRequestAddFriend()).equals(ActivityFriend.ID)))){
                        idSenderAndReceiver = message.getIdSenderAndReceiver();
                        idRecevier = String.valueOf(message.getId_receiverRequestAddFriend());
                        statusFriend = String.valueOf(message.getStatusFriend());
                        long time = message.getTime();
                        Date today = new Date(time);
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        String time1 = timeFormat.format(today);

                        time123 = time1;
                        if (message.getMessage().length() > 0){
                            String[] array = message.getMessage().split("=>");
                            notification = array[0];
                        }

                        Log.e("TAG", "isCheck :" + isCheck);
                        isCheck = true; break;

                    }else{
                        isCheck = false;
                    }

                }
                if (isCheck == true){
                    linearLayout.setVisibility(View.VISIBLE);
                    textViewIdAccountFriendSearch.setText(idSearch);
                    Log.e("TAG", "idSenderAndRecevier " + idSenderAndReceiver);
                    textViewNotificationAccountFriendSearched.setText(notification);
                    textViewTime.setText(time123);
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1 = new Intent(ActivitySearchFriendId.this, ActivityMessage.class);
                            intent1.putExtra("idSenderAndRecevier", idSenderAndReceiver);
                            startActivity(intent1);
                        }
                    });
                } else{
                    linearLayout.setVisibility(View.GONE);
                    textViewResultFinded.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageViewActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ActivitySearchFriendId.this, ActivityFriend.class);
                intent1.putExtra("id", ActivityFriend.ID);
                startActivity(intent1);
            }
        });

    }
}
