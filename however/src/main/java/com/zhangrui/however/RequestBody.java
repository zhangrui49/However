package com.zhangrui.however;

/**
 * DESC:
 * Created by zhangrui on 2017/1/17.
 */

public  class RequestBody implements IRequest{
    private byte[] body;

    public RequestBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody(){
        return body;
    }
}
