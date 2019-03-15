package com.dfs.utils;

import com.dfs.entity.SenseAgroMember;
import com.dfs.model.RedisConfig;
import redis.clients.jedis.*;

import java.util.*;

/**
 * @author taoxy 2019/1/3
 */
public class RedisSingletonUtil {
	private static ShardedJedisPool pool = null;

	/**
	 * 初始化线程池
	 */
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(RedisConfig.maxTotal);
		config.setMaxIdle(RedisConfig.maxIdle);
		config.setMaxWaitMillis(RedisConfig.maxWaitMillis);
		config.setTestOnBorrow(RedisConfig.testOnBorrow);
		config.setTestOnReturn(RedisConfig.testOnReturn);
		// 集群
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo(RedisConfig.host, RedisConfig.port);
		jedisShardInfo1.setPassword(RedisConfig.passwd);
		List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
		list.add(jedisShardInfo1);
		pool = new ShardedJedisPool(config, list);
	}

	/**
	 * 获取连接
	 *
	 * @return
	 */
	public static synchronized ShardedJedis getJedis() {
		try {
			return pool.getResource();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param @param key
	 * @param @param obj
	 * @param @param minute
	 * @return boolean 返回类型
	 * @Description:插入对象(值序列化为json)
	 */
	public static boolean setString(String key, Object value, int minute) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value.toString());
			jedis.expire(key, minute * 60);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}

	/**
	 * @param @param key
	 * @param @param obj
	 * @return boolean 返回类型
	 * @Description:插入对象(值序列化为json)
	 */
	public static boolean setString(String key, Object value) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value.toString());
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}

	/**
	 * @param @param key
	 * @return List<Object> 返回类型
	 * @Description:获取list
	 */
	public static List<Object> getList(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			List<Object> list = null;
			Long length = jedis.llen(key);
			if (length > 0) {
				list = Collections.singletonList(jedis.lrange(key, 0, length - 1));
				return (List<Object>) list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}

	/**
	 * @param @param key
	 * @param @param obj
	 * @param @param minute
	 * @return boolean 返回类型
	 * @Description:插入列表
	 */
	public static boolean setList(String key, List<Object> value, int minute) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			if (value != null) {
				for (int i = 0, length = value.size(); i < length; i++) {
					jedis.lpush(key, value.get(i).toString());
				}
			}
			jedis.expire(key, minute * 60);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}

	/**
	 * @param @param key
	 * @return boolean 返回类型
	 * @Description:获取对象
	 */
	public static String get(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			String code = jedis.get(key);
			if (code.equals("[]")) {
				return null;
			}
			return code;
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}

	/**
	 * @param @param key
	 * @return boolean 返回类型
	 * @Description:删除对象
	 */
	public static boolean remove(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}

	public static SenseAgroMember getSenseAgroMember(String openId) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] senseAgroMember = jedis.get(openId.getBytes());
			return (SenseAgroMember) SerializeUtil.unserialize(senseAgroMember);
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}
	public static boolean setSenseAgroMember(String key, SenseAgroMember senseAgroMember, int minute) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key.getBytes(), SerializeUtil.serialize(senseAgroMember));
			jedis.expire(key, minute * 60);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}
	public static boolean removeSenseAgroMember(String openId) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(openId.getBytes());
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}
	/**
	 * @param @param key
	 * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String>返回类 型
	 * @Description:模糊查询对象
	 */
	/*public static Map<String, String> contains(String key) {
		ShardedJedis  jedis = null;
		try {
			jedis = getJedis();
			Set<String> set = jedis.keys("*" + key + "*");
			Map<String, String> hashTable = new HashMap<>();
			if (set != null && set.size() > 0) {
				for (String setKey : set) {
					hashTable.put(setKey, get(setKey));
				}
			}
			return hashTable;
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}*/

	/**
	 * @param @param keys
	 * @return Map<Object                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String> 返回类型
	 * @Description:模糊查询对象
	 */
	/*public static Map<Object, String> contains(Object[] keys) {
		ShardedJedis  jedis = null;
		try {
			jedis = getJedis();
			Map<Object, String> hashTable = new HashMap<>();
			if (keys != null) {
				for (int i = 0, len = keys.length; i < len; i++) {
					Map<String, String> map = contains(keys[i].toString());
					hashTable.putAll(map);
				}
			}
			return hashTable;
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}*/

	/**
	 * @param @param key
	 * @return Long 返回类型
	 * @Description:插入队列
	 */
	/*public static Long lpush(String key, String value) {
		ShardedJedis  jedis = null;
		try {
			Long lpush = jedis.lpush(key, value);
			return lpush;
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}*/

	/**
	 * @param key
	 * @param values String[]
	 * @return 插入队列
	 */
	/*public static Long lpush(String key, String[] values) {
		ShardedJedis  jedis = null;
		try {
			Long lpush = jedis.lpush(key, values);
			return lpush;
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}*/
}
