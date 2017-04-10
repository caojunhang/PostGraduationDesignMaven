package cn.edu.ncut.istc.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.edu.ncut.istc.common.basedao.Dao;
import cn.edu.ncut.istc.model.InformationObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

public interface InformationDao extends Dao<InformationObj>{
	public DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby, String[] fields);
	
	public List<InformationObj> getAllinformations();
	
	public  List<InformationObj> getInformationByPid(BigDecimal pid);

}
