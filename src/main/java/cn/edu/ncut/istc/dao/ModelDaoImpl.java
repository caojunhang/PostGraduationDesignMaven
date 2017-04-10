package cn.edu.ncut.istc.dao;

import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.ModelObj;

@Repository("modelDao")
public class ModelDaoImpl extends DaoSupport<ModelObj> implements ModelDao {

}
