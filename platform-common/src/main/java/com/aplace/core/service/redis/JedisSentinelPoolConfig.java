package com.aplace.core.service.redis;

import redis.clients.jedis.JedisPoolConfig;
/**
 * Descript:
 *
 * @author ningning.wei
 * @version 17/9/22.
 */
public class JedisSentinelPoolConfig {
    /** 连接超时时间 */
    private int connectionTimeout = 2000;
    private int soTimeout = 2000;

    private JedisPoolConfig jedisPoolConfig;

    public JedisSentinelPoolConfig() {

    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return this.soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return this.jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }
}
