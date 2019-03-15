package com.dfs.utils.pay.wechat;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SignatureException;
import java.util.*;


/**  
* @author  
* @version 创建时间：2017年1月17日 下午7:46:29 类说明  
*/   
public class PayUtil {   
    
    public static final Logger log = Logger.getLogger(PayUtil.class);
    /*
     * 商户支付url
     */
    public static String trans_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    /**  
     * 签名字符串  
     * @param text需要签名的字符串  
     * @param key 密钥  
     * @param input_charset编码格式  
     * @return 签名结果  
     */   
    public static String sign(String text/*, String key*/, String input_charset) {   
        /*text = text + key;   */
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));   
    }   
    /**  
     * 签名字符串  
     *  @param text需要签名的字符串  
     * @param sign 签名结果  
     * @param key密钥  
     * @param input_charset 编码格式  
     * @return 签名结果  
     */   
    public static boolean verify(String text, String sign, String key, String input_charset) {   
        text = text + key;   
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));   
        if (mysign.equals(sign)) {   
            return true;   
        } else {   
            return false;   
        }   
    }   
    /**  
     * @param content  
     * @param charset  
     * @return  
     * @throws SignatureException  
     * @throws UnsupportedEncodingException  
     */   
    public static byte[] getContentBytes(String content, String charset) {   
        if (charset == null || "".equals(charset)) {   
            return content.getBytes();   
        }   
        try {   
            return content.getBytes(charset);   
        } catch (UnsupportedEncodingException e) {   
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);   
        }   
    }   
    /**  
     * 生成6位或10位随机数 param codeLength(多少位)  
     * @return  
     */   
    public static String createCode(int codeLength) {   
        String code = "";   
        for (int i = 0; i < codeLength; i++) {   
            code += (int) (Math.random() * 9);   
        }   
        return code;   
    }   
    private static boolean isValidChar(char ch) {   
        if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))   
            return true;   
        if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))   
            return true;// 简体中文汉字编码   
        return false;   
    }   
    /**  
     * 除去数组中的空值和签名参数  
     * @param sArray 签名参数组  
     * @return 去掉空值与签名参数后的新签名参数组  
     */   
    public static Map<String,Object> paraFilter(Map<String,Object> sArray) {   
        Map<String,Object> result = new HashMap<String,Object>();   
        if (sArray == null || sArray.size() <= 0) {   
            return result;   
        }   
        for (String key : sArray.keySet()) {   
            Object value = (Object) sArray.get(key);   
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")   
                    || key.equalsIgnoreCase("sign_type")) {   
                continue;   
            }   
            result.put(key, value);   
        }   
        return result;   
    }   
    /**  
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串  
     * @param params 需要排序并参与字符拼接的参数组  
     * @return 拼接后字符串  
     */   
    public static String createLinkString(Map<String,Object> params) {   
        List<String> keys = new ArrayList(params.keySet());   
        Collections.sort(keys);   
        String prestr = "";   
        for (int i = 0; i < keys.size(); i++) {   
            String key = keys.get(i);   
            Object value = (Object) params.get(key);   
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符   
                prestr = prestr + key + "=" + value;   
            } else {   
                prestr = prestr + key + "=" + value + "&";   
            }   
        }   
        return prestr;   
    }   
    /**  
     *  
     * @param requestUrl请求地址  
     * @param requestMethod请求方法  
     * @param outputStr参数  
     */   
    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){   
        // 创建SSLContext   
        StringBuffer buffer=null;   
        try{   
        URL url = new URL(requestUrl);   
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
        conn.setRequestMethod(requestMethod);   
        conn.setDoOutput(true);   
        conn.setDoInput(true);   
        conn.connect();   
        //往服务器端写内容   
        if(null !=outputStr){   
            OutputStream os=conn.getOutputStream();   
            os.write(outputStr.getBytes("utf-8"));   
            os.close();   
        }   
        // 读取服务器端返回的内容   
        InputStream is = conn.getInputStream();   
        InputStreamReader isr = new InputStreamReader(is, "utf-8");   
        BufferedReader br = new BufferedReader(isr);   
        buffer = new StringBuffer();   
        String line = null;   
        while ((line = br.readLine()) != null) {   
                      buffer.append(line);   
        }   
        }catch(Exception e){   
            e.printStackTrace();   
        }   
        return buffer.toString();   
        }     
    public static String urlEncodeUTF8(String source){   
        String result=source;   
        try {   
            result=java.net.URLEncoder.encode(source, "UTF-8");   
        } catch (UnsupportedEncodingException e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        }   
        return result;   
    }   
    
	public static Map<String, String> parseXmlToMap(String xml) {
		InputStream in = new ByteArrayInputStream(xml.getBytes());
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = null;
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            // XXE漏洞修复
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            reader.setEncoding("GB2312");
            document = reader.read(in);
            
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            for (Element element : elementList)
            {
                map.put(element.getName(), element.getText());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
	}
	
	public static Map<String, String> parseXmlToMap(InputStream in) {
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = null;
        try
        {
            // XXE漏洞修复
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            document = reader.read(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(document.getStringValue());
        
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        Map<String, String> map = new HashMap<String, String>();
        for (Element element : elementList)
        {
            map.put(element.getName(), element.getText());
        }
        return map;
	}
    
}   
