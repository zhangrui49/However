package com.zhangrui.however;

/**
 * DESC:
 * Created by zhangrui on 2017/1/20.
 */

public abstract class Callback {

    public abstract void onSuccess(byte[] t);

    public abstract void onFailure(Error error);

    public abstract void onSend(byte[] t);


    public abstract void sendMessage(int what, Object object);
}
