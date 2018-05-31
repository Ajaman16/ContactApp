package com.exmaple.contactapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exmaple.contactapp.models.History;
import com.exmaple.contactapp.models.Users;
import com.exmaple.contactapp.network.ApiClient;
import com.exmaple.contactapp.network.ApiInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.message)
    EditText message;
    @BindView(R.id.send)
    Button send;

    Context context;

    Users user;
    Realm realm;
    @BindView(R.id.status)
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        context = this;
        realm = Realm.getDefaultInstance();

        if (getIntent().getParcelableExtra("data") != null) {
            user = getIntent().getParcelableExtra("data");

            Random rand = new Random();
            final int num = rand.nextInt(888888) + 111111;

            message.setText("Hi, Your OTP is: " + num);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sendSMS(num);


                }
            });
        }
    }

    private void sendSMS(final int num) {

        try {
            Map<String, String> map = new HashMap<>();
            map.put("phone", "+91" + user.getNumber());
            map.put("message", "Hi, your OTP is " + num);
            map.put("key", "d5678252ada031e427353ca6f6c16992b5a0465abnevIsTGeBxaUIDEwFasT9DRi");


            status.setVisibility(View.GONE);

            ApiInterface apiInterface = ApiClient.getUnauthorizedApiInterface();
            Call<HashMap<String, Object>> call = apiInterface.sendOTP(map);
            call.enqueue(new Callback<HashMap<String, Object>>() {
                @Override
                public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {

                    if (response.isSuccessful()) {
                        //Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();

                        status.setText(response.body().toString());
                        status.setVisibility(View.VISIBLE);

                        Log.d("DDDD: ", response.body().toString());

                        History history = new History();
                        history.setFirstName(user.getFirstName());
                        history.setLastName(user.getLastName());
                        history.setNumber(user.getNumber());
                        history.setOtp("" + num);
                        history.setTimestamp(System.currentTimeMillis());


                        realm.beginTransaction();
                        realm.copyToRealm(history);
                        realm.commitTransaction();
                    } else {
                        Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
