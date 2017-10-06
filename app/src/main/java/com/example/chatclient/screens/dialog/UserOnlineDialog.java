package com.example.chatclient.screens.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.chatclient.R;
import com.example.chatclient.model.OnlineList;
import com.example.chatclient.model.User;

import java.util.List;

public class UserOnlineDialog extends DialogFragment {
    private static final String ONLINE_LIST = "onlineList";

    public static UserOnlineDialog newInstance(OnlineList onlineList) {

        Bundle args = new Bundle();
        args.putParcelable(ONLINE_LIST, onlineList);

        UserOnlineDialog fragment = new UserOnlineDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_online, null);

        OnlineList onlineList = getArguments().getParcelable(ONLINE_LIST);
        List<User> userOnline = onlineList.getUserList();

        TextView txtOnline = (TextView) view.findViewById(R.id.txtOnline);
        for (int i = 0; i < userOnline.size(); i++) {
            txtOnline.append((i+1) + ". " + userOnline.get(i).getName() + "\n");
        }

        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserOnlineDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
