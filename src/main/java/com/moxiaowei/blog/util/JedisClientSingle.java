package com.moxiaowei.blog.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 封装redis操作工具
 * @author moxiaowei
 *
 */
@Component
public class JedisClientSingle {

	@Autowired
	private JedisPool jedisPool;

	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}
	
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	public long hdel(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key, field);
		jedis.close();
		return result;
	}
	
	public Set<String> keys(String pattern) {
		Jedis jedis = jedisPool.getResource();
		Set<String> keys = jedis.keys(pattern);
		jedis.close();
		return keys;
	}
	
	/**
	 * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 * @author moxiaowei
	 */
//	public long zadd(String key, double score, String member) {
//		Jedis jedis = jedisPool.getResource();
//		Long zadd = jedis.zadd(key, score, member);
//		jedis.close();
//		return zadd;
//	}

}
