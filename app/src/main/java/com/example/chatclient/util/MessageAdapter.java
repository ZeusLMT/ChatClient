package com.example.chatclient.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatclient.R;
import com.example.chatclient.model.ChatMessage;

import java.util.List;

/**
 * Created by Zeus on 3/10/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<ChatMessage> chatMessageList;
    private static final int TYPE_MY_MESSAGE = 1;
    private static final int TYPE_OTHER_MESSAGE = 2;

    public MessageAdapter(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case TYPE_MY_MESSAGE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_outgoing_message, parent, false);
                break;
            case TYPE_OTHER_MESSAGE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_incoming_message, parent, false);
                break;
        }

        MessageAdapter.ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        boolean myMessage = chatMessage.isMyMessage();
        if (myMessage) {
            return TYPE_MY_MESSAGE;
        } else {
            return TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        holder.getTxtUserName().setText(chatMessage.getUser().getName());
        holder.getTxtTextMessage().setText(chatMessage.getMessage());
        holder.getTxtTime().setText(chatMessage.getTimestamp() + "");
    }


    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUserName;
        private TextView txtTextMessage;
        private TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            txtTextMessage = (TextView) itemView.findViewById(R.id.txtTextMessage);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
        }

        public TextView getTxtUserName() {
            return txtUserName;
        }

        public void setTxtUserName(TextView txtUserName) {
            this.txtUserName = txtUserName;
        }

        public TextView getTxtTextMessage() {
            return txtTextMessage;
        }

        public void setTxtTextMessage(TextView txtTextMessage) {
            this.txtTextMessage = txtTextMessage;
        }

        public TextView getTxtTime() {
            return txtTime;
        }

        public void setTxtTime(TextView txtTime) {
            this.txtTime = txtTime;
        }
    }
}
