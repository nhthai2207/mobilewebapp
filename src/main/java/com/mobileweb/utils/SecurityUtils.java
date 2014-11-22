package com.mobileweb.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {

	public static final String CRYPT_AES_ALGORITHM = "AES/CBC/PKCS5Padding";
	// public static String SECRET_KEY = "vUWQDyY+vH3bFHR3FpqrrQ==";
	public static final String ENCRYPT_KEY = "abcdxyz";
	public static SecretKey secretKey;

	public static SecretKey getSecretKey() {
		try {
			if (secretKey == null) {
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
				KeySpec spec = new PBEKeySpec(ENCRYPT_KEY.toCharArray(), "abcd".getBytes(), 65536, 256);
				SecretKey tmp = factory.generateSecret(spec);
				secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secretKey;
	}

	public static String encryptAES(String data) {
		try {
			Cipher cipher = Cipher.getInstance(CRYPT_AES_ALGORITHM);
			byte[] iv = new byte[cipher.getBlockSize()];
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), new IvParameterSpec(iv));
			byte[] aesEncryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
			return Base64.encode(aesEncryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T encryptAES(T data) {
		try {
			Class<? extends Object> class1 = data.getClass();
			class1.getName();
			Cipher cipher = Cipher.getInstance(CRYPT_AES_ALGORITHM);
			byte[] iv = new byte[cipher.getBlockSize()];
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), new IvParameterSpec(iv));
			byte[] aesEncryptedBytes = cipher.doFinal(data.toString().getBytes("UTF-8"));
			return testGeneric(data, Base64.encode(aesEncryptedBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T testGeneric(T data, String x) {
		Class<? extends Object> srcType = data.getClass();
		// String srcType = ;
		System.out.println("======> Src type " + srcType.getName());
		if (srcType.equals(boolean.class) || srcType.equals(Boolean.class))
			return (T) (Boolean.valueOf(x));
		else if (srcType.equals(byte.class) || srcType.equals(Byte.class))
			return (T) (Byte.valueOf(x));
		else if (srcType.equals(char.class) || srcType.equals(Character.class))
			return (T) (Character.valueOf(x.charAt(0)));
		else if (srcType.equals(double.class) || srcType.equals(Double.class))
			return (T) (Double.valueOf(x));
		else if (srcType.equals(float.class) || srcType.equals(Float.class))
			return (T) (Float.valueOf(x));
		else if (srcType.equals(int.class) || srcType.equals(Integer.class))
			return (T) (Integer.valueOf(x));
		else if (srcType.equals(long.class) || srcType.equals(Long.class))
			return (T) (Long.valueOf(x));
		else if (srcType.equals(short.class) || srcType.equals(Short.class))
			return (T) (Short.valueOf(x));
		else {
			return (T) x;
		}
	}

	public static String decryptAES(String data) {
		try {
			byte[] dataBytes = Base64.decode(data);
			Cipher cipher = Cipher.getInstance(CRYPT_AES_ALGORITHM);
			byte[] iv = new byte[cipher.getBlockSize()];
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), ivSpec);
			byte[] result = cipher.doFinal(dataBytes);
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T decryptAES(T data) {
		try {
			byte[] dataBytes = Base64.decode(data.toString());
			Cipher cipher = Cipher.getInstance(CRYPT_AES_ALGORITHM);
			byte[] iv = new byte[cipher.getBlockSize()];
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), ivSpec);
			byte[] result = cipher.doFinal(dataBytes);
			return (T) new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encryptSHA256(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data.getBytes(), 0, data.length());
		byte[] mdbytes = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public static String toHexString(byte[] ba) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < ba.length; i++) {
			str.append(String.format("%02x", ba[i]));
		}
		return str.toString();
	}

	public static byte[] revertToDecimal(String tmp) {
		byte[] ret = new byte[tmp.length() / 2];
		for (int i = 0; i < tmp.length(); i += 2) {
			String aa = tmp.substring(i, i + 2);
			ret[i / 2] = (byte) Integer.parseInt(aa, 16);
		}
		return ret;
	}

	public static void testGenerateKey() {
		String username = "GodFather";
		try {
			byte[] key = username.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] byteData = sha.digest(key);

			key = Arrays.copyOf(byteData, 16); // use only first 128 bit
			System.out.println("key length  " + key.length);
			System.out.println("Base 64 key: " + Base64.encode(key));

			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance(CRYPT_AES_ALGORITHM);
			;
			byte[] iv = new byte[cipher.getBlockSize()];
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
			byte[] doFinal = cipher.doFinal("Nguyen Hong Thai".getBytes());
			String tmp = Base64.encode(doFinal);
			System.out.println(tmp);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static String getMD5String(String input) {
		if (input != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(input.getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}
				return sb.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String asHex(byte buf[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(0xFF & buf[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static void main(String[] args) {
		if (true) {
			for (int i = 0; i < 1000000; i++) {
				Long a = new Long(1);
				Long b = new Long(1);
				if (a == b) {
					System.out.println(a == b);
				}
			}

			return;
		}
		String data = "abc";
		try {
			String encryptedString = encryptAES(data);
			String decrypt = decryptAES(encryptedString);
			System.out.println("Encrypt of data :  " + data + " is:  " + encryptedString + "  decode : " + decrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}