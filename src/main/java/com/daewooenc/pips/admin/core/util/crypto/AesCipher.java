package com.daewooenc.pips.admin.core.util.crypto;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * javax.crypto.Cipher과 Base64
 * 암호화 Open Source인 BouncyCastle를 이용한 암호화 클래스
 * transfomation = "AES/ECB/PKCS7Padding"
 *
 */
public final class AesCipher implements com.daewooenc.pips.admin.core.util.crypto.Cipher {

	
	// 생성된 추출키(DK)에서 처음 16바이트를 암호화 키(K)로 정의한다.
	static byte[] keyData = "!ntels!-ysm-arch".getBytes();//	

	// provider 방식
	static String cipherProvider = "AES/ECB/PKCS7Padding";	
	
	static BouncyCastleProvider provider = new BouncyCastleProvider();	
	
	static String charsetName = "UTF-8";
	
	static Key key = new SecretKeySpec(keyData, "AES");

	String value;
	
	AesCipher(String _value){
		this.value = _value;
	}
	
	/**
	 * 암화화 메소드
	 */
	public String encrypt() {

		String encrypted = null;
		try {

			Cipher cipher = Cipher.getInstance(cipherProvider, provider);
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypted = new String(Base64.encodeBase64(cipher.doFinal(value.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encrypted;
	}

	/**
	 * 복호화 메소드
	 */
	public String decrypt() {

		String decrypted = null;
		try {
			Cipher cipher = Cipher.getInstance(cipherProvider, provider);
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decodedString = Base64.decodeBase64(new String(value.getBytes(),charsetName));
			decrypted = new String(cipher.doFinal(decodedString), charsetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decrypted;
	}
}
