package com.dfs.utils;

/**
 * @author taoxy 2019/1/3
 */
public class Constants {

	/**
	 * 存储当前登录用户id的字段名
	 */
	public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

	/**
	 * token有效期（小时）
	 */
	public static final int TOKEN_EXPIRES_HOUR = 6;

	/**
	 * 存放Token的header字段
	 */
	public static final String DEFAULT_TOKEN_NAME = "token";
	/**
	 * 密码正则
	 * 不能全部是数字
	 * 不能全部是字母
	 * 必须是数字或字母
	 */
	public static final String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
}
