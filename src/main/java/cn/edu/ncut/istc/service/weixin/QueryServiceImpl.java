package cn.edu.ncut.istc.service.weixin;

import cn.edu.ncut.istc.dao.InformationDao;
import cn.edu.ncut.istc.dao.ProductDao;
import cn.edu.ncut.istc.dao.STDao;
import cn.edu.ncut.istc.dao.SeriseDao;
import cn.edu.ncut.istc.dao.UserinfoDao;
import cn.edu.ncut.istc.model.InformationObj;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.STObj;
import cn.edu.ncut.istc.model.SeriseObj;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.model.UserinfoObj;
import cn.edu.ncut.istc.model.assistant.ValObj;
import cn.edu.ncut.istc.service.base.BaseServiceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("queryService")
public class QueryServiceImpl extends BaseServiceImpl<Object> implements QueryService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private InformationDao informationDao;

	@Autowired
	private STDao sTDao;

	@Autowired
	private SeriseDao seriseDao;

	@Value(value = "${cn.edu.ncut.istc.yeshu32}")
	private String yeshu32;

	@Value(value = "${cn.edu.ncut.istc.yeshu64}")
	private String yeshu64;

	@Value(value = "${cn.edu.ncut.istc.zhuangdingxingshi32}")
	private String zhuangdingxingshi32;

	@Value(value = "${cn.edu.ncut.istc.zhuangdingxingshi64}")
	private String zhuangdingxingshi64;

	@Autowired
	private UserinfoDao userinfoDao;

	@Resource
	protected SessionFactory sessionFactory;

	@Value(value = "${cn.edu.ncut.istc.websitepath}")
	private String websitepath;

	@Override
	public List<SeriseObj> getForecastRank(String year) {
		String sqlStr = "SELECT A.CATA, A.SALES, A.marketshare, A.saletime,A.numbervarieties,A.varietiesshare "
				+ " FROM (SELECT B.*, "
				+ " ROW_NUMBER() OVER(PARTITION BY B.saletime ORDER BY B.marketshare DESC) ROW_NUMBER "
				+ " FROM HK_SERIES B ) A  WHERE A.ROW_NUMBER <= 5  and   A.saletime = to_date('" + year
				+ "','yyyy-MM')";

		System.out.println(sqlStr);
		SQLQuery query = (SQLQuery) sessionFactory.getCurrentSession().createSQLQuery(sqlStr)
				.addEntity(SeriseObj.class);
		return query.list();
	}

	public ValObj returnVal() {
		ValObj val = new ValObj();
		val.setYeshu32(yeshu32);
		val.setYeshu64(yeshu64);
		val.setZhuangdingxingshi32(zhuangdingxingshi32);
		val.setZhuangdingxingshi64(zhuangdingxingshi64);
		return val;
	}

	

	@Override
	public List<SeriseObj> getForecast(String year) {
		String where = "saletime <= to_date('" + year + "','yyyy-MM')";
		return seriseDao.findAll(where, null, null);
	}

	@Override
	public List<STObj> getISBNTime(String as) {
		String where = "cata = '" + as + "'";
		return sTDao.findAll(where, null, null);
	}

	@Override
	public ProductObj getProductByNameOrISTC(String queryValue) {

		ProductObj proObj = new ProductObj();
		proObj.setProductid(0L);
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("PRODUCTNAME", queryValue);
		params.put("ISTC", queryValue);

		String where = "PRODUCTNAME=:PRODUCTNAME or ISTC=:ISTC  ";

		List<ProductObj> productList = productDao.findAll(where, params, null);
		if (productList.size() == 1) {
			ProductObj productObj = productList.get(0);
			return productObj;
		} else {
			return proObj;
		}
	}

	@Override
	public String getWebsitPath() {
		return websitepath;
	}

	@Override
	public String getInfoId(Long productid) {
		List<InformationObj> informationObjList = informationDao.findAll(" pid = " + productid.toString(), null, null);

		if (informationObjList.size() == 1) {
			InformationObj obj = informationObjList.get(0);
			return obj.getInfid().toString();
		} else {
			return "";
		}
	}

	@Override
	public List<Object> getForecast2() {

		return null;
	}

	@Override
	public void saveUserInfo(String info) {
		UserinfoObj userinfoObj = new UserinfoObj();
		userinfoObj.setInfovalue(info);
		userinfoDao.save(userinfoObj);
	}

	@Override
	public BigDecimal saveUserInfoAndgetId(String info) {
		UserinfoObj userinfoObj = new UserinfoObj();
		userinfoObj.setInfovalue(info);
		userinfoDao.save(userinfoObj);

		return userinfoObj.getInfoid();
	}

	@Override
	public UserinfoObj findInfo(String infoid) {

		return userinfoDao.find(infoid);
	}

}
