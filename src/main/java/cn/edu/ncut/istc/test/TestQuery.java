package cn.edu.ncut.istc.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.ncut.istc.dao.ProductDao;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.assistant.IntegeratedQueryObj;
import cn.edu.ncut.istc.service.SystemService;

@ContextConfiguration(locations="classpath:spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestQuery {

	
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testfy() {
		StringBuilder timeBuilder = new StringBuilder("");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String starttime = new StringBuilder("to_date('")
				.append("2015-01-07")
				.append("','yyyy-MM-dd')) ").toString();
		String endtime = new StringBuilder("to_date('")
				.append("2016-01-08")
				.append("','yyyy-MM-dd')) ").toString();
		timeBuilder.append("(CREATETIME>=").append(starttime);
		timeBuilder.append("and (CREATETIME<=").append(endtime);
		
		String where=timeBuilder.toString();

		List<ProductObj> productObjList =  productDao.findAll(45, 15, where, null, null);
		System.out.println(productObjList.size());
	}
	
	@Test
	public void testfycount() {
		Map<String, Object> params=new HashMap<String, Object>();
		StringBuilder timeBuilder = new StringBuilder("");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String starttime = new StringBuilder("to_date('")
				.append("2015-01-07")
				.append("','yyyy-MM-dd')) ").toString();
		String endtime = new StringBuilder("to_date('")
				.append("2016-01-08")
				.append("','yyyy-MM-dd')) ").toString();
		timeBuilder.append("(CREATETIME>=").append(starttime);
		timeBuilder.append("and (CREATETIME<=").append(endtime);
		
		String where=timeBuilder.toString();
		System.out.println(new Long(productDao.getCount(where, params)).intValue());
		
	}
	

}
