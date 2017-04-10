package cn.edu.ncut.istc.dao;

import org.springframework.stereotype.Repository;
import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.view.WorkMapObj;


@Repository("workMapDao")
public class WorkMapDaoImpl extends DaoSupport<WorkMapObj> implements WorkMapDao
{
}
