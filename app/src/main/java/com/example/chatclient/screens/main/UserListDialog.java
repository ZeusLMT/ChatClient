package com.example.chatclient.screens.main;

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

public class UserListDialog extends DialogFragment {
    private static final String USER_LIST = "userList";

    public static UserListDialog newInstance(String userList) {

        Bundle args = new Bundle();
        args.putString(USER_LIST, userList);

        UserListDialog fragment = new UserListDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_userlist, null);
        // Inflate and set the layout for the dialog
        String userList = getArguments().getString(USER_LIST);
        TextView txtUserList = (TextView) view.findViewById(R.id.txtUserList);
        txtUserList.setText(userList);

        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                .setTitle("Active users:")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserListDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
