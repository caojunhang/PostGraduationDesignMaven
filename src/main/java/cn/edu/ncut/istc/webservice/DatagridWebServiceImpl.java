package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncut.istc.service.DatagridService;
import cn.edu.ncut.istc.service.base.BaseService;
import cn.edu.ncut.istc.webservice.base.BaseWebService;

/**
 * 进行分页列表展示的webservice发布类，实现分页列表展示基本数据的传递
 */
@WebService(endpointInterface = "cn.edu.ncut.istc.webservice.DatagridWebService")
@Service("datagridWebService")
public class DatagridWebServiceImpl implements DatagridWebService, BaseWebService {

	@Autowired
	private DatagridService datagridService;
	
	@Override
	public BaseService getService() {
		return datagridService;
	}

	@Override
	public String getDifInformPageList(String xml){
		return datagridService.getDifInformPageList();
	}
	
	/*返回information列表*/
	@Override
	public String getInformationPage(String xml){
		return datagridService.getInformationPage();
	}
	
	/************************栏目channel列表****************************/
	@Override
	public String getChannel(String xml) 
	{		
		return datagridService.getChannel();
	}
	
	/**************获得审核意见audit列表*******************/
	@Override
	public String getAuditObjPage(String xml) {
		return datagridService.getAuditObjPage();
	}
	
}
