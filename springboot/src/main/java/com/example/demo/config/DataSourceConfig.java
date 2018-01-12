package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.demo.datasource.DynamicDataSource;

@Configuration
public class DataSourceConfig {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Bean(name="master")
	@Qualifier("master")
	@ConfigurationProperties(prefix="spring.datasource.master")
	public DataSource getMasterDataSource() {
		logger.info("初始化数据源master");
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="salve")
	@Qualifier("salve")
	@ConfigurationProperties(prefix="spring.datasource.salve")
	public DataSource getSalverDataSource() {
		logger.info("初始化数据源salve");
		return DataSourceBuilder.create().build();
	}
	
	
	@Bean
	@Primary
	public DynamicDataSource  getDynamicDataSource() {
		 Map<Object, Object> targetDataSources = new HashMap<>();
		 targetDataSources.put("master", getMasterDataSource());
		 targetDataSources.put("salve", getSalverDataSource());
		
		 DynamicDataSource  dynamicDataSource = new DynamicDataSource();
		 dynamicDataSource.setTargetDataSources(targetDataSources);
		 dynamicDataSource.setDefaultTargetDataSource(getSalverDataSource());
		 return dynamicDataSource;
	}
	
	

}
