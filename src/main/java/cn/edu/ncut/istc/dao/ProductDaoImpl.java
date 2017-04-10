package cn.edu.ncut.istc.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.common.basedao.QueryResult;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

@Repository("productDao")
public class ProductDaoImpl extends DaoSupport<ProductObj> implements ProductDao{

	protected final static Logger logger = Logger
			.getLogger(ProductDaoImpl.class);

	@Override
	public DatagridResult getProductWaitAuditGrid(int firstResult,
			int maxResult, String where, Map<String, Object> params,
			String orderby, String[] fields) {
		DatagridResult dr = null;
		try {
			Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
			fieldMap.put("PRODUCTID", null);
			fieldMap.put("PRODUCTNAME", null);
			fieldMap.put("AUTHOR", null);
			fieldMap.put("PRODUCTSTATUS", null);
		
			 
			Map<Object, String> statusMap = new HashMap<Object, String>();
			statusMap.put("100010", "查重不通过");
			statusMap.put("100020", "全文检索不通过");
			statusMap.put("100025", "敏感词过滤不通过");
			statusMap.put("100030", "已配码");
			fieldMap.put("PRODUCTSTATUS", statusMap);
			
	 
			QueryResult<Map<String, Object>> qr = this.getScrollData(
					firstResult, maxResult, where, params, orderby, fieldMap
							.keySet().toArray(new String[0]));
			dr = qr.getDatagridResult(fieldMap);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return dr;
	}
	
	

	@Override
	public DatagridResult getProductAuditGrid(int firstResult,
			int maxResult, String where, Map<String, Object> params,
			String orderby, String[] fields) {
		DatagridResult dr = null;
		try {
			Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
			fieldMap.put("PRODUCTID", null);
			fieldMap.put("PRODUCTNAME", null);
			fieldMap.put("AUTHOR", null);
			fieldMap.put("ISTC", null);
			fieldMap.put("PRODUCTSTATUS", null);

			 
			Map<Object, String> statusMap = new HashMap<Object, String>();
			statusMap.put("100010", "查重不通过");
			statusMap.put("100020", "全文检索不通过");
			statusMap.put("100025", "敏感词过滤不通过");
			statusMap.put("100030", "已配码");
			fieldMap.put("PRODUCTSTATUS", statusMap);
			
	 
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
