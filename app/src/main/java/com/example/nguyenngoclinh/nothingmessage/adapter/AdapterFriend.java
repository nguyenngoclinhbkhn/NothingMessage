package com.example.nguyenngoclinh.nothingmessage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyenngoclinh.nothingmessage.R;
import com.example.nguyenngoclinh.nothingmessage.model.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AdapterFriend extends RecyclerView.Adapter<AdapterFriend.ViewHolder>{
    private List<Message> messageList;
    private Context context;
    private int id;
    private int lastPosition = -1;


    public AdapterFriend(Context context, List<Message> messagesList, int id){
        this.context = context;
        this.messageList = messagesList;
        this.id = id;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Message message = messageList.get(position);
        String []array123 = message.getMessage().split("=>");
//        holder.textViewNotificationMessage.setTextColor(Color.GRAY);
        String idTest = id + "_" + "1";
        if ((array123[1]).equals(idTest)){
            holder.textViewNotificationMessage.setTextColor(Color.GRAY);
        }else {
            holder.textViewNotificationMessage.setTextColor(Color.BLACK);
        }
        holder.textViewNotificationMessage.setText(array123[0]);
        if (id == message.getId_senderSendRequestAddFriend()) {
            holder.textViewIdFriend.setText(String.valueOf(message.getId_receiverRequestAddFriend()));
//            holder.imageViewNotification.setImageBitmap(null);
//            holder.textViewNotificationMessage.setText(message.getMessage());
        }else {
            holder.textViewIdFriend.setText(String.valueOf(message.getId_senderSendRequestAddFriend()));
//            holder.textViewNotificationMessage.setText(message.getMessage());
//            holder.imageViewNotification.setImageResource(message.getImageResource());
//            holder.textViewNotificationMessage.sette
        }
//        holder.textViewTime.setText(message.getTime());
        if (message.getTime() > 0) {
            long time = message.getTime();
            Date today = new Date(time);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String time1 = timeFormat.format(today);
            holder.textViewTime.setText(time1);
//            holder.imageViewNotification.setImageResource(message.getImageResource());
        }else {
            holder.textViewTime.setText("");
        }

        setAnimation(holder.itemView);
//        if (id == message.getidre)

        holder.linearLayoutFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickedListener != null){
                    onItemClickedListener.OnItemClicked(holder.itemView, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private void setAnimation(View viewToAnimate)
    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition)
//        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewIdFriend;
        private TextView textViewNotificationMessage;
        private LinearLayout linearLayoutFriend;
        private TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewIdFriend = itemView.findViewById(R.id.textViewIdMessageFriend);
            textViewNotificationMessage = itemView.findViewById(R.id.textViewNotificationMessage);
            linearLayoutFriend = itemView.findViewById(R.id.linearLayoutFriend);
            textViewTime = itemView.findViewById(R.id.textViewTime);

        }


    }

    public interface OnItemClickedListener{
        void OnItemClicked(View view, int position);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener itemClickedListener){
        this.onItemClickedListener = itemClickedListener;
    }

}
