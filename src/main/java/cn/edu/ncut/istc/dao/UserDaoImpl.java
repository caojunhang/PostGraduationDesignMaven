package cn.edu.ncut.istc.dao;

import org.springframework.stereotype.Repository;

import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.UserObj;

@Repository("userDao")
public class UserDaoImpl extends DaoSupport<UserObj> implements UserDao {

}
