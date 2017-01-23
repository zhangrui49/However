package com.zhangrui.however;

import java.util.concurrent.TimeUnit;

/**
 * DESC:
 * Created by zhangrui on 2017/1/21.
 */

public class ScheduleRequestBody implements IRequest{

    private byte[] body;
    public static final long DEFAULT_DELAY = 0L;
    public static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MILLISECONDS;
    private long delayed = DEFAULT_DELAY;
    private TimeUnit mTimeUnit = DEFAULT_TIMEUNIT;
    private long period;

    public ScheduleRequestBody(byte[] body, long delayed,long period, TimeUnit timeUnit) {
        this.body = body;
        this.delayed = delayed;
        mTimeUnit = timeUnit;
        this.period = period;
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    public long getDelayed() {
        return delayed;
    }

    public void setDelayed(long delayed) {
        this.delayed = delayed;
    }

    public TimeUnit getTimeUnit() {
        return mTimeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        mTimeUnit = timeUnit;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}
