package com.datacert.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyEncryptedPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  private static byte[] key = { 0x35, 0x36, 0x61, 0x23, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x70 };
  private final static String cipherKey="AES/ECB/PKCS5Padding";
  private final static String encryptionAlgo="AES";

  private static Logger logger = LoggerFactory.getLogger(PropertyEncryptedPlaceholderConfigurer.class);

  public PropertyEncryptedPlaceholderConfigurer() {
	super();
  }

  protected String convertPropertyValue(String originalValue) {
	logger.info("Encrypt Value of "+originalValue +"is"+encrypt(originalValue));
	return originalValue;
  }

  public static String encrypt(String strToEncrypt) {
	try {
	  Cipher cipher = Cipher.getInstance(cipherKey);
	  final SecretKeySpec secretKey = new SecretKeySpec(key, encryptionAlgo);
	  cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	  final String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
	  return encryptedString;
	} catch (Exception e) {
	  logger.error("Not able to Encrypt the property" + strToEncrypt);
	}
	return null;
  }

  public static String decrypt(String strToDecrypt) {
	try {
	  Cipher cipher = Cipher.getInstance(cipherKey);
	  final SecretKeySpec secretKey = new SecretKeySpec(key, encryptionAlgo);
	  cipher.init(Cipher.DECRYPT_MODE, secretKey);
	  final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
	  return decryptedString;
	} catch (Exception e) {
	  logger.error("Not able to Decrypt the property" + strToDecrypt);
	  logger.error(e.toString());
	}
	return null;
  }
}
