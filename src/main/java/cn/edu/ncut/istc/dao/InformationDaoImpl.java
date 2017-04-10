package cn.edu.ncut.istc.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.common.basedao.QueryResult;
import cn.edu.ncut.istc.model.InformationObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

@Repository("informationDao")
public class InformationDaoImpl extends DaoSupport<InformationObj> implements InformationDao {
	private final static Logger logger = Logger.getLogger(InformationDaoImpl.class);

	public DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby,
			String[] fields) {
		DatagridResult dr = null;
		try {
			Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
			fieldMap.put("INFID", null);
			fieldMap.put("INFNAME", null);
			//fieldMap.put("INFTPYE", null);
			fieldMap.put("INFDATE", null);
			fieldMap.put("INFSTATUS", allInfos());
			fieldMap.put("MEMO", null);
			fieldMap.put("PID", null);
			fieldMap.put("PICURL", null);
			fieldMap.put("ISPIC", allPics());
			//fieldMap.put("DELAYDATE", null);
			QueryResult<Map<String, Object>> qr = this.getScrollData(
					firstResult, maxResult, where, params, orderby, fieldMap
							.keySet().toArray(new String[0]));
			dr = qr.getDatagridResult(fieldMap);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return dr;
	}
	
	public Map<Object,String> allPics(){
		Map<Object,String> map = new HashMap<Object,String>();
		map.put("0", "非图片新闻");
		map.put("1", "普通图片新闻");
		map.put("2", "重要图片新闻");
		return map;
		
	}
	
	public Map<BigDecimal, Object> allInfos(){
		Map<BigDecimal, Object> map = new HashMap<BigDecimal, Object>();
		map.put(new BigDecimal(21), "未发布");
		map.put(new BigDecimal(22), "已发布");
		return map;
		
	}
	
	public List<InformationObj> getAllinformations(){
		String sql="select t.infid,t.infname,t.inftype,t.infdate,t.infstatus,t.memo,t.pid,t.picurl,t.ispic,t.delaydate,t.imagename from(select * from(select infid,infname,inftype,infdate,infstatus,memo,pid,picurl,ispic,delaydate,imagename,row_number()over(partition by inftype order by infdate desc) row_num from tb_information where infstatus=22)where row_num <=10) t";
		List<InformationObj> objlist = getList(sql);
		return objlist;

	}
	
	
	public List<InformationObj> getInformationByPid(BigDecimal pid){
		String sql = "select * from tb_information t where pid="+pid;
		List<InformationObj> objlist = getList(sql);
		return objlist;
	}
	
	private List getList(String sql) {
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(InformationObj.class);
		/*sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);*/
		List list = sqlQuery.list();
		return list;
	}
}