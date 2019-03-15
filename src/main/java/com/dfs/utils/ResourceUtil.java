package com.dfs.utils;


import java.util.ResourceBundle;

/**
 * @author taoxy 2019/1/9
 */
public class ResourceUtil {
	private static ResourceBundle bundle = ResourceBundle.getBundle("sysConfig");

	public static String algorithmServer() {
		return bundle.getString("algorithmServer");
	}

	public static String pictureInsert() {
		return bundle.getString("pictureInsert");
	}

	public static String dataDir() {
		return bundle.getString("dataDir");
	}

	public static String imageServer() {
		return bundle.getString("imageServer");
	}

	public static String wikiDir() {
		return bundle.getString("wikiDir");
	}

	public static String originDir() {
		return bundle.getString("originDir");
	}

	public static String avaterInsert() {
		return bundle.getString("avaterInsert");
	}

	public static String tmpDir() {
		return bundle.getString("tmpDir");
	}

	public static String bossServer() {
		return bundle.getString("bossServer");
	}

	public static String wikiImage() {
		return bundle.getString("wikiImage");
	}

	public static String wikiVideo() {
		return bundle.getString("wikiVideo");
	}

	public static String citrusDaily() {
		return bundle.getString("citrusDaily");
	}

	public static String detection() {
		return bundle.getString("detection");
	}

	public static String chat() {
		return bundle.getString("chat");
	}

	public static String advertImageDir() {
		return bundle.getString("advertImageDir");
	}
	
	public static String specialistImageDir() {
		return bundle.getString("specialistImageDir");
	}
}
