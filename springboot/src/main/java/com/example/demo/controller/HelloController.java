package com.example.demo.controller;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.datasource.DataSourceHolder;
import com.example.demo.datasource.TargetDataSource;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/hello")
public class HelloController extends BaseController{
	
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

}
