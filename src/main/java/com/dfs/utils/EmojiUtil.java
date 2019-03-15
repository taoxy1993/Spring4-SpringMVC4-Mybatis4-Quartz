package com.dfs.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URLEncoder;

/**
 * @author taoxy 2019/1/9
 */
public  class EmojiUtil {


	public static String filterEmoji(String source) {
		if(source != null)
		{
			Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern. CASE_INSENSITIVE ) ;
			Matcher emojiMatcher = emoji.matcher(source);
			if ( emojiMatcher.find())
			{
				source = emojiMatcher.replaceAll("*");
				return source ;
			}
			return source;
		}
		return source;
	}
	public static String emojiConvert(String str) {
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()) {
			try {
				matcher.appendReplacement(sb, "[[EMOJI:"  + URLEncoder.encode(matcher.group(1),"UTF-8") + "]]");
			} catch(UnsupportedEncodingException e) {
				return str;
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String emojiRecovery(String str) {
		String patternString = "\\[\\[EMOJI:(.*?)\\]\\]";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
