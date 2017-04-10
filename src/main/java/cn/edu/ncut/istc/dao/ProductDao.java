package cn.edu.ncut.istc.dao;

import java.util.Map;

import cn.edu.ncut.istc.common.basedao.Dao;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

public interface ProductDao extends Dao<ProductObj> {
	
	public DatagridResult getProductWaitAuditGrid(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby, String[] fields);
	
	public DatagridResult getProductAuditGrid(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby, String[] fields);
	
}
