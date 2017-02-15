package com.zhangrui.however;

import com.google.gson.Gson;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * DESC:
 * Created by zhangrui on 2017/1/17.
 */

public final class However {
    public static final String TAG = "However";
    public static String DEFAULT_IP = "127.0.0.1";
    public static int DEFAULT_PORT = 8080;
    public static int DEFAULT_COREPOOLSIZE = Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4));
    public static int DEFAULT_TIMEOUT = 15000;
    private static ScheduledExecutorService sScheduleExecutor;
    private static volatile However sUdpClient;
    private static Gson sGson;
    private Request sRequest;
    private ScheduleRequest sScheduleRequest;

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
        stop();
        sScheduleExecutor = Executors.newScheduledThreadPool(config.getCorePoolSize());
        DEFAULT_PORT = config.getPort();
        DEFAULT_IP = config.getIp();
        DEFAULT_TIMEOUT = config.getTimeout();
        sRequest = null;
        sScheduleRequest = null;
    }

    public void newRequest(IRequest requestBody, Callback callback) {
        if (sRequest == null) {
            sRequest = new Request(requestBody, callback);
            sScheduleExecutor.execute(sRequest);
        } else {
            sRequest.setData(requestBody.getBody());
            sScheduleExecutor.execute(sRequest);
        }

//        Request request = new Request(requestBody, callback);
//        if (requestBody instanceof RequestBody) {
//            sScheduleExecutor.execute(request);
//        } else if (requestBody instanceof ScheduleRequestBody) {
//            ScheduleRequestBody scheduleRequestBody = (ScheduleRequestBody) requestBody;
//            sScheduleExecutor.scheduleAtFixedRate(request, scheduleRequestBody.getDelayed(), scheduleRequestBody.getPeriod(), scheduleRequestBody.getTimeUnit());
//        } else {
//            sScheduleExecutor.execute(request);
//        }
    }

    public void newScheduleRequest(ScheduleRequestBody requestBody, Callback callback) {
        if (sScheduleRequest == null) {
            sScheduleRequest = new ScheduleRequest(requestBody, callback);
            sScheduleExecutor.scheduleAtFixedRate(sScheduleRequest, requestBody.getDelayed(), requestBody.getPeriod(), requestBody.getTimeUnit());
        } else {
            sScheduleRequest.setData(requestBody.getBody());
            sScheduleExecutor.scheduleAtFixedRate(sScheduleRequest, requestBody.getDelayed(), requestBody.getPeriod(), requestBody.getTimeUnit());
        }

    }

    public void newRequest(String ip, int port, IRequest requestBody, Callback callback) {
        Request request = new Request(ip, port, requestBody, callback);
        if (requestBody instanceof RequestBody) {
            sScheduleExecutor.execute(request);
        } else if (requestBody instanceof ScheduleRequestBody) {
            ScheduleRequestBody scheduleRequestBody = (ScheduleRequestBody) requestBody;
            sScheduleExecutor.scheduleAtFixedRate(request, scheduleRequestBody.getDelayed(), scheduleRequestBody.getPeriod(), scheduleRequestBody.getTimeUnit());
        } else {
            sScheduleExecutor.execute(request);
        }
    }

    public void stop() {
        if (sScheduleExecutor != null) {
            sScheduleExecutor.shutdownNow();
        }
    }
}
