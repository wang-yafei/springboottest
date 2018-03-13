package com.example.demo.util.encrypt;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
   
public class DesUtil {  
   
    private final static String DES = "DES";  
    private final static String DESDETAIL = "DES/ECB/PKCS5Padding"; 
   
    /*public static void main(String[] args) throws Exception {  
        String data = "测试";  
        String key = "puyitou_des_key";  
        String encrypt = encrypt(data, key);
        System.out.println(encrypt);  
        System.out.println(decrypt(encrypt, key));  
    } */ 
       
    /**  
     * Description 根据键值进行加密  
     * @param data   
     * @param key  加密键byte数组  
     * @return  
     * @throws Exception  
     */  
    public static String encrypt(String data, String key) throws Exception {  
        byte[] bt = encrypt(data.getBytes(), key.getBytes());  
        String strs = Base64.encodeBase64String(bt);  
        return strs;  
    }  
   
    /**  
     * Description 根据键值进行解密  
     * @param data  
     * @param key  加密键byte数组  
     * @return  
     * @throws IOException  
     * @throws Exception  
     */  
    public static String decrypt(String data, String key) throws IOException,  
            Exception {  
        if (data == null)  
            return null;  
        
        byte[] buf = Base64.decodeBase64(data);  
        byte[] bt = decrypt(buf,key.getBytes());  
        return new String(bt);  
    }  
   
    /**  
     * Description 根据键值进行加密  
     * @param data  
     * @param key  加密键byte数组  
     * @return  
     * @throws Exception  
     */  
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {  
        // 从原始密钥数据创建DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);  
   
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);  
        SecretKey securekey = keyFactory.generateSecret(dks);  
   
        
        // Cipher对象实际完成加密操作  
        Cipher cipher = Cipher.getInstance(DESDETAIL);  
   
        // 用密钥初始化Cipher对象  
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        return cipher.doFinal(data);  
    }  
       
       
    /**  
     * Description 根据键值进行解密  
     * @param data  
     * @param key  加密键byte数组  
     * @return  
     * @throws Exception  
     */  
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {  
        // 从原始密钥数据创建DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);  
   
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);  
        SecretKey securekey = keyFactory.generateSecret(dks);  

        // Cipher对象实际完成解密操作  
        Cipher cipher = Cipher.getInstance(DESDETAIL);  
   
        // 用密钥初始化Cipher对象  
        cipher.init(Cipher.DECRYPT_MODE, securekey);  
   
        return cipher.doFinal(data);  
    }  
}  