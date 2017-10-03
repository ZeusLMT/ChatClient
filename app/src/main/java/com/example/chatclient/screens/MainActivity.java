package com.example.chatclient.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatclient.DialogFragment;
import com.example.chatclient.MessageEvent;
import com.example.chatclient.R;
import com.example.chatclient.model.ChatMessage;
import com.example.chatclient.service.ChatReceiveService;
import com.example.chatclient.util.ChatSharedPreference;
import com.example.chatclient.util.MessageAdapter;
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
    @BindView(R.id.btnSend)
    Button btnSend;

    @BindView(R.id.txtInput)
    EditText txtInput;

    @BindView(R.id.recyclerView)
    RecyclerView recycleView;

    private MainContract.Presenter presenter;
    private ChatSharedPreference chatSharedPreference;
    private List<ChatMessage> chatMessageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        chatSharedPreference = new ChatSharedPreference(this);
        chatSharedPreference.saveMyAccount("Tuan");

        //setup chat list
        chatMessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(chatMessageList);
        recycleView.setAdapter(messageAdapter);

        //Start service
        Intent serviceIntent = new Intent(this, ChatReceiveService.class);
        startService(serviceIntent);
        Log.i("abc", "Service start: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.btnSend)
    void sendMessage() {
        //
    }

    @OnClick(R.id.messLayout)
    void hideKeyboard(){
        ViewUtil.hideSoftKeyBoard(this);
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
            case R.id.itemChangeUsername:
                DialogFragment newFragment = new DialogFragment();
                newFragment.show(getSupportFragmentManager(), "dialogfragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectSuccess(ChatMessage chatMessage) {
        chatMessageList.add(chatMessage);
        messageAdapter.notifyDataSetChanged();
        Log.i("abc", "onConnectSuccess: " + chatMessage.getMessage());
    }

    @Override
    public void onConnectFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        Log.i("abc", "onConnectFailure: " + error);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String myAccount = chatSharedPreference.getMyAccount();
        presenter.receiveMessage(event, myAccount);
        Log.i("abc", "onMessageEvent: " + event.getServerMessage());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
