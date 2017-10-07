package com.aplace.core.service.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author apeace
 * @version 2017/9/27.
 */
public class JedisSentinelSource implements InitializingBean {

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
     * 配置的sentinel集群的 host:port
     */
    private String sentinel;
    /**
     * 配置的sentinel集群的Set结构 host:port
     */
    private Set<String> sentinelSet;
    /**
     * sentinel主的名字
     */
    private String masterName = "mymaster";
    /**
     * 连接超时时间
     */
    Integer connectionTimeout = 2000;
    /**
     *
     */
    Integer soTimeout = 2000;
    /**
     * redis 密码
     */
    String password;
    /**
     * 默认数据库
     */
    Integer database = 0;


    JedisPoolConfig jedisPoolConfig;

    JedisSentinelPool jedisSentinelPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);

        this.sentinelSet = getHostAndPort(sentinel);
        jedisSentinelPool = new JedisSentinelPool(masterName, sentinelSet,
                jedisPoolConfig, connectionTimeout, soTimeout, password, database);
    }

    private Set<String> getHostAndPort(String value) { // value like host:port
        String[] sentinels = value.split(",");
        Set<String> set = new HashSet();

        for(String sentinel : sentinels) {
            if(null != sentinel && sentinel.length() >0) {
                set.add(sentinel);
            } else {
                LOGGER.info("exists null host:port");
            }
        }
        LOGGER.info(String.format("sentinel address %s",value));
        return set;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
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

    public String getSentinel() {
        return sentinel;
    }

    public void setSentinel(String sentinel) {
        this.sentinel = sentinel;
    }

    public Set<String> getSentinelSet() {
        return sentinelSet;
    }

    public void setSentinelSet(Set<String> sentinelSet) {
        this.sentinelSet = sentinelSet;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(Integer soTimeout) {
        this.soTimeout = soTimeout;
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

    public JedisSentinelPool getJedisSentinelPool() {
        return jedisSentinelPool;
    }
}
