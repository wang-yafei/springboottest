package com.example.demo.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
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
public class CommonController {
	
	@SuppressWarnings("restriction")
	@InitBinder
	private void formatTime(WebDataBinder binder) {
		binder.registerCustomEditor(Integer.class,new IntegerEditor()); 
		System.out.println("初始化方法");
	}
	
	
	@ModelAttribute
	public User modelAttribute() {
		System.out.println("添加用户信息");
		return new User("用户id", "用户名称",new Date(11111111));
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String execption(Exception e) {
		System.out.println("异常处理");
		return e.getMessage();
	}
	

}
