package com.trip.finalProject.security.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesProcessor {
	
	// AES/CBC/PKCS5 Padding 사용
	
	@Value("${aes256.secret.key}")
	String projectSecretKey;
		
	@Value("${aes256.iv}")
	String projectIv;
	
	private SecretKeySpec secretKey;
	private IvParameterSpec IV;
	
	@PostConstruct
    public void initKeys() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        
		// properites 에서 받은 secretKey와 iv를 바이트 배열로 변환하여 초기화
        this.secretKey = new SecretKeySpec(projectSecretKey.getBytes("UTF-8"), "AES");
        this.IV = new IvParameterSpec(projectIv.getBytes());
    }
	
    // AES CBC PKCS5Padding 암호화(Hex)
	public String aesCBCEncode(String plainText) throws Exception {
		
		// Cipher 객체 인스턴스화
		Cipher cypher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		// Cipher 객체 초기화
		cypher.init(Cipher.ENCRYPT_MODE, secretKey, IV);
		
		// Encrpytion / Decryption
		byte[] encrpytionByte = cypher.doFinal(plainText.getBytes("UTF-8"));
		
		// Hex Encode
		return Hex.encodeHexString(encrpytionByte);
		
	}

	// AES CBC PKCS5Padding 복호화(Hex)
	public String aesCBCDecode(String encodeText) throws Exception {

		// Cipher 객체 인스턴스화
		Cipher cypher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		// Cipher 객체 초기화
		cypher.init(Cipher.DECRYPT_MODE, secretKey, IV);
		
		// Decode Hex
		byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());
		
		// Encrpytion / Decryption
		return new String(cypher.doFinal(decodeByte), "UTF-8");
	}
	
}
