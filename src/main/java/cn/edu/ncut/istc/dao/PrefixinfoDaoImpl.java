package cn.edu.ncut.istc.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.common.basedao.QueryResult;
import cn.edu.ncut.istc.model.PrefixinfoObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

@Repository("prefixinfoDao")
public class PrefixinfoDaoImpl extends DaoSupport<PrefixinfoObj> implements
		PrefixinfoDao {
	private final static Logger logger = Logger
			.getLogger(PrefixinfoDaoImpl.class);

	/**
	 * 获取前缀信息列表
	 * 
	 * @author 周全，2013-11-07
	 */
	@Override
	public DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby,
			String[] fields) {
		DatagridResult dr = null;
		try {
			Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
			fieldMap.put("PREFIXID", null);
			fieldMap.put("PUBLISHINFO.PUBLISHID", null);
			Map<Object, String> prefixType = new HashMap<Object, String>();
			prefixType.put(false, "音像");
			prefixType.put(true, "电子");
			fieldMap.put("PREFIXTYPE", prefixType);
			fieldMap.put("PREFIXNAME", null);
			Map<Object, String> prefixStatus = new HashMap<Object, String>();
			prefixStatus.put(0L, "曾用");
			prefixStatus.put(1L, "当前");
			prefixStatus.put(2L, "未用");
			fieldMap.put("PREFIXSTATUS", prefixStatus);
			fieldMap.put("CONFIGTIME", null);
			fieldMap.put("STARTCODE", null);
			fieldMap.put("ENDCODE", null);
			fieldMap.put("CURSTARTCODE", null);
			fieldMap.put("STARTDATE", null);
			fieldMap.put("SUSPENDDATE", null);
			Map<Object, String> stopStatus = new HashMap<Object, String>();
			stopStatus.put(false, "否");
			stopStatus.put(true, "是");
			fieldMap.put("STOPSTATUS", stopStatus);
			QueryResult<Map<String, Object>> qr = this.getScrollData(
					firstResult, maxResult, where, params, orderby, fieldMap
							.keySet().toArray(new String[0]));
			dr = qr.getDatagridResult(fieldMap);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return dr;
	}
}
