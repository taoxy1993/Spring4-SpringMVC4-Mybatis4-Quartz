package com.dfs.utils;

import java.util.Collection;


/**
 * @author taoxy 2019/1/3
 */
public class CollectionUtil {
	public static boolean isNotEmpty(Collection<?> c){
		if (c != null && c.size() != 0 ) {
			return true;
		}
		return false;
	}
}
