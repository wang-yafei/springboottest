package com.example.demo.controller;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.datasource.muldatasource.DataSourceHolder;
import com.example.demo.datasource.muldatasource.TargetDataSource;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/hello")
public class HelloController extends BaseController implements InitializingBean,DisposableBean{
	
	@Autowired
	private UserMapper userMapper;
	
	@TargetDataSource("master")
	@ApiOperation(value="添加helloworld访问",notes="添加helloworld访问")
	@GetMapping()
	public String helloWord() {
		logger.info("数据是:"+userMapper.getUser());
		logger.info("dataSource:"+DataSourceHolder.getDataSourceType());
		return "hello world";
	}
	
	@TargetDataSource("savle")
	@ApiOperation(value="获取用户",notes="获取用户列表")
	@GetMapping("user")
	public Map<String, Object> getUser(ModelAndView modelAndView) {
		logger.info("数据是:"+userMapper.getUser());
		logger.info("dataSource:"+DataSourceHolder.getDataSourceType());
		Map<String, Object> model = modelAndView.getModel();
		User user = new User("hello", "王亚飞", Date.valueOf(LocalDate.now()));
		model.put("user", user);
		return model;
		
	}
	
	@PostConstruct
	private void postConstruct() {
		logger.info("初始化方法前调用1");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化方法前调用2");
	}
	
	@PreDestroy
	private void preDestroy() {
		logger.info("初始化方法后调用1");
	}
	
	@Override
	public void destroy() throws Exception {
		logger.info("初始化方法后调用2");
	}
}
