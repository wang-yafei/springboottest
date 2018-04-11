package com.example.demo.util.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Vector;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	private ChannelSftp sftp;
	private Session session;
	 /** FTP 登录用户名*/  
    private String username;   
    /** FTP 登录密码*/  
    private String password;  
    /** 私钥文件的路径*/  
    private String keyFilePath;  
    /** FTP 服务器地址IP地址*/  
    private String host;  
    /** FTP 端口*/  
    private int port; 
    
    /**
     * @param username 
     * @param password
     * @param host
     * @param port
     */
    public SFTPUtil(String username, String password, String host, int port) {  
        this.username = username;  
        this.password = password;  
        this.host = host;  
        this.port = port;  
    } 
    /**
     * 
     * @param username
     * @param host
     * @param port
     * @param keyFilePath
     */
    public SFTPUtil(String username, String host, int port, String keyFilePath) {  
        this.username = username;  
        this.host = host;  
        this.port = port;  
        this.keyFilePath = keyFilePath;  
    }  
    
	public void getChannel(int timeout) throws JSchException {

        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(username, host, port); // 根据用户名，主机ip，端口获取一个Session对象
        if (password != null) {
            session.setPassword(password); // 设置密码
        }
        
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接

        Channel channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        sftp = (ChannelSftp) channel;  
    }
	
	public void closeChannel() throws Exception {
        if (sftp != null) {
        	sftp.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
	
	public void upload(String directory, String sftpFileName, InputStream input) throws SftpException{  
        try {  
            sftp.cd(directory);  
        } catch (SftpException e) {  
            sftp.mkdir(directory);  
            sftp.cd(directory);  
        }  
        sftp.put(input, sftpFileName);  
    } 
	public void upload(String directory, String uploadFile) throws FileNotFoundException, SftpException{  
        File file = new File(uploadFile);  
        upload(directory, file.getName(), new FileInputStream(file));  
    } 
	public void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException{  
        upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));  
    } 
	public void upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException{  
        upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));  
    } 
	public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException{  
        if (directory != null && !"".equals(directory)) {  
            sftp.cd(directory);  
        }  
        File file = new File(saveFile); 
        File fileParent = file.getParentFile();  
		if(!fileParent.exists()){  
		    fileParent.mkdirs();  
		}
        sftp.get(downloadFile, new FileOutputStream(file)); 
    } 
	public byte[] download(String directory, String downloadFile) throws SftpException, IOException{  
        if (directory != null && !"".equals(directory)) {  
            sftp.cd(directory);  
        }  
        InputStream is = sftp.get(downloadFile);  
          
        byte[] fileData = IOUtils.toByteArray(is);  
          
        return fileData;  
    }  
	public void delete(String directory, String deleteFile) throws SftpException{  
        sftp.cd(directory);  
        sftp.rm(deleteFile);  
    }  
	public Vector<?> listFiles(String directory) throws SftpException {  
        return sftp.ls(directory);  
    }  
	public SftpATTRS getFileInfo(String filePath)throws SftpException {
		 return sftp.lstat(filePath);
	}

}
