package com.lemax333.rabbitmessenger.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lemax333.rabbitmessenger.R;
import com.lemax333.rabbitmessenger.tools.model.Message;
import com.lemax333.rabbitmessenger.tools.service.AmqpListenerService;
import com.lemax333.rabbitmessenger.tools.service.AmqpSenderService;
import com.lemax333.rabbitmessenger.ui.adapters.MessageListAdapter;
import com.lemax333.rabbitmessenger.utils.Constants;

import java.util.List;

/**
 * Created by oleksii on 03/03/2018.
 */

public class ConversationActivity extends AppCompatActivity {

    public static final String USERNAME = "username";
    public static final String EXCHANGE = "exchange";
    public static final String CONVERSATION_NAME = "conversationName";

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private EditText editText;
    private Button button;
    private List<Message> messageList;
    private Gson gson;
    private MessageReciver messageReciver;
    private String username;
    private String exchange;
    private String conversationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        gson = new Gson();

        username = getIntent().getStringExtra(USERNAME);
        exchange = getIntent().getStringExtra(EXCHANGE).replaceAll("\\s+","");
        conversationName = getIntent().getStringExtra(CONVERSATION_NAME);

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        editText = (EditText) findViewById(R.id.edittext_chatbox);
        button = (Button) findViewById(R.id.button_chatbox_send);
        mMessageAdapter = new MessageListAdapter(this, messageList, "testUser1");
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(new Message(username, editText.getText().toString(), conversationName));
            }
        });
        this.messageReciver = new MessageReciver(new Handler());

        declareMessageListener();
    }

    private void declareMessageListener() {
        Intent intent = new Intent(this, AmqpListenerService.class);
        intent.putExtra(AmqpListenerService.USERANAME, username);
        intent.putExtra(AmqpListenerService.EXCHANGE, exchange);
        intent.putExtra(Constants.DATA_RECEIVER, this.messageReciver);
        startService(intent);
    }

    private void sendMessage(Message message){
        Intent intent = new Intent(this, AmqpSenderService.class);
        intent.putExtra(AmqpSenderService.MESSAGE, gson.toJson(message));
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.messageReciver = new MessageReciver(new Handler());
    }

    private class MessageReciver extends ResultReceiver {

        public MessageReciver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            String message = resultData.getString(AmqpListenerService.MESSAGE);
            Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
