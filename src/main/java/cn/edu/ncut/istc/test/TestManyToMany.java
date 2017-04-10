package cn.edu.ncut.istc.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.ncut.istc.dao.AuthorDao;
import cn.edu.ncut.istc.dao.AuthorOrgDao;
import cn.edu.ncut.istc.dao.OrganizationinfoDao;
import cn.edu.ncut.istc.model.AuthorObj;
import cn.edu.ncut.istc.model.AuthornewObj;
import cn.edu.ncut.istc.model.AuthororgObj;
import cn.edu.ncut.istc.model.AuthororgnewObj;
import cn.edu.ncut.istc.model.OrganizationinfoObj;
import cn.edu.ncut.istc.model.assistant.OrganizationInfoAssistant;
import cn.edu.ncut.istc.model.plugin.Result;

@ContextConfiguration(locations="classpath:spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestManyToMany {
	
	@Autowired
	AuthorDao authorDao;
	@Autowired
	OrganizationinfoDao organizationinfoDao;
	@Autowired
	AuthorOrgDao authorOrgDao;
	
//	@Test
//	public void test(){
//		AuthorObj  author = authorDao.find(1l);
//		System.out.println(author);
//		System.out.println(author.getAuthordesc());
//		List<AuthororgObj> list = author.getTbAuthororgs();
//		for(AuthororgObj obj : list){
//			System.out.println(obj.getOrganizationinfoObj().getOrgfullname());
//		}
//	}
	
	@Test
	public void testGetOrg(){
		OrganizationinfoObj  org = organizationinfoDao.find("1");
		System.out.println(org);
		System.out.println(org.getOrgfullname());
		List<AuthororgObj> list = org.getTbAuthororgs();
		for(AuthororgObj obj : list){
			System.out.println(obj.getTbAuthor().getAuthorid());
		}
		
	}
	
	@Test
	public void testAddAuthorNew(){
		AuthornewObj authornewObj = new AuthornewObj();
		authornewObj.setAuthorrealname("张三");
		authornewObj.setIdcardno("230103199002153126");
		List<AuthororgnewObj> list = new ArrayList<AuthororgnewObj>();
		AuthororgnewObj org1 = new AuthororgnewObj();
		org1.setAuthordesc("AAA");
		org1.setPenname("AAApenname");
		org1.setRemark("AAAremark");
		org1.setSignwebsite("AAAORG");
		org1.setUniqueid("aaaid");
		org1.setAuthorstatus(new BigDecimal(1));
		AuthororgnewObj org2 = new AuthororgnewObj();
		org2.setAuthordesc("BBB");
		org2.setPenname("BBBpenname");
		org2.setRemark("BBBremark");
		org2.setSignwebsite("BBBORG");
		org2.setUniqueid("bbbid");
		org2.setAuthorstatus(new BigDecimal(1));
		list.add(org1);
		list.add(org2);
		authornewObj.setTbAuthororgnews(list);
		authorDao.save(authornewObj);
	}
	
	
	
//	@Test
//	public void test3(){
//		long id=3l;
//		String[] ids = {"1100005","1","10"};
//		AuthorObj author = new AuthorObj();
//		author.setAuthorid(id);
//		author.setAuthorrealname("3改的");
//		author.setSignwebsite("www");
//		author.setPenname("penname333");
//		authorDao.merge(author);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("authorid", id);
//		List<AuthororgObj> oldAuthorOrgs = authorOrgDao.findAll(" authorid = :authorid ",
//				params, null);
//		for (AuthororgObj obj : oldAuthorOrgs) {
//			authorOrgDao.delete(obj.getAuthororgid());
//		}
//		for(String newId : ids){
//			AuthororgObj aorg = new AuthororgObj();
//			aorg.setOrganizationinfoObj(organizationinfoDao.find(newId));
//			aorg.setTbAuthor(author);
//			authorOrgDao.save(aorg);
//		}
//	}

	@Test
	//@Transactional
	public void test44(){
		String[] ids = {"232","242"};
		OrganizationinfoObj org = new OrganizationinfoObj();
		org.setOrgfullname("test44");
		org.setFax("123");
		org.setOrgstatus(new BigDecimal(1));
		org.setOrgshortname("t44");
		organizationinfoDao.save(org);
//		for(String id : ids){
//			AuthororgObj aorg = new AuthororgObj();
//			aorg.setTbAuthor(authorDao.find(Long.parseLong(id)));
//			aorg.setOrganizationinfoObj(org);
//			authorOrgDao.save(aorg);
//		}
	}
	
	/**
	 * 注：
	 * 管理界面的作者添加
	 */
	@Test
	public void addAuthorDetail() {
		AuthorObj author = new  AuthorObj();
		author.setAuthorrealname("lyy333");
		author.setIdcradno("092811333");
		author.setPenname("miemiemie");
		author.setSignwebsite("aaaa");
		author.setSigntime(new Date());
		author.setAuthordesc("狗狗");
		author.setRemark("一只狗狗");
		author.setUniqueid("666666");
		String orgid = "10009";
		OrganizationinfoObj orgObj = organizationinfoDao.find(orgid);
		String orgfullname = orgObj.getOrgfullname();
		//是否有此作者
		String authorRealName = author.getAuthorrealname();
		String idcard = author.getIdcradno();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorrealname", authorRealName);
		params.put("idcardno", idcard);
		List<AuthornewObj> authorList = authorDao.findAll(" authorrealname = :authorrealname and idcardno = :idcardno ", params, null);
		if(authorList.size() > 0){
			//有次作者，判断此orgid是否添加过
			AuthornewObj authornewObj = authorList.get(0);
			
			long authorid = authornewObj.getAuthorid();
			params.clear();
			params.put("authorid", authorid);
			params.put("orgfullname", orgfullname);
			List<AuthororgnewObj> list = authorOrgDao.findAll(" signwebsite = :orgfullname and authorid = :authorid ", params, null);
			if(list.size()<=0){
				//未添加过次网站，只存副表
				AuthororgnewObj orgnewObj = new AuthororgnewObj();
				orgnewObj.setAuthorstatus(new BigDecimal(1));
				orgnewObj.setPenname(author.getPenname());
				orgnewObj.setSignwebsite(orgfullname);
				orgnewObj.setSignwebsiteid(orgid);
				orgnewObj.setSigntime(author.getSigntime());
				orgnewObj.setAuthordesc(author.getAuthordesc());
				orgnewObj.setRemark(author.getRemark());
				orgnewObj.setTbAuthornew(authornewObj);
				orgnewObj.setUniqueid(author.getUniqueid());
				authorOrgDao.save(orgnewObj);
				//return new Result(1,"添加作者成功");
			}else{
				//return new Result(2,"该作者已添加过此网站");
			}
		}else{
			//无此作者，添加主表和副表
			AuthornewObj authornewObj = new AuthornewObj();
			authornewObj.setAuthorrealname(author.getAuthorrealname());
			authornewObj.setIdcardno(author.getIdcradno());
			authorDao.save(authornewObj);
			AuthororgnewObj orgnewObj = new AuthororgnewObj();
			orgnewObj.setAuthorstatus(new BigDecimal(1));
			orgnewObj.setPenname(author.getPenname());
			orgnewObj.setSignwebsite(orgfullname);
			orgnewObj.setSignwebsiteid(orgid);
			orgnewObj.setSigntime(author.getSigntime());
			orgnewObj.setAuthordesc(author.getAuthordesc());
			orgnewObj.setRemark(author.getRemark());
			orgnewObj.setTbAuthornew(authornewObj);
			orgnewObj.setUniqueid(author.getUniqueid());
			authorOrgDao.save(orgnewObj);
			//return new Result(1,"添加作者成功");
		}
	}
	
	@Test
	public void updateAuthorDetail() {
		long authorid = 344L;
		String orgid = "10009";
		AuthorObj author = new AuthorObj();
		author.setPenname("新改的344");
		author.setSigntime(new Date());
		author.setAuthordesc("新改的狗狗");
		author.setRemark("一只新狗狗");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorid", authorid);
		params.put("signwebsiteid", orgid);
		List<AuthororgnewObj> list = authorOrgDao.findAll(" authorid = :authorid and signwebsiteid = :signwebsiteid ", params, null);
		if(list.size()>0){
			AuthororgnewObj orgObj = list.get(0);
			orgObj.setPenname(author.getPenname());
			orgObj.setSigntime(author.getSigntime());
			orgObj.setAuthordesc(author.getAuthordesc());
			orgObj.setRemark(author.getRemark());
			authorOrgDao.merge(orgObj);
		}
	}
	
	@Test
	public void updateOrgInfoDetail() {
		String orgid = "10009";
		OrganizationInfoAssistant orgAssistant = new OrganizationInfoAssistant();
		orgAssistant.setOrgfullname("北方工业大学改动测试");
		orgAssistant.setOrgshortname("NCUT改动测试");
		orgAssistant.setAddress("晋元庄路5号");
		orgAssistant.setPhone("88803374");
		orgAssistant.setZip("zipnew");
		orgAssistant.setFax("faxnew");
		
		OrganizationinfoObj org = organizationinfoDao.find(orgid);
		//判断机构名称是否改动，如果改动，同步更新authororgnew表中的signwebsite字段
		if(!org.getOrgfullname().equals(orgAssistant.getOrgfullname())){
			org.setOrgfullname(orgAssistant.getOrgfullname());
			//查找出authororgnew表中signwebsiteid字段为orgid的集合
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("signwebsiteid", orgid);
			List<AuthororgnewObj> authorOrgList = authorOrgDao.findAll(" signwebsiteid = :signwebsiteid ", params, null);
			if(authorOrgList.size() > 0){
				for(AuthororgnewObj authorOrgObj : authorOrgList){
					authorOrgObj.setSignwebsite(orgAssistant.getOrgfullname());
					authorOrgDao.merge(authorOrgObj);
				}
			}
		}
		org.setOrgshortname(orgAssistant.getOrgshortname());
		org.setAddress(orgAssistant.getAddress());
		org.setPhone(orgAssistant.getPhone());
		org.setZip(orgAssistant.getZip());
		org.setFax(orgAssistant.getFax());
		organizationinfoDao.merge(org);
	}
	
	@Test
	public void addOrgInfoDetail() {
		OrganizationInfoAssistant orgAssistant = new OrganizationInfoAssistant();
		orgAssistant.setOrgid("110007");
		orgAssistant.setOrgfullname("组织机构新增测试");
		orgAssistant.setOrgshortname("ncut简称测试");
		orgAssistant.setAddress("石景山区");
		orgAssistant.setPhone("110");
		orgAssistant.setFax("新增fax");
		orgAssistant.setZip("新增zip");
		
		OrganizationinfoObj orgObj = new OrganizationinfoObj();
		orgObj.setOrgid(orgAssistant.getOrgid());
		orgObj.setOrgfullname(orgAssistant.getOrgfullname());
		orgObj.setOrgshortname(orgAssistant.getOrgshortname());
		orgObj.setPhone(orgAssistant.getPhone());
		orgObj.setFax(orgAssistant.getFax());
		orgObj.setZip(orgAssistant.getZip());
		orgObj.setAddress(orgAssistant.getAddress());
		orgObj.setOrgstatus(new BigDecimal(1));
		organizationinfoDao.save(orgObj);
	}
	

}
