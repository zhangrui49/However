package com.zhangrui.however;

import java.lang.reflect.ParameterizedType;

/**
 * DESC:
 * Created by zhangrui on 2017/1/20.
 */

public abstract class JsonAsyncCallback<T> extends AsyncCallback {

    public abstract void onGson(T t);

    public abstract void onFailure(String reason);

    @Override
    public void onSuccess(byte[] t) {
        onGson(However.GsonClient().fromJson(new String(t).trim(), getTClass()));
    }

    @Override
    public void onFailure(Error error) {
        onFailure(error.getMessage());
    }

    @SuppressWarnings("unchecked")
    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        return tClass;
    }
}
