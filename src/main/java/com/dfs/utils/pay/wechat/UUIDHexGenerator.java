package com.dfs.utils.pay.wechat;


import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author
 * @version 创建时间：2017年1月17日 下午7:45:06 类说明
 */
public class UUIDHexGenerator {
	private static String sep = "";
	/* private static final int IP; */
	private static short counter = (short) 0;
	/* private static final int JVM = (int) (System.currentTimeMillis() >>>; */
	private static UUIDHexGenerator uuidgen = new UUIDHexGenerator();

	/*
	 * static { int ipadd; try { ipadd =
	 * toInt(InetAddress.getLocalHost().getAddress()); } catch (Exception e) {
	 * ipadd = 0; } IP = ipadd; }
	 */
	public static UUIDHexGenerator getInstance() {
		return uuidgen;
	}

	/**/
	protected static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	/*
	 * protected static int getJVM() { return JVM; }
	 */
	protected synchronized static short getCount() {
		if (counter < 0) {
			counter = 0;
		}
		return counter++;
	}

	/*
	 * protected static int getIP() { return IP; }
	 */
	protected static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	//生成随机字符串
	  public static String getNonce_str() {
	    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Random random = new Random();
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < 15; i++) {
	      int number = random.nextInt(base.length());
	      sb.append(base.charAt(number));
	    }
	    return sb.toString();
	  }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String id = "";
		UUIDHexGenerator uuid = UUIDHexGenerator.getInstance();
		/*
		 * for (int i = 0; i < 100; i++) { id = uuid.generate(); }
		 */
		id = uuid.getNonce_str();
		System.out.println(id);
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}