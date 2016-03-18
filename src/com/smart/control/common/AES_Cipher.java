package com.smart.control.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

/**
 * AES���ܽ���
 * @ְ�� 
 * @���� 
 * @author ByTom
 */
public class AES_Cipher {
	private static final String TAG = "AES";
	 
	public static byte[] encrypt(String str_Content, String str_Key) {
		if (str_Key == null) {
			Log.d(TAG, "KeyΪ��null");
			return null;
		}
		
		//Ҫ���ܻ���ܵ����ݱ�����16�ı���,�������		
		StringBuilder strBuilder = new StringBuilder(str_Content);
		if((str_Content.length()%16) != 0){
			for(int i=0;i<(16-str_Content.length()%16);i++)		
				strBuilder.append("\0");	
		}
		String str_Filled = strBuilder.toString();
		
		byte[] bytes_Result = encrypt(str_Filled.getBytes(), str_Key.getBytes());
		return bytes_Result;		 
		
	}
	public static String decrypt(byte[] bytes_EnContent, String str_Key) {
		// �ж�Key�Ƿ���ȷ
        if (str_Key == null) {
        	Log.d(TAG, "KeyΪ��null");
            return null;
        }       
        String str_Result = decrypt(bytes_EnContent, str_Key.getBytes());
        return str_Result;
	}
	 
	private static byte[] encrypt(byte[] bytes_Content, byte[] bytes_Key) {
		
		try {		
			SecretKeySpec skeySpec = new SecretKeySpec(bytes_Key,"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// ����������//"�㷨/ģʽ/���뷽ʽ"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);// ��ʼ��
			byte[] result;			
			result = cipher.doFinal(bytes_Content);				 
			return result; // ����
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
		return null;
	}
	public static String decrypt(byte[] bytes_EnContent, byte[] bytes_Key) {
		 
		try {	
            SecretKeySpec skeySpec = new SecretKeySpec(bytes_Key, "AES");
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// ����������
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);// ��ʼ��
			byte[] result = cipher.doFinal(bytes_EnContent);		
			String content = new String(result);		 
			return content; 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
//			return null;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
//			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
//			return null;
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
//			return null;
		} catch (BadPaddingException e) {
			e.printStackTrace();
//			return null;
		}
		return null;
	}

 


	
//	 public static String encrypt(String seed, String clearText) {
//	        Log.d(TAG, "����ǰ��seed=" + seed);
//	        Log.d(TAG, "����Ϊ:" + clearText);
//	        byte[] result = null;
//	        try {
//	            byte[] rawkey = getRawKey(seed.getBytes());
//	            result = encrypt(rawkey, clearText.getBytes());
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        String content = toHex(result);
//	         Log.d(TAG, "���ܺ������Ϊ:" + content);
//	        return content;
//
//	    }
//
//	    public static String decrypt(String seed, String encrypted) {
//	        // Log.d(TAG, "����ǰ��seed=" + seed + ",����Ϊ:" + encrypted);
//	        byte[] rawKey;
//	        try {
//	            rawKey = getRawKey(seed.getBytes());
//	            byte[] enc = toByte(encrypted);
//	            byte[] result = decrypt(rawKey, enc);
//	            String coentn = new String(result);
//	            // Log.d(TAG, "���ܺ������Ϊ:" + coentn);
//	            return coentn;
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return null;
//	        }
//
//	    }
//	    private static byte[] getRawKey(byte[] seed) throws Exception {
//	        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//	        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//	        sr.setSeed(seed);
//	        kgen.init(128, sr);
//	        SecretKey sKey = kgen.generateKey();
//	        byte[] raw = sKey.getEncoded();
//
//	        return raw;
//	    }
//
//    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
////        Cipher cipher = Cipher.getInstance("AES");
//         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
////         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
//                new byte[cipher.getBlockSize()]));
//        
//        byte[] encrypted = cipher.doFinal(clear);
//        return encrypted;
//    }
//
//    private static byte[] decrypt(byte[] raw, byte[] encrypted)
//            throws Exception {
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
////        Cipher cipher = Cipher.getInstance("AES");
//         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
//                new byte[cipher.getBlockSize()]));
//        byte[] decrypted = cipher.doFinal(encrypted);
//        return decrypted;
//    }
//
//    public static String toHex(String txt) {
//        return toHex(txt.getBytes());
//    }
//
//    public static String fromHex(String hex) {
//        return new String(toByte(hex));
//    }
//
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789abcdef";
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}