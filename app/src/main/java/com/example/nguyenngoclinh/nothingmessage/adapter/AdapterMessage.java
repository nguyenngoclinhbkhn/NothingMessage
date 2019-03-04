package com.example.nguyenngoclinh.nothingmessage.adapter;

import android.content.Context;
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

public class AdapterMessage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private String idSender;
    private List<Message> messageList;
    private Context context;
    private boolean isOk = false;
    private boolean isOkSend = false;
    private int seen = R.drawable.ic_action_tick;

    public AdapterMessage(Context context, List<Message> messagesList, String idSender) {
        this.context = context;
        this.messageList = messagesList;
        this.idSender = idSender;
    }


//    Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (String.valueOf(message.getId_senderSendRequestAddFriend()).equals(idSender)) {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_MESSAGE_SENT: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_send, parent, false);
                return new SentMessage(view);
            }
            case VIEW_TYPE_MESSAGE_RECEIVED: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_receiver, parent, false);
                return new ReceiverMessage(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT: {
                ((SentMessage) holder).bind(message);
//                if (message.getStatusMessage() == 1){
//                    remove(position);
//                }
            }
            break;
            case VIEW_TYPE_MESSAGE_RECEIVED: {
                ((ReceiverMessage) holder).bind(message);
//                if (message.getStatusMessage() == 1){
//                    remove(position);
//                }
            }
            break;
        }

    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ReceiverMessage extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewIdMessageRecevier;
        TextView textViewContentMessageReceiver;
        TextView textViewSendMessage;
        ImageView imageViewReceiver1;
        ImageView imageViewReceiver2;
        final Animation animation12 = AnimationUtils.loadAnimation(context, R.anim.new_animation_left);

        public ReceiverMessage(View itemView) {
            super(itemView);
            textViewIdMessageRecevier = itemView.findViewById(R.id.textViewIdAccountMessageReceiver);
            textViewContentMessageReceiver = itemView.findViewById(R.id.textViewContentMessageReceiver);
            textViewSendMessage = itemView.findViewById(R.id.textViewSendMessage);
            imageViewReceiver1 = itemView.findViewById(R.id.imageViewReceiver1);
            imageViewReceiver2 = itemView.findViewById(R.id.imageViewReceiver2);
//            itemView.startAnimation(animation12);
            itemView.setOnClickListener(this);

        }

        void bind(Message message) {
            final Animation animation12 = AnimationUtils.loadAnimation(context, R.anim.old_animation);
            long time = message.getTime();
            Date today = new Date(time);
            SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
            String time1 = timeFormat.format(today);
            if (message.getMessage().length() > 0) {
                String[] array = message.getMessage().split("=>");
                textViewContentMessageReceiver.setText(array[0]);
            }
            textViewIdMessageRecevier.setText(String.valueOf(message.getId_senderSendRequestAddFriend()));
            textViewSendMessage.setText(time1);
            textViewSendMessage.setVisibility(View.GONE);
            imageViewReceiver1.setImageResource(seen);
            if ((message.getStatusMessage()) == 1) {
                imageViewReceiver2.setImageResource(seen);
            } else {
                imageViewReceiver2.setImageBitmap(null);
            }

//            textViewContentMessageReceiver.setAnimation(animation12);
        }

        @Override
        public void onClick(View v) {
            final int position = getLayoutPosition();
            Message message = messageList.get(position);
            String a = message.getMessage();
            final Animation animation = AnimationUtils.loadAnimation(context, R.anim.old_animation);
            final Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            animation1.setDuration(900);
            animation.setDuration(300);
            isOkSend = !isOkSend;
            if (isOkSend == true) {
                textViewSendMessage.startAnimation(animation);
                textViewSendMessage.setVisibility(View.VISIBLE);
//                relativeLayout2.startAnimation(animation);
//                relativeLayout2.setVisibility(View.VISIBLE);

//                isOk = false;
            }
            if (isOkSend == false) {
                textViewSendMessage.startAnimation(animation1);
                textViewSendMessage.setVisibility(View.GONE);
//                relativeLayout2.setVisibility(View.VISIBLE);

            }
        }
    }

    class SentMessage extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewIdMessageSend;
        TextView textViewContentMessageSend;
        TextView textViewTime;
        LinearLayout messageSend;
        ImageView imageViewSend1;
        ImageView imageViewSend2;

        final Animation animation12 = AnimationUtils.loadAnimation(context, R.anim.new_animation_left);

        public SentMessage(View itemView) {
            super(itemView);
            textViewIdMessageSend = itemView.findViewById(R.id.textViewIdAccountMessageSend);
            textViewContentMessageSend = itemView.findViewById(R.id.textViewContentMessageSend);
            textViewTime = itemView.findViewById(R.id.textViewTimeMessage);
            messageSend = itemView.findViewById(R.id.messageSend);
            imageViewSend1 = itemView.findViewById(R.id.imageViewSend1);
            imageViewSend2 = itemView.findViewById(R.id.imageViewSend2);

//            itemView.startAnimation(animation12);
//            itemView.setOnClickListener(this);
        }

        void bind(Message message) {
            final Animation animation12 = AnimationUtils.loadAnimation(context, R.anim.old_animation);
            long time = message.getTime();
            Date today = new Date(time);
            SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
            String time1 = timeFormat.format(today);
            if (message.getMessage().length() > 0) {
                String[] array = message.getMessage().split("=>");
                textViewContentMessageSend.setText(array[0]);
            }
            textViewIdMessageSend.setText(String.valueOf(message.getId_senderSendRequestAddFriend()));
            textViewTime.setText(time1);
            textViewTime.setVisibility(View.GONE);
//            relativeLayout.setVisibility(View.GONE);
            imageViewSend1.setImageResource(seen);
            if ((message.getStatusMessage()) == 1) {
                imageViewSend2.setImageResource(seen);
            } else {
                imageViewSend2.setImageBitmap(null);
            }


//            textViewContentMessageSend.setAnimation(animation12);
            itemView.setOnClickListener(this);
//            holder.linearLayoutFriend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(onItemClickedListener != null){
//                        onItemClickedListener.OnItemClicked(holder.itemView, position);
//                    }
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            final Animation animation = AnimationUtils.loadAnimation(context, R.anim.old_animation);
            final Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            animation1.setDuration(380);
            animation.setDuration(300);
            int position = getLayoutPosition();
            Message message = messageList.get(position);
            isOk = !isOk;
            if (isOk == true) {
                textViewTime.startAnimation(animation);
                textViewTime.setVisibility(View.VISIBLE);
//                relativeLayout.startAnimation(animation);
//                relativeLayout.setVisibility(View.VISIBLE);
//                isOk = false;
            }
            if (isOk == false) {
                textViewTime.startAnimation(animation1);
                textViewTime.setVisibility(View.GONE);
//                relativeLayout.setVisibility(View.GONE);
            }
//            isOk = !isOk;
//            Toast.makeText(context, "Hello " + message.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(int position) {
        messageList.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickedListener {
        void OnItemClicked(View view, int position);
    }

    private AdapterFriend.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(AdapterFriend.OnItemClickedListener itemClickedListener) {
        this.onItemClickedListener = itemClickedListener;
    }

}
