package cn.edu.ncut.istc.dao;

import java.util.Map;

import cn.edu.ncut.istc.common.basedao.Dao;
import cn.edu.ncut.istc.model.AuditObj;
import cn.edu.ncut.istc.model.AuthornewObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

public interface AuditDao extends Dao<AuditObj>{
	/**
	 * 获得审核所需作品列表
	 * @param firstResult
	 * @param maxResult
	 * @param where
	 * @param params
	 * @param orderby
	 * @param fields
	 * @return
	 */
	public DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby, String[] fields);
	
}
