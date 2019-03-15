package com.dfs.utils;

import java.util.UUID;


/**
 * @author taoxy 2019/1/3
 */
public class StringUtil {
	/**
	 * @param str
	 * @return
	 * @description 给定字符串是否为空或空串
	 * @author taoxy
	 * @created 2018年12月4日 下午5:15:46
	 */
	public static boolean isNotEmpty(String str) {
		if (str != null && str.length() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param str
	 * @return
	 * @description 给定字符串是否为空或空串
	 * @author taoxy
	 * @created 2018年12月4日 下午5:15:46
	 */
	public static boolean isEmpty(String str) {
		if (str != null && str.length() != 0) {
			return false;
		}
		return true;
	}

	public static StringBuffer getWikiContentInString(String contentList) {
		String[] contents = contentList.split("<p>");
		StringBuffer contentbuffer = new StringBuffer();
		for (int i = 1; i < contents.length ; i++) {
			contents[i] = contents[i].trim();
			String[] part = contents[i].split("<br>");
			for (String string2 : part) {
				StringBuffer stringBuffer = new StringBuffer();
				string2 = string2.replace("<strong>[", "");
				string2 = string2.replace("]</strong>", "");
				stringBuffer.append(string2);
				contentbuffer.append(stringBuffer);
			}
		}
		return contentbuffer;
	}

	public static String createUUID() {
		return UUID.randomUUID().toString();
	}

}
