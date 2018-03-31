package com.lemax333.rabbitmessenger.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lemax333.rabbitmessenger.R;
import com.lemax333.rabbitmessenger.tools.model.Message;
import com.lemax333.rabbitmessenger.ui.adapters.holders.MessageHolder;
import com.lemax333.rabbitmessenger.ui.adapters.holders.ReceivedMessageHolder;
import com.lemax333.rabbitmessenger.ui.adapters.holders.SentMessageHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksii on 03/03/2018.
 */

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<Message> messageList = new ArrayList<>();
    private String userName = "testUser1";

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message.getAuthor().equals(userName)){
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    public MessageListAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
