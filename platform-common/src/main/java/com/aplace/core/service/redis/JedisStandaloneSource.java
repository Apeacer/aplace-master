package com.aplace.core.service.redis;

import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisStandaloneSource 用于获取redis连接资源
 *
 * @author apeace
 * @version 2017/9/27.
 */
public class JedisStandaloneSource implements InitializingBean {

    /**
     * 最大闲置
     */
    private Integer maxIdle = 100;
    /**
     * 最小闲置
     */
    private Integer minIdle = 10;
    /**
     * 超时时间
     */
    private Integer maxWaitMillis = 1000;
    /**
     * 可获取
     */
    private Boolean testOnBorrow = true;

    /**
     * redis 服务地址
     */
    private String host = "127.0.0.1";
    /**
     * redis 服务端口
     */
    private Integer port = 6379;
    /**
     * redis 超时时间
     */
    private Integer timeout = 5000;
    /**
     * 连接密码
     */
    private String pass = "123";

    /** 连接池设置对象 */
    private JedisPoolConfig jedisPoolConfig;

    /** 获取资源用的连接池 */
    private JedisPool jedisPool;

    /** 判断是否进行了初始化 */
    private boolean isInitialize = false;


    public JedisPool getJedisPool() throws Exception {
        if (!isInitialize) // 还未初始化，主要适用于菲spring项目
            afterPropertiesSet();

        return jedisPool;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, pass);

        isInitialize = true;

    }


    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(Integer maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

//    public JedisPoolConfig getJedisPoolConfig() {
//        return jedisPoolConfig;
//    }
//
//    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
//        this.jedisPoolConfig = jedisPoolConfig;
//    }
//
//    public void setJedisPool(JedisPool jedisPool) {
//        this.jedisPool = jedisPool;
//    }

}
