package cn.edu.ncut.istc.dao;

import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.AuthornewObj;

@Repository("authorDao")
public class AuthorDaoImpl extends DaoSupport<AuthornewObj> implements AuthorDao{

}
