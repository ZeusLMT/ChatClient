package com.example.chatclient.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatclient.R;
import com.example.chatclient.event.ResponseEvent;
import com.example.chatclient.model.ChatMessage;
import com.example.chatclient.screens.login.LoginActivity;
import com.example.chatclient.service.MessReceiver;
import com.example.chatclient.util.ChatSharedPreference;
import com.example.chatclient.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.txtInput)
    EditText txtInput;

    @BindView(R.id.recyclerView)
    RecyclerView recycleView;

    private MainContract.Presenter presenter;
    private ChatSharedPreference chatSharedPreference;
    private List<ChatMessage> chatMessageList;
    private MessageAdapter messageAdapter;
    private String myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        chatSharedPreference = new ChatSharedPreference(this);
        myAccount = chatSharedPreference.getMyAccount();

        //setup chat list
        setupChatList();

        //Start mess receiver
        startMessReceiver();

        //hidekeyboard
        ViewUtil.hideKeyboardWhenTouchOutside(findViewById(R.id.messLayout), this);
    }

    private void startMessReceiver() {
        MessReceiver msgReceiver = new MessReceiver();
        Thread t = new Thread(msgReceiver);
        t.start();
    }

    private void setupChatList() {
        chatMessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(chatMessageList, this);
        recycleView.setAdapter(messageAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.btnSend)
    void sendMessage() {
        presenter.sendMessage(txtInput.getText().toString());
        txtInput.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemLogout:
                presenter.logout(myAccount);
                return true;
            case R.id.itemUserList:
                presenter.getUserList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMess(ChatMessage chatMessage) {
        chatMessageList.add(chatMessage);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void logOutSuccess(String success) {
        Toast.makeText(this, success, Toast.LENGTH_SHORT).show();

        //TODO: clear SharePref
        chatSharedPreference.clear();

        //TODO: goto login activity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void saveUserName(String userName) {
        //TODO: save username
        chatSharedPreference.saveMyAccount(userName);
    }

    @Override
    public void showUserList(String users) {
        UserListDialog userListDialog = UserListDialog.newInstance(users);
        userListDialog.show(getSupportFragmentManager(), "userListFragment");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ResponseEvent event) {
        presenter.receiveMessage(event, myAccount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
