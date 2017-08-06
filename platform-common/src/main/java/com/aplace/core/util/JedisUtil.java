package com.aplace.core.util;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 管理jedis连接，对数据进行增删查内部封装了jedis pool
 *
 * @author Apeace 
 * @version 2016-11-24
 *
 */
public class JedisUtil {


    /**
     * logger 对象
     */
    private static Logger logger = Logger.getLogger(JedisUtil.class);

    private JedisPool jedisPool;

//    private JedisUtil (String configer) throws IOException {
//        InputStream in = new BufferedInputStream(new FileInputStream(configer));
//        Properties properties = new Properties();
//        properties.load(in);
//
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 最大闲置
//        jedisPoolConfig.setMaxIdle((Integer) properties.get("jedis.maxIdle"));
//        // 最小闲置
//        jedisPoolConfig.setMinIdle((Integer) properties.get("jedis.minIdle"));
//        // 超时时间
//        jedisPoolConfig.setMaxWaitMillis((Long) properties.get("jedis.maxWaitMillis"));
//        // 可获取？
//        jedisPoolConfig.setTestOnBorrow((Boolean) properties.get("jedis.testOnBorrow"));
//    }



    /**
     * 得到一个Jedis操作对象
     *
     * @return Jedis,获得不了将抛出异常
     */
    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource(); 
        } 
        catch (Exception e) {
            logger.info("[JedisPool] 获取 [Jedis] 对象错误");
            throw new JedisConnectionException(e);
        }
        return jedis;
    }

    /**
     * Resource cleanup
     *
     * @param jedis the jedis for close
     */
    public void returnResource(Jedis jedis) {
        if (jedis == null)
            return;
        jedis.close();
    }

    /**
     * 通过key 得到value
     *
     * @param dbIndex database index
     * @param key key byte array
     * @return value byte array
     * @throws Exception when error
     */
    public byte[] getValueByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        byte[] result = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            result = jedis.get(key);
        } catch (Exception e) {
            logger.info("[JedisPool] 通过 [Key] 获取值 [Value] 错误");
            throw e;
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 通过key 删除value
     *
     * @param dbIndex database index
     * @param key key byte array
     * @throws Exception when error
     */
    public void deleteValueByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            Long result = jedis.del(key);
            logger.info( "删除Session结果:"+result);
        } catch (Exception e) {
            logger.info("[JedisPool] 通过 [Key] 删除值 [Value] 错误");
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 根据key 保存 Value
     *
     * @param dbIndex database index
     * @param key key byte array
     * @param value the value be saved
     * @param expireTime 失效时间，单位秒
     * @throws Exception when error
     */
    public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime)
            throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.set(key, value);
            if (expireTime > 0)
                jedis.expire(key, expireTime); 
        } catch (Exception e) {
            logger.info("保存错误");
        	throw e;
        } finally {
            returnResource(jedis);
        }
    }

    // getter & setter ----------------------------
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

	/**
	 * 获取所有多个值
	 * @param dbIndex
	 * @param keys redis表达式
	 * @return
	 * @throws Exception
	 */
    public Set<byte[]> getSetByKeys(int dbIndex,String keys){
    	Jedis jedis = null;
    	Set<byte[]> bytes = new HashSet<byte[]>();
		try {
      jedis = getJedis();
      jedis.select(dbIndex);
      
      bytes = jedis.keys((keys).getBytes());  
  
  } catch (Exception e) {
            logger.info("批量获得缓存错误");
  } finally {
      returnResource(jedis);
  }
  return bytes;
    	
    }
//	@SuppressWarnings("unchecked")
//	public Collection<Session> AllSession(int dbIndex, String redisShiroSession) throws Exception {
//		Jedis jedis = null;
//        boolean isBroken = false;
//        Set<Session> sessions = new HashSet<Session>();
//		try {
//            jedis = getJedis();
//            jedis.select(dbIndex);
//            
//            Set<byte[]> byteKeys = jedis.keys((JedisShiroSessionRepository.REDIS_SHIRO_ALL).getBytes());  
//            if (byteKeys != null && byteKeys.size() > 0) {  
//                for (byte[] bs : byteKeys) {  
//                	Session obj = SerializeUtil.deserialize(jedis.get(bs),  
//                    		 Session.class);  
//                     if(obj instanceof Session){
//                    	 sessions.add(obj);  
//                     }
//                }  
//            }  
//        } catch (Exception e) {
//            isBroken = true;
//            throw e;
//        } finally {
//            returnResource(jedis, isBroken);
//        }
//        return sessions;
//	}
}
