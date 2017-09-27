package com.aplace.core.service.redis;

import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

/**
 * @author apeace
 * @version 2017/9/27.
 */
public class JedisSentinelSource implements InitializingBean {


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

    Set<String> sentinel;


    JedisPoolConfig jedisPoolConfig;

    JedisSentinelPool jedisSentinelPool;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
