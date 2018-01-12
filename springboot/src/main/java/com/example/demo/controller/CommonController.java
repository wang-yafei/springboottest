package com.example.demo.controller;

import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.model.User;
import com.sun.beans.editors.IntegerEditor;

@RestControllerAdvice
public class CommonController extends BaseController{
	
	@SuppressWarnings("restriction")
	@InitBinder
	private void formatTime(WebDataBinder binder) {
		logger.debug("初始化方法");
		binder.registerCustomEditor(Integer.class,new IntegerEditor()); 
	}
	
	
	@ModelAttribute
	public User modelAttribute() {
		logger.debug("添加用户信息");
		return new User("用户id", "用户名称",new Date(11111111));
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String execption(Exception e) {
		logger.error("开始处理异常");
		return e.getMessage();
	}
	

}
