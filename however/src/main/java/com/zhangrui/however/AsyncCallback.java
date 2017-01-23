package com.zhangrui.however;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import static com.zhangrui.however.Request.MESSAGE_FAILURE;
import static com.zhangrui.however.Request.MESSAGE_SUCCESS;

/**
 * DESC:
 * Created by zhangrui on 2017/1/17.
 */

public abstract class AsyncCallback extends Callback{

    private Handler mHandler;

    public AsyncCallback() {
        mHandler = new InternalHandler(this,Looper.myLooper());
    }

    private static class InternalHandler extends Handler {

        private AsyncCallback mCallback;

        public InternalHandler(AsyncCallback callback, Looper looper) {
            super(looper);
            mCallback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            mCallback.handleMessage(msg);
        }
    }


    private void handleMessage(Message message) {

        switch (message.what) {
            case MESSAGE_SUCCESS:

                onSuccess((byte[])message.obj);
                break;
            case MESSAGE_FAILURE:
                onFailure(new Error("ERROR"));
                break;
        }
    }

    @Override
    public void sendMessage(int what, Object object) {
        mHandler.obtainMessage(what,object).sendToTarget();
    }

}
