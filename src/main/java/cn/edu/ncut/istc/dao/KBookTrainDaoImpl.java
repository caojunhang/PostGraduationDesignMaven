package cn.edu.ncut.istc.dao;

import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.KBookTrainObj;

@Repository("kBookTrainDao")
public class KBookTrainDaoImpl extends DaoSupport<KBookTrainObj> implements  KBookTrainDao{

}
