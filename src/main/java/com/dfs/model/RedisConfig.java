package com.dfs.model;

public class RedisConfig {
    public static final Integer maxTotal = 100;
    public static final Integer maxIdle = 50;
    public static final Integer maxWaitMillis = 120;
    public static final Boolean testOnBorrow = true;
    public static final Boolean testOnReturn = true;
    public static final String host = "127.0.0.1";
    public static final Integer port = 3991;
    public static final String passwd = "www.taoxy.online";
}
