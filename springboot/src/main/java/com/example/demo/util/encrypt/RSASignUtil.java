package com.example.demo.util.encrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Formatter;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSASignUtil {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final Logger logger = LoggerFactory.getLogger(RSASignUtil.class);

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/ 
	private RSASignUtil() {
		super();
	}

	/**
	 * rsa签名
	 * @param content
	 * @param priKey
	 * @param input_charset
	 * @return
	 */
	public static String sign(String content, PrivateKey priKey,String inputCharset) {
		try {
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(inputCharset));
			byte[] signed = signature.sign();

			return byteToHex(signed);
		} catch (Exception e) {
			logger.error("rsa签名失败,原因:"+e);
		}
		return null;
	}
	
	/**
	 * rsa签名校验
	 * @param content
	 * @param priKey
	 * @param input_charset
	 * @return
	 */
	public static boolean verify(String content, String sign,PublicKey pubKey, String inputCharset){
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(inputCharset)); 
            boolean bverify = signature.verify(hexStringToByte(sign));
            return bverify;  
        } catch (Exception e){
        	logger.error("rsa签名校验失败,原因:"+e);
        }
        return false;
    }
	
	/**
	 * 解密
	 * @param content
	 * @param prikey
	 * @param input_charset
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public static String decrypt(String content,PrivateKey prikey, String inputCharset) throws  Exception  {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, prikey);
        InputStream ins = new ByteArrayInputStream(hexStringToByte(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;
			while ((bufl = ins.read(buf)) != -1) {
			    byte[] block;
 
			    if (buf.length == bufl) {
			        block = buf;
			    } else {
			        block = new byte[bufl];
			        for (int i = 0; i < bufl; i++) {
			            block[i] = buf[i];
			        }
			    }
			    writer.write(cipher.doFinal(block));
			}
			return new String(writer.toByteArray(), inputCharset);
    }
	
	/**
	 * byte转换成hex
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	/**
	 * hex转换成string
	 * @param s
	 * @return
	 */
	public static byte[] hexStringToByte(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
}
