package cn.edu.ncut.istc.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.common.basedao.QueryResult;
import cn.edu.ncut.istc.model.AuditObj;
import cn.edu.ncut.istc.model.AuthornewObj;
import cn.edu.ncut.istc.model.UserinfoObj;
import cn.edu.ncut.istc.webservice.base.DatagridResult;

@Repository("userinfoDao")
public class UserinfoDaoImpl extends DaoSupport<UserinfoObj> implements UserinfoDao{

}
