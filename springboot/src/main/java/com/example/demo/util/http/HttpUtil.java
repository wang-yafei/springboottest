package com.example.demo.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpUtil {
	private  int timeout ;
	private  boolean isKeepAlive;
    private URLConnection connection;
    private String url ;	

    public HttpUtil()
    {
        this.connection = null;
        this.url = "";
        this.timeout = 10000;
        this.isKeepAlive = true;
    }
    /*
     * if we find the url is different with this.url we should new another connection 
     * 
     */
    private void newHttpConnection(String url) throws Exception
    {
        if(this.url != url)
        {
            URL realUrl = new URL(url);
            if(url.toLowerCase().startsWith("https")){
                HttpsURLConnection httpsConn = (HttpsURLConnection)realUrl.openConnection();
                httpsConn.setHostnameVerifier(new HostnameVerifier(){
                    public boolean verify(String hostname, SSLSession session){
                        return true;
                    }
                });
                connection = httpsConn;
            }
            else{
                connection = realUrl.openConnection();
            }
           	this.connection.setRequestProperty("Accept", "*/*");
			if(this.isKeepAlive)
				this.connection.setRequestProperty("Connection", "Keep-Alive");
			this.connection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            this.url = url ;
        }
    }
    public  String request(String method, String url, String req,
			int userTimeout) throws Exception {
		String result = "";
		BufferedReader in = null;
		try{
            if (this.url != url)
                this.newHttpConnection(url);

            this.connection.setConnectTimeout(timeout+userTimeout);
            this.connection.setReadTimeout(timeout+userTimeout);
	
			if (method.equals("POST")) {
				((HttpURLConnection)this.connection).setRequestMethod("POST");
	
				this.connection.setDoOutput(true);
				this.connection.setDoInput(true);
				DataOutputStream out = new DataOutputStream(this.connection.getOutputStream());
				out.writeBytes(req);
				out.flush();
				out.close();
			}

			this.connection.connect();
			int status = ((HttpURLConnection)this.connection).getResponseCode();
			if(status != 200) {
				throw new RuntimeException(status+"");
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
	
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}catch(Exception e){
			throw e;
		}finally{
			try {
				if (in != null) 
					in.close();
			} catch (Exception e2) {
				throw e2;
			}
		}
		return result;
	}

	public static String doGet(String url) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 通过址默认配置创建一个httpClient实例
			httpClient = HttpClients.createDefault();
			// 创建httpGet远程连接实例
			HttpGet httpGet = new HttpGet(url);
			// 设置请求头信息，鉴权
			// 设置配置请求参数
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
					.setConnectionRequestTimeout(35000)// 请求超时时间
					.setSocketTimeout(60000)// 数据读取超时时间
					.build();
			// 为httpGet实例设置配置
			httpGet.setConfig(requestConfig);
			// 执行get请求得到返回对象
			response = httpClient.execute(httpGet);
			// 通过返回对象获取返回数据
			HttpEntity entity = response.getEntity();
			// 通过EntityUtils中的toString方法将结果转换为字符串
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}


}
