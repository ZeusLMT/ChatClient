package com.example.chatclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnSend)
    Button btnSend;

    @BindView(R.id.txtInput)
    EditText txtInput;

    @BindView(R.id.txtMessage)
    TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        txtMessage.setMovementMethod(new ScrollingMovementMethod());
    }

    @OnClick(R.id.btnSend)
    void sendMessage() {
        String input = txtInput.getText().toString();
        if(!TextUtils.isEmpty(input)){
            txtMessage.append(input + "\n");
        }
        txtInput.setText("");

    }

    @OnClick(R.id.txtMessage)
    void hideKeyboard(){
        ViewUtil.hideSoftKeyBoard(this);
    }
}
