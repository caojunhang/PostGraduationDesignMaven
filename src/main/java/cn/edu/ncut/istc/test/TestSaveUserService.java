package cn.edu.ncut.istc.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.service.TestService;
/**
 * 
 * @author dell
 * 测试Service
 *
 */
@ContextConfiguration(locations="classpath:spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSaveUserService {

	@Resource
	TestService testService;
	
	@Test
	public void test() {
		UserObj userobj = new UserObj();
		userobj.setLoginname("吴迪");
		userobj.setLoginpassword("李越洋");
		userobj.setUsername("林海");
		testService.saveUser(userobj);
	}
	
}
