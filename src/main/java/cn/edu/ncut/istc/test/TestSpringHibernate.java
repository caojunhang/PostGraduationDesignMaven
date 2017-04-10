package cn.edu.ncut.istc.test;


import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.ncut.istc.service.weixin.QueryService;

/**
 * 
 * @author dell
 * 测试Spring与Hibernate的集成
 *
 */
public class TestSpringHibernate {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}

	/*
	 * 测试获取queryService
	 */
	@Test
	public void test2() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		QueryService queryService = (QueryService) context.getBean("queryService");
		System.out.println(queryService);
	}
	
	/*
	 * 随机数
	 */
	@Test
	public void test3() {
		int i = (int)(1+Math.random()*(10-1+1));
		String pic ="http://istc.ngrok.natapp.cn/ISTCServer/weixin/"+String.valueOf(i)+".jpg";
		System.out.println(pic);
	}
	
	/*
	 * 测试获取queryService
	 */
	@Test
	public void test4() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		QueryService queryService = (QueryService) context.getBean("queryService");
		System.out.println(queryService.getWebsitPath());
	}

	
}
