package cn.edu.ncut.istc.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.common.basedao.QueryResult;
import cn.edu.ncut.istc.model.ChannelObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

@Repository("ChannelDao")
public class ChannelDaoImpl extends DaoSupport<ChannelObj> implements ChannelDao {

private final static Logger logger = Logger.getLogger(ChannelDaoImpl.class);

	
	@Override
	public DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby,
			String[] fields) {
		
		
		DatagridResult dr = null;
		try {
			Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
			fieldMap.put("CID", null);//这种地方fieldMap里面填写了什么字段，在页面便会显示什么字段
			fieldMap.put("CPARENTID", null);
			fieldMap.put("CNAME", null);
			fieldMap.put("CDESC", null);
			fieldMap.put("MEMO", null);
			fieldMap.put("CSHOWTYPE", null);
			fieldMap.put("SORTNUM", null);
			fieldMap.put("CREATETIME", null);

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