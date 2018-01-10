package com.example.demo;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;

@SpringBootApplication
@RestController
public class SpringbootApplication {
	
	@GetMapping("/")
	public String test(ModelAndView model,@ModelAttribute("user") User user) throws Exception {
		System.out.println(user);
		Map<String, Object> model2 = model.getModel();
		System.out.println(model2);
		throw new Exception("错误");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
