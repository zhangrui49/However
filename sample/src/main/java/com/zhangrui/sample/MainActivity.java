package com.zhangrui.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhangrui.however.AsyncCallback;
import com.zhangrui.however.Error;
import com.zhangrui.however.However;
import com.zhangrui.however.InitConfig;
import com.zhangrui.however.RequestBody;
import com.zhangrui.however.ScheduleRequestBody;
import com.zhangrui.however.SyncCallback;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String requestStr="test";
        However.getInstance().init(new InitConfig.Builder().CorePoolSize(10).Ip("127.0.0.1").Port(8080).build());
        However.getInstance().newRequest(new RequestBody(requestStr.getBytes()), new SyncCallback() {
            @Override
            public void onSuccess(byte[] t) {

            }

            @Override
            public void onFailure(Error error) {

            }
        });

        However.getInstance().newRequest(new ScheduleRequestBody(requestStr.getBytes(), 0, 5000, TimeUnit.MILLISECONDS), new AsyncCallback() {
            @Override
            public void onSuccess(byte[] t) {

            }

            @Override
            public void onFailure(Error error) {

            }
        });
    }
}
