package cn.edu.ncut.istc.dao;

import java.util.Map;

import cn.edu.ncut.istc.common.basedao.Dao;
import cn.edu.ncut.istc.model.PrefixinfoObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

public interface PrefixinfoDao extends Dao<PrefixinfoObj> {
	public abstract DatagridResult getGridData(int firstResult, int maxResult,
			String where, Map<String, Object> params, String orderby,
			String[] fields);
}
