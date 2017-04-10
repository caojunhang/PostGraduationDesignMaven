package cn.edu.ncut.istc.webservice;

import cn.edu.ncut.istc.service.AuditService;
import cn.edu.ncut.istc.service.base.BaseService;
import cn.edu.ncut.istc.webservice.base.BaseWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * 进行审核管理的webservice发布类
 */
@WebService(endpointInterface = "cn.edu.ncut.istc.webservice.AuditWebService")
@Service("auditWebService")
public class AuditWebServiceImpl implements AuditWebService, BaseWebService {

	@Autowired
	private AuditService auditService;

	@Override
	public BaseService getService() {
		return auditService;
	}

	@Override
	public String getProductWaitBarCode(String xml) {

		return auditService.getProductWaitBarCode();
	}
	
	@Override
	public String getProductBarCode(String xml) {
		
		return auditService.getProductBarCode();
	}

	@Override
	public String getProductAttachByProductId(String xml) {
		return auditService.getProductAttachByProductId();
	}

	@Override
	public String getProductObjByProductid(String xml) {
		// TODO Auto-generated method stub
		return auditService.getProductObjByProductid();
	}

	@Override
	public String sensitiveWordFilterByProductId(String xml)
	{
		return auditService.sensitiveWordFilterByProductId();
	}
}
