package com.zhangrui.however;

import java.io.Closeable;
import java.io.IOException;

/**
 * DESC:
 * Created by zhangrui on 2017/1/18.
 */

public class CloseUtil {

    public static void close(Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
