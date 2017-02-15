package com.zhangrui.however;

import android.os.Looper;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.zhangrui.however.However.DEFAULT_IP;
import static com.zhangrui.however.However.DEFAULT_PORT;
import static com.zhangrui.however.However.DEFAULT_TIMEOUT;


/**
 * DESC:
 * Created by zhangrui on 2017/1/17.
 */

public class Request implements Runnable {
    public static final int MESSAGE_SUCCESS = 0x12;
    public static final int MESSAGE_FAILURE = 0x13;
    public static final int MESSAGE_SEND = 0x14;
    private DatagramSocket socket;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    private Callback mCallback;

    public Request(String ip, int port, IRequest requestBody, Callback callback) {
        try {
            mCallback = callback;
            InetAddress serverAddress = InetAddress.getByName(ip);
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            receivePacket = new DatagramPacket(new byte[1024], 1024);
            sendPacket = new DatagramPacket(requestBody.getBody(), requestBody.getBody().length,
                    serverAddress, port);

        } catch (Exception e) {
            mCallback.sendMessage(MESSAGE_FAILURE, new Error(e.getMessage()));
        }
    }

    public Request(IRequest requestBody, Callback callback) {

        this(DEFAULT_IP, DEFAULT_PORT, requestBody, callback);
    }

    @Override
    public void run() {
        try {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            socket.send(sendPacket);
            mCallback.sendMessage(MESSAGE_SEND, sendPacket.getData());
            socket.setSoTimeout(DEFAULT_TIMEOUT);
            while (true){
            socket.receive(receivePacket);
            mCallback.sendMessage(MESSAGE_SUCCESS, receivePacket.getData());
            }

        } catch (Exception e) {
            mCallback.sendMessage(MESSAGE_FAILURE, new Error(e.getMessage()));
        }
    }

    public Callback getCallback() {
        return mCallback;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public byte[] getData() {
        return sendPacket.getData();
    }

    public void setData(byte[] data) {
        sendPacket.setData(data);
    }
}
