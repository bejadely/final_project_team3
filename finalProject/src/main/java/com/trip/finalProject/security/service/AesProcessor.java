package com.trip.finalProject.security.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

@Component
public class AesProcessor {
	
	// AES256 암호화 기법을 사용하기 위한 클래스
	// AES/CBC/PKCS5 Padding 사용예정
	
	private SecretKeySpec secretKey;
	private IvParameterSpec IV;
	
	public AesProcessor() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// secretKey, iv 실험
		this("aeskey87522558466654321asekey987","aesiv12422411342");
	};
	
	public AesProcessor(String reqSecretKey, String iv) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		//바이트 배열로부터 SecretKey를 구축
		this.secretKey = new SecretKeySpec(reqSecretKey.getBytes("UTF-8"), "AES");
		//this.IV = new IvParameterSpec(reqSecretKey.substring(0,16).getBytes());
		this.IV = new IvParameterSpec(iv.getBytes());
	}
	
    //AES CBC PKCS5Padding 암호화(Hex | Base64)
	public String aesCBCEncode(String plainText) throws Exception {
		
		//Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//Cipher 객체 초기화
		c.init(Cipher.ENCRYPT_MODE, secretKey, IV);
		
		//Encrpytion/Decryption
		byte[] encrpytionByte = c.doFinal(plainText.getBytes("UTF-8"));
		
		//Hex Encode
		return Hex.encodeHexString(encrpytionByte);
		
		//Base64 Encode
//		return Base64.encodeBase64String(encrpytionByte);
	}

	//AES CBC PKCS5Padding 복호화(Hex | Base64)
	public String aesCBCDecode(String encodeText) throws Exception {

		//Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//Cipher 객체 초기화
		c.init(Cipher.DECRYPT_MODE, secretKey, IV);
		
		//Decode Hex
		byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());
		
		//Decode Base64
//		byte[] decodeByte = Base64.decodeBase64(encodeText);
		
		//Encrpytion/Decryption
		return new String(c.doFinal(decodeByte), "UTF-8");
	}
	
}
