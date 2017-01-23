package com.zhangrui.however;

/**
 * DESC:
 * Created by zhangrui on 2017/1/19.
 */

public class InitConfig {

    private String ip=However.DEFAULT_IP;
    private int port=However.DEFAULT_PORT;
    private int corePoolSize=However.DEFAULT_COREPOOLSIZE;
    private int timeout=However.DEFAULT_TIMEOUT;

    private InitConfig(Builder builder) {
        ip = builder.ip;
        port = builder.port;
        corePoolSize = builder.corePoolSize;
        timeout = builder.timeout;
    }


    public static final class Builder {
        private String ip;
        private int port;
        private int corePoolSize;
        private int timeout;

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

        public Builder Timeout(int val) {
            timeout = val;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
