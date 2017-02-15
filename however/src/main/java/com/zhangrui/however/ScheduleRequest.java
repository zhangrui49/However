package com.zhangrui.however;

import android.os.Looper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.zhangrui.however.However.DEFAULT_IP;
import static com.zhangrui.however.However.DEFAULT_PORT;
import static com.zhangrui.however.Request.MESSAGE_FAILURE;
import static com.zhangrui.however.Request.MESSAGE_SEND;
import static com.zhangrui.however.Request.MESSAGE_SUCCESS;

/**
 * DESC:
 * Created by zhangrui on 2017/2/15.
 */

public class ScheduleRequest implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    private Callback mCallback;
    private ReceiveThread mReceiveThread;
    public ScheduleRequest(String ip, int port, ScheduleRequestBody requestBody, Callback callback) {
        try {
            mCallback = callback;
            InetAddress serverAddress = InetAddress.getByName(ip);
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            receivePacket = new DatagramPacket(new byte[1024], 1024);
            sendPacket = new DatagramPacket(requestBody.getBody(), requestBody.getBody().length,
                    serverAddress, port);
            mReceiveThread=new ReceiveThread(socket,receivePacket,callback);
            mReceiveThread.start();
        } catch (Exception e) {
            mCallback.sendMessage(MESSAGE_FAILURE, new Error(e.getMessage()));
        }
    }

    public ScheduleRequest(ScheduleRequestBody requestBody, Callback callback) {
        this(DEFAULT_IP, DEFAULT_PORT, requestBody, callback);
    }

//    public ScheduleRequest(long delayed,long period, TimeUnit timeUnit,ScheduleRequestBody requestBody, Callback callback) {
//        this(requestBody, callback);
//        this.delayed = delayed;
//        this.mTimeUnit = timeUnit;
//        this.period = period;
//    }

    @Override
    public void run() {
        try {
            socket.send(sendPacket);
            mCallback.sendMessage(MESSAGE_SEND, sendPacket.getData());
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


    public class ReceiveThread extends Thread{
        private DatagramSocket mSocket;
        private DatagramPacket receivePacket;
        private Callback mCallback;

        public ReceiveThread(DatagramSocket socket, DatagramPacket receivePacket, Callback callback) {
            mSocket = socket;
            this.receivePacket = receivePacket;
            mCallback = callback;
        }

        @Override
        public void run() {
            try {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                while (true){
                    mSocket.receive(receivePacket);
                    mCallback.sendMessage(MESSAGE_SUCCESS, receivePacket.getData());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
