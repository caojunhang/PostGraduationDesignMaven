package cn.edu.ncut.istc.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ncut.istc.dao.AuditDao;
import cn.edu.ncut.istc.dao.ChannelDao;
import cn.edu.ncut.istc.dao.InformationDao;
import cn.edu.ncut.istc.model.base.BaseModel;
import cn.edu.ncut.istc.service.base.BaseServiceImpl;
import cn.edu.ncut.istc.webservice.base.Result;

/**
 * @Description DatagridServiceImpl用来实现所有与分页列表展示功能相关的webservice的业务
 *              使用场合：DatagridServiceImpl调用基本的Dao为分页列表展示提供数据。
 */
@Transactional
@Service("datagridService")
public class DatagridServiceImpl extends BaseServiceImpl<Object> implements DatagridService {

	private final static Logger logger = Logger.getLogger(DatagridServiceImpl.class);

	@Autowired
	private InformationDao informationDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private AuditDao auditDao;

	@Override
	@Transactional(readOnly = true)
	public String getDifInformPageList() {
		Map<String, String> params = parameters.getParams();
		BaseModel result;
		String inftype ="10";
		
		try {
			int firstResult = Integer.parseInt(params.get("firstResult"));
			int maxResult = Integer.parseInt(params.get("maxResult"));

			inftype = params.get("inftype").toString();
			if(null==inftype)
			{
				inftype ="10";
			}
			String orderby = " ORDER BY " + (String) params.get("orderby");
			String requirement = "INFTYPE = " + inftype;

			Map<String, Object> paramswhere = new HashMap<String, Object>();
			String fuzzy = parameters.getParams().get("fuzzy");
			if (fuzzy != null && !fuzzy.equals("")) {
				requirement += "and instr(INFNAME, :fuzzy)>0 ";
				paramswhere.put("fuzzy", fuzzy);
			}

			result = informationDao.getGridData(firstResult, maxResult, requirement, paramswhere, orderby, null);
		} catch (Exception e) {
			logger.error(e);
			Result res = new Result();
			res.setMessage("获取列表异常！");
			res.setType(1);
			result = res;
		}
		logger.debug(result.asXML(false));
		return result.asXML(true);
	}

	/* 返回information列表 */
	@Override
	@Transactional(readOnly = true)
	public String getInformationPage() {
		int firstResult = Integer.parseInt(parameters.getParams().get("firstResult"));
		int maxResult = Integer.parseInt(parameters.getParams().get("maxResult"));
		String orderby = " ORDER BY " + (String) parameters.getParams().get("orderby");

		Map<String, Object> params = new HashMap<String, Object>();
		String where = " ";
		String fuzzy = parameters.getParams().get("fuzzy");
		if (fuzzy != null && !fuzzy.equals("")) {

			where += " instr(INFNAME, :fuzzy)>0 ";
			params.put("fuzzy", fuzzy);
		}

		BaseModel result;
		try {
			result = informationDao.getGridData(firstResult, maxResult, where, params, orderby, null);
		} catch (Exception e) {
			logger.error(e);
			Result res = new Result();
			res.setMessage("获取列表异常！");
			res.setType(1);
			result = res;
		}
		logger.debug(result.asXML(false));
		return result.asXML(true);
	}

	/********************** 获取栏目channel列表 ******************************/
	/**
	 * 获取栏目列表
	 * 
	 * @author dell
	 */
	@Override
	public String getChannel() {
		int firstResult = Integer.parseInt(parameters.getParams().get("firstResult"));
		int maxResult = Integer.parseInt(parameters.getParams().get("maxResult"));
		String orderby = " ORDER BY " + (String) parameters.getParams().get("orderby");
		String where = " cid != 0";

		BaseModel result;
		try {
			result = channelDao.getGridData(firstResult, maxResult, where, null, orderby, null);
		} catch (Exception e) {
			logger.error(e);
			Result res = new Result();
			res.setMessage("获取列表异常！");
			res.setType(1);
			result = res;
		}
		logger.debug(result.asXML(false));
		return result.asXML(true);
	}

	
	/**************获得审核意见audit列表*******************/
	/**
	 * 获取审核列表
	 * @author wc  2016-05-09
	 */
	@Override
	@Transactional(readOnly = true)
	public String getAuditObjPage() {
		Map<String, String> frontParams = parameters.getParams();
		int firstResult = Integer.parseInt(frontParams.get("firstResult"));
		int maxResult = Integer.parseInt(frontParams.get("maxResult"));
		String orderby = " ORDER BY " + (String) frontParams.get("orderby");
		String where = "pid = :pid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", new BigDecimal(frontParams.get("pid")));
		
		BaseModel result;
		try {
			result = auditDao.getGridData(firstResult, maxResult, where, params, orderby, null);
		} catch (Exception e) {
			logger.error(e);
			Result res = new Result();
			res.setMessage("");
			res.setType(1);
			result = res;
		}
		logger.debug(result.asXML(false));
		return result.asXML(true);
	}
}
