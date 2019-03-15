package com.dfs.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密工具类
 *
 * @author 陶星袁
 * @date 2018-9-12
 */
public class DesUtil {
	private static final String KEY = "19930701329455536senseagrocomccc";

	//  private static final String KEY ="inspur_OTA-upgrade~!@#$%^&*()";
	public static String encrypt(String strDataToEncrypt) {
		byte[] key = KEY.getBytes();
		Provider sunJCE = new com.sun.crypto.provider.SunJCE();
		Security.addProvider(sunJCE);
		String strAlgorithm = "DES";
		SecretKeySpec keySpec = null;
		DESKeySpec deskey = null;
		String strResult = "";
		try {
			deskey = new DESKeySpec(key);
			keySpec = new SecretKeySpec(deskey.getKey(), "DES");
			Cipher cipher = Cipher.getInstance(strAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] utf8 = strDataToEncrypt.getBytes("UTF8");
			byte[] enc = cipher.doFinal(utf8);
			strResult = new sun.misc.BASE64Encoder().encode(enc);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return strResult;
	}

	public static String decrypt(String strDataToDecrypt) throws IOException {
		byte[] key = KEY.getBytes();
		Provider sunJCE = new com.sun.crypto.provider.SunJCE();
		Security.addProvider(sunJCE);
		String strAlgorithm = "DES";
		SecretKeySpec keySpec = null;
		DESKeySpec deskey = null;
		String strResult = "";
		try {
			deskey = new DESKeySpec(key);
			keySpec = new SecretKeySpec(deskey.getKey(), "DES");
			Cipher cipher = Cipher.getInstance(strAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(strDataToDecrypt);
			byte[] utf8 = cipher.doFinal(dec);
			return new String(utf8, "UTF8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return strResult;

	}
}
