package com.example.chatclient.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatclient.R;
import com.example.chatclient.event.ServerEvent;
import com.example.chatclient.screens.main.MainActivity;
import com.example.chatclient.server.ReceiverThread;
import com.example.chatclient.util.AppPref;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginActivityContract.View {

    @BindView(R.id.txtUserName)
    EditText txtUserName;

    private LoginActivityContract.Presenter presenter;
    private AppPref chatSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
        chatSharedPreference = new AppPref(this);

        //Start mess receiver
        startMessReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register
        EventBus.getDefault().register(this);
    }

    private void startMessReceiver() {
        ReceiverThread msgReceiver = new ReceiverThread();
        Thread t = new Thread(msgReceiver);
        t.start();
    }

    @OnClick(R.id.btnLogin)
    void login() {
        presenter.performLogin(txtUserName.getText().toString());
    }

    @Override
    public void showLoginSuccess(String success, String userName) {
        Toast.makeText(this, success, Toast.LENGTH_SHORT).show();

        //TODO: save username
        chatSharedPreference.saveMyAccount(userName);

        //TODO: go to main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ServerEvent event) {
        presenter.validLogin(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
