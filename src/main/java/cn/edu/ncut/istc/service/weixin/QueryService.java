package cn.edu.ncut.istc.service.weixin;

import java.math.BigDecimal;
import java.util.List;

import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.STObj;
import cn.edu.ncut.istc.model.SeriseObj;
import cn.edu.ncut.istc.model.UserinfoObj;
import cn.edu.ncut.istc.model.assistant.ValObj;
import cn.edu.ncut.istc.service.base.BaseService;


public interface QueryService extends BaseService{

	public ProductObj getProductByNameOrISTC(String queryValue);
	
	public String getWebsitPath();

	public String getInfoId(Long productid);	
	
	public List<SeriseObj> getForecast(String year); 
	
	public List<SeriseObj> getForecastRank(String year); 
	
	public List<STObj> getISBNTime(String as);
	
	public List<Object> getForecast2();
	
	public void saveUserInfo(String info);
	
	public BigDecimal saveUserInfoAndgetId(String info);

	public UserinfoObj findInfo(String infoid);
	
	public ValObj returnVal();
	
}
