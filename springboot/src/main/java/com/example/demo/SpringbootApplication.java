package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@MapperScan("com.example.demo.mapper")
public class SpringbootApplication {
	
	/*@GetMapping("/")
	public String test(ModelAndView model,@ModelAttribute("user") User user) throws Exception {
		System.out.println(user);
		Map<String, Object> model2 = model.getModel();
		System.out.println(model2);
		throw new Exception("错误");
	}*/

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
