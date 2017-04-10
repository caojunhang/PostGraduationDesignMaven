package cn.edu.ncut.istc.dao;

import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.FilterwordObj;

@Repository("filterwordDao")
public class FilterwordDaoImpl extends DaoSupport<FilterwordObj> implements FilterwordDao {

}
