package com.daewooenc.pips.admin.core.util.crypto;

import org.springframework.beans.factory.annotation.Value;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * java.security.MessageDigest 를 이용한 암호화 클래스
 * algorithm = "SHA-256"
 */
public final class Sha256Cipher implements Cipher {

	String value;
	String algorithm;
	
	public Sha256Cipher(String _value){
		this.value = _value;
		this.algorithm = "SHA-256";
	}
	
	public Sha256Cipher(String _value, String _algorithm){
		this.value = _value;
		this.algorithm = _algorithm;
	}
	
	/**
	 * algorithm 전달 파라미터 추가한 암호화 메소드
	 */
	public String encrypt() {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(value.getBytes());

			byte[] byteData = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public String decrypt() {
		return null;
	}	
}
