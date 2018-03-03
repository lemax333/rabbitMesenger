package com.lemax333.rabbitmessenger.ui.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lemax333.rabbitmessenger.R;
import com.lemax333.rabbitmessenger.tools.model.Message;

/**
 * Created by oleksii on 03/03/2018.
 */

public class SentMessageHolder extends RecyclerView.ViewHolder {

    TextView messageText;

    public SentMessageHolder(View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.text_message_body);
    }

    public void bind(Message message){
        messageText.setText(message.getText());
    }
}
