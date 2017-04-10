package cn.edu.ncut.istc.service.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ncut.istc.dao.AuthorDao;
import cn.edu.ncut.istc.dao.AuthorOrgDao;
import cn.edu.ncut.istc.dao.OrganizationinfoDao;
import cn.edu.ncut.istc.model.AuthorObj;
import cn.edu.ncut.istc.model.AuthornewObj;
import cn.edu.ncut.istc.model.AuthororgnewObj;
import cn.edu.ncut.istc.model.OrganizationinfoObj;
import cn.edu.ncut.istc.model.assistant.AuthorAssistant;
import cn.edu.ncut.istc.model.assistant.AuthorListAssistant;
import cn.edu.ncut.istc.model.assistant.OrgAssistant;
import cn.edu.ncut.istc.model.assistant.OrgInfoAssistant;
import cn.edu.ncut.istc.model.assistant.OrganizationInfoAssistant;
import cn.edu.ncut.istc.model.plugin.Result;

/**
 * 网站 作者机构管理 service实现类
 * 
 * @author 李越洋
 */
@Transactional
@Service("authorOrgService")
public class AuthorOrgServiceImpl implements AuthorOrgService {

	@Autowired
	AuthorDao authorDao;
	@Autowired
	AuthorOrgDao authorOrgDao;
	@Autowired
	OrganizationinfoDao organizationinfoDao;
	
	@Override
	public List<AuthorListAssistant> getAllAuthor() {
		List<AuthorListAssistant> list = new ArrayList<AuthorListAssistant>();
		List<AuthornewObj> authorList = authorDao.findAll();
		for(AuthornewObj author : authorList){
			AuthorListAssistant assistant = new AuthorListAssistant();
			long authorid = author.getAuthorid();
			assistant.setAuthorid(authorid);
			assistant.setAuthorrealname(author.getAuthorrealname());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("authorid", authorid);
			params.put("authorstatus", new BigDecimal(1));
			//查询指定id作者下的，所有状态为1的authororgObj
			List<AuthororgnewObj> orgList = authorOrgDao.findAll(" authorid = :authorid and authorstatus = :authorstatus ", params, null);
			//判断作者是否有关联的签约网站，如果有，将其查询并关联返回前台。如果没有，将不显示此作者
			if(orgList.size() > 0){
				List<OrgAssistant> orgNames = new ArrayList<OrgAssistant>();
				for(AuthororgnewObj org : orgList){
					OrgAssistant orgObj = new OrgAssistant();
					orgObj.setOrgid(org.getSignwebsiteid().toString());
					orgObj.setOrgname(org.getSignwebsite());
					orgNames.add(orgObj);
				}
				assistant.setOrgs(orgNames);
				list.add(assistant);
			}
		}
		return list;
	}
	
	/**
	 * 展示作者指定签约网站的详情信息
	 * @param authorid		作者id
	 * @param orgfullname	作者签约网站名称
	 * @return
	 */
	@Override
	public AuthorObj getAuthorDetail(long authorid , String orgid) {
		AuthorObj author = new AuthorObj();
		AuthornewObj authorNew = authorDao.find(authorid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("signwebsiteid", orgid);
		params.put("authorid", authorid);
		List<AuthororgnewObj> list = authorOrgDao.findAll(" authorid = :authorid and signwebsiteid = :signwebsiteid ", params, null);
		if(list.size() > 0){
			AuthororgnewObj orgObj = list.get(0);
			author.setPenname(orgObj.getPenname());
			author.setSignwebsite(orgObj.getSignwebsite());
			author.setSignwebsiteid(orgid);
			author.setSigntime(orgObj.getSigntime());
			author.setAuthordesc(orgObj.getAuthordesc());
			author.setRemark(orgObj.getRemark());
			author.setCardtype(orgObj.getCardtype());
			author.setNationality(orgObj.getNationality());
		}
		author.setAuthorid(authorid);
		author.setAuthorrealname(authorNew.getAuthorrealname());
		author.setIdcradno(authorNew.getIdcardno());
		return author;
	}

	@Override
	public void deleteAuthorDetail(long id , String orgid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorid", id);
		params.put("signwebsiteid", orgid);
		List<AuthororgnewObj> list = authorOrgDao.findAll(" authorid = :authorid and signwebsiteid = :signwebsiteid ", params, null);
		if(list.size()>0){
			AuthororgnewObj orgObj = list.get(0);
			orgObj.setAuthorstatus(new BigDecimal(0));
			authorOrgDao.merge(orgObj);
		}
	}

	@Override
	public List<OrganizationinfoObj> getAllOrganization() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgstatus", new BigDecimal(1));
		List<OrganizationinfoObj> list = organizationinfoDao.findAll(" orgstatus = :orgstatus ", params, null);
		return list;
	}
	
	/**
	 * 注：
	 * 1、此处AuthorObj为接口打包的总对象，拆分为两个对象存入主表和副表，命名后续可改
	 * 2、此方法为http接口添加方法，unique必须不重复
	 */
	@Override
	public Result addAuthorDetail(AuthorObj author) {
		String uniqueid = author.getUniqueid();
		//判断uniqueid是否重复
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uniqueid", uniqueid);
		List<AuthororgnewObj> list = authorOrgDao.findAll(" uniqueid = :uniqueid ", params, null);
		if(list.size() > 0){
			return new Result(2,"uniqueid重复");
		}
		//uniqueid不重复，判断主表作者是否重复
		String authorRealName = author.getAuthorrealname();
		String idcard = author.getIdcradno();
		params.clear();
		params.put("authorrealname", authorRealName);
		params.put("idcardno", idcard);
		List<AuthornewObj> authorList = authorDao.findAll(" authorrealname = :authorrealname and idcardno = :idcardno ", params, null);
		if(authorList.size()>0){
			//只存副表
			AuthornewObj authorObj = authorList.get(0);
			AuthororgnewObj orgObj = new AuthororgnewObj();
			orgObj.setAuthorstatus(new BigDecimal(1));
			orgObj.setPenname(author.getPenname());
			orgObj.setSignwebsite(author.getSignwebsite());
			//查询签约网站的id
			params.clear();
			params.put("signwebsite", author.getSignwebsite());
			List<OrganizationinfoObj> l = organizationinfoDao.findAll(" orgfullname = :signwebsite", params, null);
			if(l.size()<=0){
				return new Result(2,"网站信息有误");
			}
			OrganizationinfoObj org = l.get(0);
			orgObj.setSignwebsiteid(org.getOrgid());
			orgObj.setSigntime(author.getSigntime());
			orgObj.setAuthordesc(author.getAuthordesc());
			orgObj.setRemark(author.getRemark());
			orgObj.setTbAuthornew(authorObj);
			orgObj.setUniqueid(author.getUniqueid());
			authorOrgDao.save(orgObj);
		}else{
			//主表副表都存
			AuthornewObj authornewObj = new AuthornewObj();
			authornewObj.setAuthorrealname(author.getAuthorrealname());
			authornewObj.setIdcardno(author.getIdcradno());
			authorDao.save(authornewObj);
			AuthororgnewObj orgObj = new AuthororgnewObj();
			orgObj.setAuthorstatus(new BigDecimal(1));
			orgObj.setPenname(author.getPenname());
			orgObj.setSignwebsite(author.getSignwebsite());
			//查询签约网站的id
			params.clear();
			params.put("signwebsite", author.getSignwebsite());
			List<OrganizationinfoObj> l = organizationinfoDao.findAll(" orgfullname = :signwebsite", params, null);
			if(l.size()<=0){
				return new Result(2,"网站信息有误");
			}
			OrganizationinfoObj org = l.get(0);
			orgObj.setSignwebsiteid(org.getOrgid());
			orgObj.setSigntime(author.getSigntime());
			orgObj.setAuthordesc(author.getAuthordesc());
			orgObj.setRemark(author.getRemark());
			orgObj.setTbAuthornew(authornewObj);
			orgObj.setUniqueid(author.getUniqueid());
			authorOrgDao.save(orgObj);
		}
		return new Result(1,"添加操作完成");

	}
	
	/**
	 * 注：
	 * 管理界面的作者添加
	 */
	@Override
	public Result addAuthorDetail(String orgid, AuthorObj author) {
		//是否有此作者
		String authorRealName = author.getAuthorrealname();
		String idcard = author.getIdcradno();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorrealname", authorRealName);
		params.put("idcardno", idcard);
		List<AuthornewObj> authorList = authorDao.findAll(" authorrealname = :authorrealname and idcardno = :idcardno ", params, null);
		OrganizationinfoObj orgObj = organizationinfoDao.find(orgid);
		String orgfullname = orgObj.getOrgfullname();
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
				orgnewObj.setNationality(author.getNationality());
				orgnewObj.setCardtype(author.getCardtype());
				authorOrgDao.save(orgnewObj);
				return new Result(1,"添加作者成功");
			}else{
				return new Result(2,"该作者已添加过此网站");
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
			orgnewObj.setNationality(author.getNationality());
			orgnewObj.setCardtype(author.getCardtype());
			authorOrgDao.save(orgnewObj);
			return new Result(1,"添加作者成功");
		}
	}
	
	@Override
	public void updateAuthorDetail(long authorid, String orgid, AuthorObj author) {
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
			orgObj.setNationality(author.getNationality());
			orgObj.setCardtype(author.getCardtype());
			authorOrgDao.merge(orgObj);
		}
	}

	@Override
	public OrganizationInfoAssistant getOrgInfoDetail(String orgid) {
		OrganizationInfoAssistant orgInfo = new OrganizationInfoAssistant();
		OrganizationinfoObj org = organizationinfoDao.find(orgid);
		orgInfo.setOrgid(orgid);
		orgInfo.setOrgfullname(org.getOrgfullname());
		orgInfo.setOrgshortname(org.getOrgshortname());
		orgInfo.setAddress(org.getAddress());
		orgInfo.setPhone(org.getPhone());
		orgInfo.setRemark(org.getRemark());
		orgInfo.setZip(org.getZip());
		orgInfo.setFax(org.getFax());
		orgInfo.setNowautoistc(org.getNowautoistc());
		
		List<AuthorAssistant> authors = new ArrayList<AuthorAssistant>();
		//在authororgnew表中查询signwebsiteid为orgid的记录集合
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("signwebsiteid", orgid);
		params.put("authorstatus", new BigDecimal(1));
		List<AuthororgnewObj> list = authorOrgDao.findAll(" signwebsiteid = :signwebsiteid and authorstatus = :authorstatus ", params, null);
		for(AuthororgnewObj authororg : list){
			AuthornewObj author = authorDao.find(authororg.getTbAuthornew().getAuthorid());
			AuthorAssistant assistant = new AuthorAssistant();
			//assistant.setAuthorid(authororg.getTbAuthornew().getAuthorid());
			assistant.setAuthorrealname(author.getAuthorrealname());
			assistant.setIdcardno(author.getIdcardno());
			authors.add(assistant);
		}
		orgInfo.setAuthors(authors);
		return orgInfo;
	}

	@Override
	public void updateOrgInfoDetail(String orgid, OrganizationInfoAssistant orgAssistant) {
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
	
	/**
	 * 添加组织机构的方法，字段为7个，数据库中其余字段，后续可根据需求添加
	 */
	@Override
	public void addOrgInfoDetail(OrganizationInfoAssistant orgAssistant) {
		OrganizationinfoObj orgObj = new OrganizationinfoObj();
		orgObj.setOrgid(orgAssistant.getOrgid());
		orgObj.setOrgfullname(orgAssistant.getOrgfullname());
		orgObj.setOrgshortname(orgAssistant.getOrgshortname());
		orgObj.setPhone(orgAssistant.getPhone());
		orgObj.setFax(orgAssistant.getFax());
		orgObj.setZip(orgAssistant.getZip());
		orgObj.setAddress(orgAssistant.getAddress());
		orgObj.setOrgstatus(new BigDecimal(1));
		orgObj.setParentid("10");
		orgObj.setDeparttype("3");
		organizationinfoDao.save(orgObj);
	}
	
	/**
	 * 注：
	 * 删除组织机构操作仅仅将orgstatus字段设置为0，并没有将authororgnew表中的对应记录authorstatus置为0
	 */
	@Override
	public void deleteOrgInfoDetail(String id) {
		OrganizationinfoObj org = organizationinfoDao.find(id);
		org.setOrgstatus(new BigDecimal(0));
		organizationinfoDao.merge(org);
	}

	@Override
	public List<OrgInfoAssistant> getOrgInfoJsonList() {
		List<OrgInfoAssistant> list = new ArrayList<OrgInfoAssistant>();
		List<OrganizationinfoObj> orgList = organizationinfoDao.findAll();
		for(OrganizationinfoObj org : orgList){
			OrgInfoAssistant assistant = new OrgInfoAssistant();
			assistant.setOrgid(org.getOrgid());
			assistant.setOrgfullname(org.getOrgfullname());
			list.add(assistant);
		}
		return list;
	}
	
	/** 
	@Override
	public List<AuthorAssistant> getAuthorJsonList() {
		List<AuthorAssistant> list = new ArrayList<AuthorAssistant>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorstatus", new BigDecimal(1));
		List<AuthorObj> authorList = authorDao.findAll(" authorstatus = :authorstatus " , params , null);
		for(AuthorObj obj : authorList){
			AuthorAssistant assistant = new AuthorAssistant();
			assistant.setAuthorid(obj.getAuthorid());
			assistant.setAuthorrealname(obj.getAuthorrealname());
			list.add(assistant);
		}
		return list;
	}
	*/

}
