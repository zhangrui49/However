package com.zhangrui.however;

/**
 * DESC:
 * Created by zhangrui on 2017/1/19.
 */

public class InitConfig {

    private String ip;
    private int port;
    private int corePoolSize;

    private InitConfig(Builder builder) {
        ip = builder.ip;
        port = builder.port;
        corePoolSize = builder.corePoolSize;
    }


    public static final class Builder {
        private String ip;
        private int port;
        private int corePoolSize;


        public Builder CorePoolSize(int val) {
            corePoolSize = val;
            return this;
        }

        public Builder Port(int val) {
            port = val;
            return this;
        }

        public Builder Ip(String val) {
            ip = val;
            return this;
        }

        public InitConfig build() {
            return new InitConfig(this);
        }
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}
