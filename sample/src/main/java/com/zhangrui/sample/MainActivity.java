package com.zhangrui.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangrui.however.AsyncCallback;
import com.zhangrui.however.Error;
import com.zhangrui.however.However;
import com.zhangrui.however.InitConfig;
import com.zhangrui.however.RequestBody;
import com.zhangrui.however.ScheduleRequestBody;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ip)
    EditText mIp;
    @Bind(R.id.port)
    EditText mPort;
    @Bind(R.id.content)
    EditText mContent;
    @Bind(R.id.response)
    TextView mResponse;
    @Bind(R.id.request)
    Button mRequest;
    @Bind(R.id.clear)
    Button mClear;
    @Bind(R.id.period)
    EditText mPeriod;
    @Bind(R.id.isLoop)
    CheckBox mIsLoop;
    @Bind(R.id.layout_period)
    LinearLayout mLayoutPeriod;
    @Bind(R.id.set_period)
    Button mSetPeriod;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.stop)
    Button mStop;

    private String requestStr;
    private ScheduleRequestBody mScheduleRequestBody;
    private RequestBody mRequestBody;
    private boolean needInit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        String requestStr="test";
//        However.getInstance().init(new InitConfig.Builder().CorePoolSize(10).Ip("127.0.0.1").Port(8080).build());
//        However.getInstance().newRequest(new RequestBody(requestStr.getBytes()), new SyncCallback() {
//            @Override
//            public void onSuccess(byte[] t) {
//
//            }
//
//            @Override
//            public void onFailure(Error error) {
//
//            }
//        });
//
//        However.getInstance().newRequest(new ScheduleRequestBody(requestStr.getBytes(), 0, 5000, TimeUnit.MILLISECONDS), new AsyncCallback() {
//            @Override
//            public void onSuccess(byte[] t) {
//
//            }
//
//            @Override
//            public void onFailure(Error error) {
//
//            }
//        });
        mIsLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mLayoutPeriod.setVisibility(View.VISIBLE);
                } else {
                    mLayoutPeriod.setVisibility(View.INVISIBLE);
                }
                However.getInstance().init(new InitConfig.Builder().CorePoolSize(5).Ip(mIp.getText().toString()).Port(Integer.parseInt(mPort.getText().toString())).build());
            }
        });
        mPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                needInit = true;

            }
        });
        mIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                needInit = true;
                Log.e("s", s.toString());
                However.getInstance().init(new InitConfig.Builder().CorePoolSize(5).Ip(mIp.getText().toString()).Port(Integer.parseInt(mPort.getText().toString())).build());
            }
        });
        mPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                needInit = true;
            }
        });
    }

    @OnClick({R.id.request, R.id.clear, R.id.set_period, R.id.stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request:

                if (TextUtils.isEmpty(mIp.getText().toString())) {
                    Toast.makeText(this, "请输入IP", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mPort.getText().toString())) {
                    Toast.makeText(this, "请输入端口号", Toast.LENGTH_SHORT).show();
                    return;
                }

                requestStr = mContent.getText().toString();
                if (needInit) {
                    However.getInstance().init(new InitConfig.Builder().CorePoolSize(5).Ip(mIp.getText().toString()).Port(Integer.parseInt(mPort.getText().toString())).build());
                    needInit = false;
                }
                if (mIsLoop.isChecked()) {
                    if (mScheduleRequestBody == null) {
                        mScheduleRequestBody = new ScheduleRequestBody(requestStr.getBytes(), 0, 5000, TimeUnit.MILLISECONDS);
                    }
                    However.getInstance().newScheduleRequest(mScheduleRequestBody, new AsyncCallback() {
                        @Override
                        public void onSuccess(byte[] t) {
                            mResponse.append("receive: " + new String(t) + "\n" + "\n");
                            mScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }

                        @Override
                        public void onSend(byte[] t) {
                            mResponse.append("send: " + new String(t) + "\n" + "\n");
                            mScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Error error) {
                            Log.e("however", error.getMessage());
                        }
                    });
                } else {
                    mRequestBody = new RequestBody(requestStr.getBytes());
                    However.getInstance().newRequest(mRequestBody, new AsyncCallback() {
                        @Override
                        public void onSuccess(byte[] t) {
                            mResponse.append("receive: " + new String(t) + "\n" + "\n");
                            mScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }

                        @Override
                        public void onSend(byte[] t) {
                            mResponse.append("send: " + new String(t) + "\n" + "\n");
                            mScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Error error) {
                            Log.e("however", error.getMessage());
                        }
                    });
                }

                break;
            case R.id.clear:
                mResponse.setText("");
                break;
            case R.id.set_period:
                requestStr = mContent.getText().toString();
                mScheduleRequestBody = new ScheduleRequestBody(requestStr.getBytes(), 0, Integer.parseInt(mPeriod.getText().toString()), TimeUnit.MILLISECONDS);
                break;
            case R.id.stop:
                However.getInstance().stop();
                break;
        }
    }

}
