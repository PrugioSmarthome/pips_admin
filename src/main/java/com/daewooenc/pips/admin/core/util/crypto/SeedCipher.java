package com.daewooenc.pips.admin.core.util.crypto;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Seed 암호화 
 *
 */
public final class SeedCipher implements com.daewooenc.pips.admin.core.util.crypto.Cipher {
	static final Logger l = LoggerFactory.getLogger(SeedCipher.class);
	
	// 생성된 추출키(DK)에서 처음 16바이트를 암호화 키(K)로 정의한다.
	static byte[] keyData = "!ntels!-ysm-arch".getBytes();//
	
	//"0123456789012345".getBytes();//new byte[16];
	static byte[] iv = {(byte)0x26, (byte)0x8D, (byte)0x66, (byte)0xA7, (byte)0x35, (byte)0xA8, (byte)0x1A, (byte)0x81, (byte)0x6F, (byte)0xBA, (byte)0xD9, (byte)0xFA, (byte)0x36, (byte)0x16, (byte)0x25, (byte)0x01};
	
	// provider 방식
	static String cipherProvider = "SEED/CBC/PKCS5Padding";
	
	static BouncyCastleProvider provider = new BouncyCastleProvider();
	
	static Key key = new SecretKeySpec(keyData, "SEED");

	String value;
	
	SeedCipher(String _value){
		this.value = _value;
	}	
	
	/**
	 * Seed 암호화 메소드
	 */
	public String encrypt(){
		String encrypted = "";
		try{
			
			//인코딩
			Cipher cipher = Cipher.getInstance(cipherProvider, provider);			
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] input = cipher.doFinal(value.getBytes());		

			//SEED 후 전송 가능 ascii로 변환하기 위해 Base64처리
			encrypted = new String(Base64.encodeBase64(input));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return encrypted;
	}
	
	/**
	 * Seed 복호화 메소드
	 */
	public String decrypt(){

		String decrypted = "";
		try{
		
			//디코딩
			Cipher cipher = Cipher.getInstance(cipherProvider, provider);
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] output = cipher.doFinal(Base64.decodeBase64(value));	

			//Decode 처리 후 문자열 형태로 처리가 위함
			decrypted = new String(output);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return decrypted;
	}	
}
