package com.dfs.authorization;

import com.dfs.utils.CodecUtil;
import com.dfs.utils.DesUtil;
import com.dfs.utils.RedisSingletonUtil;
import com.dfs.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.Date;


/**
 * @author taoxy 2019/1/3
 */
public class TokenManager {

	private static final Logger log = Logger.getLogger(TokenManager.class);
	private static final RedisSingletonUtil redisUtil = new RedisSingletonUtil();
	/**
	 * @description 利用UUID创建boss Token(用户登录时 ， 创建Token)
	 */
	public static String createToken(int id,int minute) {
		//String token = CodecUtil.createUUID();
		String token = DesUtil.encrypt(id+"-"+new Date().getTime()/1000);
		redisUtil.setString(token, token, minute);
		return token;
	}
	/**
	 * @description 利用UUID创建boss Token(用户登录时 ， 创建Token)
	 */
	public static String createAppToken() {
		String token = "app"+CodecUtil.createUUID();
		redisUtil.setString(token, token, 24);
		return token;
	}
	/**
	 * @description Token验证(用户登录验证)
	 */
	public static boolean checkToken(String token) {
		try {
			log.info("Token is "+token);
			return StringUtil.isNotEmpty(token) && redisUtil.get(token).equals(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @description Token删除(用户登出时 ， 删除Token)
	 */
	public static void deleteToken(String token) {
		try {
			redisUtil.remove(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
