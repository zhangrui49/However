package com.zhangrui.however;

import com.google.gson.Gson;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * DESC:
 * Created by zhangrui on 2017/1/17.
 */

public final class However {
    public static final String TAG="However";
    public static String DEFAULT_IP = null;
    public static int DEFAULT_PORT = -1;
    public static int DEFAULT_COREPOOLSIZE = -1;
    private static ScheduledExecutorService sScheduleExecutor;
    private static volatile However sUdpClient;
    private static Gson sGson;

    public static However getInstance() {
        if (sUdpClient == null) {
            sUdpClient = new However();
        }
        return sUdpClient;
    }

    public static Gson GsonClient() {
        if (sGson == null) {
            sGson = new Gson();
        }
        return sGson;
    }

    public void init(InitConfig config) {
        sScheduleExecutor = Executors.newScheduledThreadPool(config.getCorePoolSize());
        DEFAULT_PORT = config.getPort();
        DEFAULT_IP = config.getIp();
    }

    public void newRequest(IRequest requestBody, Callback callback) {
        Request request = new Request(requestBody, callback);
        if (requestBody instanceof RequestBody) {

            sScheduleExecutor.execute(request);
        } else if (requestBody instanceof ScheduleRequestBody) {
            ScheduleRequestBody scheduleRequestBody = (ScheduleRequestBody) requestBody;
            sScheduleExecutor.scheduleAtFixedRate(request, scheduleRequestBody.getDelayed(), scheduleRequestBody.getPeriod(), scheduleRequestBody.getTimeUnit());
        } else {
            sScheduleExecutor.execute(request);
        }

    }

    public void newRequest(String ip,int port,IRequest requestBody, Callback callback) {
        Request request = new Request(ip,port,requestBody, callback);
        if (requestBody instanceof RequestBody) {

            sScheduleExecutor.execute(request);
        } else if (requestBody instanceof ScheduleRequestBody) {
            ScheduleRequestBody scheduleRequestBody = (ScheduleRequestBody) requestBody;
            sScheduleExecutor.scheduleAtFixedRate(request, scheduleRequestBody.getDelayed(), scheduleRequestBody.getPeriod(), scheduleRequestBody.getTimeUnit());
        } else {
            sScheduleExecutor.execute(request);
        }

    }

    public void close() {
        sScheduleExecutor.shutdownNow();
    }
}
