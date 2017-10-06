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

import com.example.chatclient.App;
import com.example.chatclient.R;
import com.example.chatclient.event.ServerEvent;
import com.example.chatclient.model.Message;
import com.example.chatclient.model.OnlineList;
import com.example.chatclient.screens.adapter.MessageAdapter;
import com.example.chatclient.screens.dialog.UserOnlineDialog;
import com.example.chatclient.screens.login.LoginActivity;
import com.example.chatclient.server.ReceiverThread;
import com.example.chatclient.util.AppPref;
import com.example.chatclient.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.chatclient.R.id.recyclerView;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.txtInput)
    EditText txtInput;

    @BindView(recyclerView)
    RecyclerView recycleView;

    private MainContract.Presenter presenter;
    private AppPref chatSharedPreference;
    private List<Message> chatMessageList;
    private MessageAdapter messageAdapter;
    private String myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        chatSharedPreference = new AppPref(this);
        myAccount = chatSharedPreference.getMyAccount();

        //setup chat list
        setupChatList();

        //Start mess receiver
        startMessReceiver();

        //hidekeyboard
        ViewUtil.hideKeyboardWhenTouchOutside(findViewById(R.id.messLayout), this);

        //load history
        presenter.loadHistory();
    }

    private void startMessReceiver() {
        ReceiverThread msgReceiver = new ReceiverThread();
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
    public void showMess(Message chatMessage) {
        chatMessageList.add(chatMessage);
        recycleView.scrollToPosition(chatMessageList.size() - 1);
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

        //TODO: Close socket
        try {
            App.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUserList(OnlineList onlineList) {
        UserOnlineDialog userListDialog = UserOnlineDialog.newInstance(onlineList);
        userListDialog.show(getSupportFragmentManager(), "userListFragment");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ServerEvent event) {
        presenter.receiveMessage(event, myAccount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
