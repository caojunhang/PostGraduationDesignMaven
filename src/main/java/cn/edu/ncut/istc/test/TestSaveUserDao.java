package cn.edu.ncut.istc.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import cn.edu.ncut.istc.dao.UserDao;
import cn.edu.ncut.istc.model.UserObj;


/**
 * 
 * @author dell
 * 测试Dao
 *
 */
@ContextConfiguration(locations="classpath:spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
//true不会改变数据，false会改变数据
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
public class TestSaveUserDao {

	@Autowired
	UserDao userDao;
	
	@Test
	public void test() {
		UserObj userobj = new UserObj();
		userobj.setLoginname("吴迪");
		userobj.setLoginpassword("李越洋");
		userobj.setUsername("林海");
		userDao.merge(userobj);
		
		
	}

}
