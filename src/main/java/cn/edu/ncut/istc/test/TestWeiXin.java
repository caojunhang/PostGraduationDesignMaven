package cn.edu.ncut.istc.test;

import javax.annotation.Resource;
import javax.print.attribute.standard.PrinterLocation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.service.weixin.QueryService;

@ContextConfiguration(locations="classpath:spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestWeiXin {

	@Resource
	private QueryService queryService;
	
	@Test
	public void test() {
		String query="微信测试";
		//String query="ISTC-0B1-2016-00000006-2";
		
		ProductObj productObj=queryService.getProductByNameOrISTC(query);
		
		System.out.println(productObj.getProductname()+productObj.getIstc());
	}

}
