package com.daewooenc.pips.admin.core.util.crypto;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

/**
 * javax.crypto.Cipher과 Base64 암호화 Open Source인 BouncyCastle를 이용한 암호화 클래스
 * transfomation = "AES/ECB/PKCS7Padding"
 */
public final class Aes256Cipher implements com.daewooenc.pips.admin.core.util.crypto.Cipher {

	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00 };

	// provider 방식
	static String cipherProvider = "AES/CBC/PKCS5Padding";

	static BouncyCastleProvider provider = new BouncyCastleProvider();

	static String charsetName = "UTF-8";

	String key;

	String value;

	Aes256Cipher(String _value, String _key) {
		this.value = _value;
		this.key = _key;
	}

	/**
	 * 암화화 메소드
	 */
	public String encrypt() {

		Cipher cipher = null;
		byte[] textBytes = null;
		String encrypted = null;
		try {

			textBytes = value.getBytes(charsetName);
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(charsetName), "AES");

			cipher = Cipher.getInstance(cipherProvider);
			cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

			encrypted = Base64.encodeBase64String(cipher.doFinal(textBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encrypted;
	}

	/**
	 * 복호화 메소드
	 */
	public String decrypt() {
		Cipher cipher = null;
		byte[] textBytes = null;
		String decrypted = null;
		try {

			textBytes = Base64.decodeBase64(value);
			// byte[] textBytes = str.getBytes("UTF-8");
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(charsetName), "AES");
			cipher = Cipher.getInstance(cipherProvider);
			cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
			decrypted = new String(cipher.doFinal(textBytes), charsetName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return decrypted;
	}
}
