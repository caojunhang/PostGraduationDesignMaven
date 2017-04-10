package cn.edu.ncut.istc.dao;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import cn.edu.ncut.istc.common.basedao.DaoSupport;
import cn.edu.ncut.istc.model.PublishinfoObj;


@Repository("publishinfoDao")
public class PublishinfoDaoImpl extends DaoSupport<PublishinfoObj> implements
		PublishinfoDao {
	private final static Logger logger = Logger
			.getLogger(PublishinfoDaoImpl.class);


}

