package com.lemax333.rabbitmessenger.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lemax333.rabbitmessenger.R;
import com.lemax333.rabbitmessenger.tools.service.ContactsService;
import com.lemax333.rabbitmessenger.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends BaseNavigationDrawerActivity {

    private ListView contactsList;
    private List<String> contacts;
    private ArrayAdapter<String> arrayAdapter;
    private ContactsReceiver receiver;
    private final String USERNAME = "username";
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userName = intent.getStringExtra(USERNAME);
        saveUserName(userName);
        inflateWithLayout(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contactsList = findViewById(R.id.contactsList);
        contacts = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.contacts_item, R.id.contact, contacts);
        contactsList.setAdapter(arrayAdapter);
        contactsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView userNameTextView = (TextView) view;
                String username = userNameTextView.getText().toString();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.receiver = new ContactsReceiver(new Handler());
        getContacts();
    }

    private void getContacts() {
        Intent intent = new Intent(this, ContactsService.class);
        intent.putExtra(Constants.DATA_RECEIVER, receiver);
        intent.putExtra(Constants.USERNAME, userName);
        startService(intent);
    }

    private void saveUserName(String userName) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, userName);
        editor.commit();
    }

    public void inflateWithLayout(int layoutResID) {
        if (contentActivity != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, contentActivity, false);
            contentActivity.addView(stubView, lp);
        }
    }

    private class ContactsReceiver extends ResultReceiver {

        private static final String CONTACTS_LIST = "contacts_list";

        public ContactsReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            contacts.clear();
            contacts.addAll(resultData.getStringArrayList(CONTACTS_LIST));
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
