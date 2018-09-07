package com.nowcoder.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * @Date: 18-9-5
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Service
public class JedisAdapter implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
//        pool = new JedisPool("redis://localhost:6739/10");
        pool = new JedisPool("redis://localhost:6379");
    }

    /**
     * 添加一个或多个指定的member元素到集合的 key中
     *
     * @param key
     * @param value
     * @return 返回新成功添加到集合里元素的数量，不包括已经存在于集合中的元素
     */
    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    /**
     * 在key集合中移除指定的元素
     *
     * @param key
     * @param value
     * @return 从集合中移除元素的个数
     */
    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    /**
     * 返回集合存储的key的基数 (集合元素的数量).
     *
     * @param key
     * @return 不存在返回0
     */
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    /**
     * 返回成员 member 是否是存储的集合 key的成员
     *
     * @param key
     * @param value
     * @return 是则返回1，否则返回0
     */
    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 在找到的第一个非空 list 的尾部弹出一个元素
     *
     * @param timeout
     * @param key
     * @return
     */
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 将所有指定的值插入到存于 key 的列表的头部
     *
     * @param key
     * @param value
     * @return 在 push 操作后的 list 长度
     */
    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> lrang(String key,int start,int end){
        Jedis jedis =null;
        try{
           jedis = pool.getResource();
           return jedis.lrange(key,start,end);
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key,double score,String value){
        Jedis jedis =null;
        try{
            jedis = pool.getResource();
            return jedis.zadd(key,score,value);
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long zrem(String key,String value){
        Jedis jedis =null;
        try{
            jedis = pool.getResource();
            return jedis.zrem(key,value);
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public Set<String> zrange(String key,int start,int end){
        Jedis jedis =null;
        try{
            jedis = pool.getResource();
            return jedis.zrange(key,start,end);
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key,int start,int end){
        Jedis jedis =null;
        try{
            jedis = pool.getResource();
            return jedis.zrevrange(key,start,end);
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key){
        Jedis jedis =null;
        try{
            jedis = pool.getResource();
            return jedis.zcard(key);
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key,String member){
        Jedis jedis =null;
        try{
            jedis = pool.getResource();
            return jedis.zscore(key,member);
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Jedis getJedis(){
        return pool.getResource();
    }

    public Transaction multi(Jedis jedis){
        try{
           return jedis.multi();
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
        } finally {

        }
        return null;
    }

    public List<Object> exec(Transaction tx,Jedis jedis){
        try{
           return tx.exec();
        } catch (Exception e){
            log.error("发生异常"+e.getMessage());
            tx.discard();
        } finally {
            if (tx != null) {
                try{
                   tx.close();
                } catch (Exception e){

                }
            }
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }



}
