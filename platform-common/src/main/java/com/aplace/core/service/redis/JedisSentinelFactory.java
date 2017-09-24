package com.aplace.core.service.redis;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Descript:
 *
 * @author ningning.wei
 * @version 17/9/22.
 */

public class JedisSentinelFactory implements BeanFactoryPostProcessor {
    private static final Logger LOGGET = Logger.getLogger(JedisSentinelFactory.class);

    private static final String CONF_PREFIX = "redis.ha.conf.";

    private String masterName = "mymaster";

    private Set<String> sentinels = null;
    private static final String CATEGORY_PREFIX = "redis.ha.category.";
    private int category = 0;
    private static final String PASSWORD_PREFIX = "redis.ha.password";
    private String password = null;
    private JedisSentinelPool jedisSentinelPool = null;
    private JedisSentinelPoolConfig jedisSentinelPoolConfig;


    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Properties properties = new Properties();

        try {
            properties = PropertiesLoaderUtils.loadAllProperties("settings.properties");
        } catch (Exception var8) {
            LOGGET.warn("load redis properties", var8);
        }

        String singleton = null;
        Iterator i$ = properties.keySet().iterator();

        while(i$.hasNext()) {
            Object str = i$.next();
            String key = (String)str;
            if(key.startsWith("redis.ha.category.")) {
                this.category = Integer.valueOf(properties.getProperty(key)).intValue();
                singleton = "redis-" + key.substring("redis.ha.category.".length());
            }

            if(key.startsWith("redis.ha.password")) {
                this.password = properties.getProperty(key);
            }

            if(key.startsWith("redis.ha.conf.")) {
                String value = properties.getProperty(key);
                this.sentinels = this.getHostAndPort(value);
            }
        }

        this.connectSentinel();
        if(!configurableListableBeanFactory.containsSingleton(singleton)) {
            configurableListableBeanFactory.registerSingleton(singleton, this.jedisSentinelPool);
            LOGGET.info("register singleton: {"+ singleton+"}");
        }

    }

    private Set<String> getHostAndPort(String value) {
        String[] sentinels = value.split(",");
        Set<String> set = new HashSet();
        String[] arr$ = sentinels;
        int len$ = sentinels.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String sentinel = arr$[i$];
            if(null != sentinel && sentinel.length() >0) {
                LOGGET.info("exists null masterName:host:port");
            } else {
                String[] nameHostPort = sentinel.split(":");
                set.add(nameHostPort[1] + ":" + nameHostPort[2]);
                this.masterName = nameHostPort[0];
            }
        }

        LOGGET.info("sentinel address {}"+value);
        return set;
    }

    private void connectSentinel() {
        this.jedisSentinelPool = new JedisSentinelPool(this.masterName, this.sentinels, this.jedisSentinelPoolConfig.getJedisPoolConfig(), this.jedisSentinelPoolConfig.getConnectionTimeout(), this.jedisSentinelPoolConfig.getSoTimeout(), this.password, this.category);
    }

    public JedisSentinelPoolConfig getJedisSentinelPoolConfig() {
        return this.jedisSentinelPoolConfig;
    }

    public void setJedisSentinelPoolConfig(JedisSentinelPoolConfig jedisSentinelPoolConfig) {
        this.jedisSentinelPoolConfig = jedisSentinelPoolConfig;
    }
//
//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:*.xml");
//        JedisSentinelPool jedisSentinelPool = (JedisSentinelPool)applicationContext.getBean("redis-dmp");
//        Jedis jedis = jedisSentinelPool.getResource();
//        jedis.del("h");
//    }
}
