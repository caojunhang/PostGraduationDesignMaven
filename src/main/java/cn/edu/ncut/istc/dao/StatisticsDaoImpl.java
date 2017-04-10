package cn.edu.ncut.istc.dao;

import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.StatisticsObj;

@Repository("statisticsDao")
public class StatisticsDaoImpl extends DaoSupport<StatisticsObj> implements StatisticsDao{

}
