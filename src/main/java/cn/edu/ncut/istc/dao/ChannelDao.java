package cn.edu.ncut.istc.dao;

import java.util.Map;
import cn.edu.ncut.istc.common.basedao.Dao;
import cn.edu.ncut.istc.model.ChannelObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

public interface ChannelDao extends Dao<ChannelObj>  {
	/**
	 * 获取列表以供页面显示
	 * 
	 * @param fields
	 * 
	 * @return 页面显示的列表数据
	 */
	public DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby, String[] fields);

}