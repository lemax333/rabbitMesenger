package com.lemax333.rabbitmessenger.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemax333.rabbitmessenger.R;
import com.lemax333.rabbitmessenger.tools.model.Conversation;

import java.util.List;

/**
 * Created by lemax333 on 15.12.17.
 */

public class ConversationAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Conversation> conversationList;

    public ConversationAdapter(Context context, List<Conversation> conversationList) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        this.conversationList = conversationList;
    }

    @Override
    public int getCount() {
        return conversationList.size();
    }

    @Override
    public Object getItem(int i) {
        return conversationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.conversation_view, viewGroup);
        Conversation conversation = getConversation(i);
        ((TextView) view.findViewById(R.id.conversationName)).setText(conversation.getName());
        ((TextView) view.findViewById(R.id.lastMessage)).setText(conversation.getLastMessage());
        return view;
    }

    private Conversation getConversation(int position){
        return conversationList.get(position);
    }
}
