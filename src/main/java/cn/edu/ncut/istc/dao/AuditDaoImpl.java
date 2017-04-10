package cn.edu.ncut.istc.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.common.basedao.QueryResult;
import cn.edu.ncut.istc.model.AuditObj;
import cn.edu.ncut.istc.model.AuthornewObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

@Repository("auditDao")
public class AuditDaoImpl extends DaoSupport<AuditObj> implements AuditDao{

private final static Logger logger = Logger.getLogger(AuditDaoImpl.class);
	
	@Override
	public DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby,
			String[] fields) {
		DatagridResult dr = null;
		try {
			Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
			fieldMap.put("AID", null);
			fieldMap.put("ADATE", null);
			fieldMap.put("ASTATUS", null);
			fieldMap.put("OPINION", null);
			QueryResult<Map<String, Object>> qr = this.getScrollData(firstResult, maxResult, where, params, orderby, fieldMap.keySet().toArray(new String[0]));
			dr = qr.getDatagridResult(fieldMap);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return dr;
	}
}
