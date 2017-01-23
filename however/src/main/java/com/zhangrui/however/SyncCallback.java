package com.zhangrui.however;

import static com.zhangrui.however.Request.MESSAGE_FAILURE;
import static com.zhangrui.however.Request.MESSAGE_SUCCESS;

/**
 * DESC:
 * Created by zhangrui on 2017/1/20.
 */

public abstract class SyncCallback extends Callback {

    @Override
   public void sendMessage(int what, Object object) {
        switch (what) {
            case MESSAGE_SUCCESS:

                onSuccess((byte[])object);
                break;
            case MESSAGE_FAILURE:
                onFailure(new Error("ERROR"));
                break;
        }
    }
}
