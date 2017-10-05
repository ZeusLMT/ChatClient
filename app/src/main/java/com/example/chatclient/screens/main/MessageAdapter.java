package com.example.chatclient.screens.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatclient.R;
import com.example.chatclient.model.ChatMessage;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<ChatMessage> chatMessageList;
    private Context context;
    private static final int TYPE_MY_MESSAGE = 1;
    private static final int TYPE_OTHER_MESSAGE = 2;

    public MessageAdapter(List<ChatMessage> chatMessageList, Context context) {
        this.chatMessageList = chatMessageList;
        this.context = context;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        Log.i("abc", "viewType: " + viewType);
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
        holder.getTxtMessage().setText(chatMessage.getMessage());
        holder.getTxtTime().setText(chatMessage.getTimestamp() + "");
    }


    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUserName;
        private TextView txtMessage;
        private TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
        }

        public TextView getTxtUserName() {
            return txtUserName;
        }

        public TextView getTxtMessage() {
            return txtMessage;
        }

        public TextView getTxtTime() {
            return txtTime;
        }

    }
}
