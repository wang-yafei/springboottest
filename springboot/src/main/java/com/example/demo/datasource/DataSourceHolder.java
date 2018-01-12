package com.example.demo.datasource;

public class DataSourceHolder{
	
	private static final ThreadLocal<String> dataSourceThreadLocl = new ThreadLocal<String>();
	
	public static void setDataSourceType(String dataSourceType) {
		dataSourceThreadLocl.set(dataSourceType);
	}
	
	public static String getDataSourceType() {
		return dataSourceThreadLocl.get();
	}
	
	public static void removetDataSourceType() {
		dataSourceThreadLocl.remove();
	}

}
