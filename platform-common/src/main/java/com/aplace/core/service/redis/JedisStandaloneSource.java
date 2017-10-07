package com.aplace.core.service.redis;

import org.apache.log4j.Logger;
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

    Logger LOGGER = Logger.getLogger(JedisStandaloneSource.class);

    /**
     * 最大实例总数
     */
    private Integer maxTotal = 500;
    /**
     * 最大闲置jedis实例数
     */
    private Integer maxIdle = 100;
    /**
     * 最小闲置实例数量
     */
    private Integer minIdle = 10;
    /**
     * 超时时间，当borrow(创建/引入)一个jedis实例时，最大的等待时间，
     * 如果超过等待时间，则直接抛出JedisConnectionException；
     */
    private Integer maxWaitMillis = 1000;
    /**
     * 在borrow(创建/引入)一个jedis实例时，是否提前进行alidate操作；
     * 如果为true，则得到的jedis实例均是可用的
     */
    private Boolean testOnBorrow = true;

    /**
     * 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
     */
    private Boolean blockWhenExhausted = true;

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
    private Integer timeout = 2000;
    /**
     * 连接密码
     */
    private String password;
    /**
     * 默认数据库
     */
    Integer database = 0;

    /**
     * 连接池设置对象
     */
    private JedisPoolConfig jedisPoolConfig;

    /**
     * 获取资源用的连接池
     */
    private JedisPool jedisPool;

    /**
     * 判断是否进行了初始化
     */
    private boolean isInitialize = false;


    public JedisPool getJedisPool() throws Exception {
        if (!isInitialize) // 还未初始化，主要适用于菲spring项目
            afterPropertiesSet();

        return jedisPool;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);

        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password,database);

        isInitialize = true;
        LOGGER.info(String.format("Redis pool initialized success ! host:%s, port:%s", host, port));

    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    public void setBlockWhenExhausted(Boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
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
