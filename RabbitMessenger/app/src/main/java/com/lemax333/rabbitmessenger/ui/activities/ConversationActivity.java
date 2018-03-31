package com.lemax333.rabbitmessenger.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by oleksii on 03/03/2018.
 */

public class ConversationActivity extends AppCompatActivity {

    public static final String USERNAME = "username";
    public static final String EXCHANGE = "exchange";
    public static final String CONVERSATION_NAME = "conversationName";
    public static final String MESSAGE = "message";

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

    private Thread subscribeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gson = new Gson();

        username = getIntent().getStringExtra(USERNAME);
        exchange = getIntent().getStringExtra(EXCHANGE).replaceAll("\\s+","");
        conversationName = getIntent().getStringExtra(CONVERSATION_NAME);

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        editText = (EditText) findViewById(R.id.edittext_chatbox);
        button = (Button) findViewById(R.id.button_chatbox_send);

        messageList = new ArrayList<>();
        messageList.add(new Message("testUser1", "hello", "conv"));
        mMessageAdapter = new MessageListAdapter(messageList);

        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(new Message(username, editText.getText().toString(), conversationName));
            }
        });
        this.messageReciver = new MessageReciver(new Handler());

        subscribe(new MessageReciver(new Handler()));
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
        //this.messageReciver = new MessageReciver(new Handler());
    }

    @Override
    protected void onDestroy() {
        this.subscribeThread.interrupt();
        super.onDestroy();
    }

    private void subscribe(final ResultReceiver receiver){
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionFactory connectionFactory;
                Connection connection = null;
                Channel channel = null;
                QueueingConsumer consumer;
                QueueingConsumer.Delivery delivery;
                while (true) {
                    try {
                        connectionFactory = new ConnectionFactory();
                        connectionFactory.setHost(com.lemax333.rabbitmessenger.tools.common.Constants.AMQP_HOST);
                        connectionFactory.setUsername(com.lemax333.rabbitmessenger.tools.common.Constants.AMQP_USER);
                        connectionFactory.setPassword(com.lemax333.rabbitmessenger.tools.common.Constants.AMQP_PASSWORE);
                        connection = connectionFactory.newConnection();
                        channel = connection.createChannel();
                        channel.confirmSelect();
                        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(username + ".messages", false, false, false, null);
                        channel.queueBind(username + ".messages", exchange, "");
                        consumer = new QueueingConsumer(channel);
                        channel.basicConsume(declareOk.getQueue(), true, consumer);
                        while (true) {
                            delivery = consumer.nextDelivery();
                            String message = new String(delivery.getBody());
                            Bundle bundle = new Bundle();
                            bundle.putString(MESSAGE, message);
                            receiver.send(com.lemax333.rabbitmessenger.utils.Constants.STATUS_OK, bundle);
                        }
                    } catch (IOException e) {
                        break;
                    } catch (TimeoutException e) {
                        break;
                    } catch (InterruptedException e) {
                        try {
                            connection.close();
                            channel.abort();
                            consumer = null;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    }
                }
            }
        });
        subscribeThread.start();
    }



    private class MessageReciver extends ResultReceiver {

        public MessageReciver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            String message = resultData.getString(AmqpListenerService.MESSAGE);
            messageList.add(gson.fromJson(message, Message.class));
            mMessageAdapter.notifyDataSetChanged();
        }
    }
}
