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
 * javax.crypto.Cipher를 이용한 암호화 클래스
 * transfomation = "AES/CFB8/NoPadding"
 *
 */
public final class AesIVCipher implements com.daewooenc.pips.admin.core.util.crypto.Cipher {
	static final Logger logger = LoggerFactory.getLogger(AesIVCipher.class);
	
	
	// 생성된 추출키(DK)에서 처음 16바이트를 암호화 키(K)로 정의한다.
	static byte[] keyData = "!ntels!-ysm-arch".getBytes();//	

	// provider 방식
	static String cipherProvider = "AES/CFB8/NoPadding";	
	
	static BouncyCastleProvider provider = new BouncyCastleProvider();	
	
	static String charsetName = "UTF-8";
	
	static Key key = new SecretKeySpec(keyData, "AES");	
	
	static byte[] iv = "26c7d1d265142de0".getBytes();

	String value;
	
	AesIVCipher(String _value){
		this.value = _value;
	}
	
	/**
	 * 암호화 메소드
	 *
	 */
	public String encrypt(){
		String encrypted = null;
		try {
			Cipher cipher = Cipher.getInstance(cipherProvider, provider);
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			//encrypted = bytes2Hex( cipher.doFinal(encrypt.getBytes()) );
			encrypted = new String(Base64.encodeBase64(cipher.doFinal(value.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encrypted;
	}	
	
	/**
	 * 복호화 메소드
	 */
	public String decrypt(){
		String decrypted = null;
		try {
			Cipher cipher = Cipher.getInstance(cipherProvider, provider);
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			//decrypted = new String(cipher.doFinal(hex2byte(sBase64EncodedEncrypted)));
			decrypted = new String(cipher.doFinal(Base64.decodeBase64(value.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decrypted;
	}

	/**
	 * 
	 * 
	 * @param s
	 * @return byte[]
	 */
	@SuppressWarnings("unused")
	private static byte[] hex2byte(String s) {
		if (s == null)
			return null;
		int l = s.length();
		if (Math.abs(l % 2) == 1)
			return null;
		byte[] b = new byte[l / 2];
		for (int i = 0; i < l / 2; i++) {
			b[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	private static String byte2Hex(byte b) {
		String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "a", "b", "c", "d", "e", "f" };
		int nb = b & 0xFF;
		int i_1 = (nb >> 4) & 0xF;
		int i_2 = nb & 0xF;
		return HEX_DIGITS[i_1] + HEX_DIGITS[i_2];
	}

	@SuppressWarnings("unused")
	private static String bytes2Hex(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int x = 0; x < b.length; x++) {
			sb.append(byte2Hex(b[x]));
		}
		return sb.toString();
	}
}
