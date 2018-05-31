package com.exmaple.contactapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exmaple.contactapp.models.Users;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactInfo extends AppCompatActivity {

    Users user;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_num)
    TextView userNum;
    @BindView(R.id.send)
    Button send;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        ButterKnife.bind(this);

        context = this;

        if (getIntent().getParcelableExtra("data") != null) {
            user = getIntent().getParcelableExtra("data");

            userName.setText("Name: "+user.getFirstName()+" "+user.getLastName());
            userNum.setText("Mobile: "+user.getNumber());

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MessageActivity.class);
                    i.putExtra("data", user);
                    startActivity(i);
                }
            });

        }
    }
}
