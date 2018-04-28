package com.example.demo.datasource.muldatasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DynamicDataSourceAspect {
	

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before("@annotation(TargetDataSource)")
	public void beforeSwitchDS(JoinPoint point) {
		
		//获取当前class
		Class<? extends Object> clasz = point.getTarget().getClass();
		//获取当前类继承和实现的类
		/*Class<?>[] interfaces = clasz.getInterfaces();*/
		//获取当前方法名称
		String methodName = point.getSignature().getName();
		//获取参数
		Class<?>[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
		//如果当前类继承的类或者实现的类上有数据源的注解 设置值
		/*for (Class<?> class1 : parameterTypes) {
			if(class1.isAnnotationPresent(TargetDataSource.class)) {
				this.setClassDataSource(class1);
			}
		}*/
		//获取类上的数据源注解
		this.setClassDataSource(clasz);
		//获取方法上数据源注解
		try {
			/*for (Class<?> class1 : interfaces) {
				try {
					this.setMothedDataSource(class1.getMethod(methodName, parameterTypes));
				} catch (Exception e) {
				}
			}*/
			this.setMothedDataSource(clasz.getMethod(methodName, parameterTypes));
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("根据类和方法名获取方法出错",e);
		}
		
		logger.info("TargetDataSource is" + DataSourceHolder.getDataSourceType());
		
	}
	
	@After("@annotation(TargetDataSource)")
	public void afterSwitchDS(JoinPoint point) {
		DataSourceHolder.removetDataSourceType();
	}
	
	/**
	 * 根据类上的注解设置数据源
	 * @param clasz
	 */
	private void setClassDataSource(Class<?> clasz) {
		if(clasz.isAnnotationPresent(TargetDataSource.class))
			DataSourceHolder.setDataSourceType(clasz.getAnnotation(TargetDataSource.class).value());
	}
	
	/**
	 * 根据方法上的注解设置数据源
	 * @param method
	 */
	private void setMothedDataSource(Method method) {
		if(method.isAnnotationPresent(TargetDataSource.class))
			DataSourceHolder.setDataSourceType(method.getAnnotation(TargetDataSource.class).value());
	}
	
}
