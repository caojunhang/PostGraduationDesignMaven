package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncut.istc.service.NoticeService;
import cn.edu.ncut.istc.service.base.BaseService;
import cn.edu.ncut.istc.webservice.base.BaseWebService;
import cn.edu.ncut.istc.model.*;

/**
 * 进行istc公告公示管理的webservice发布类，实现公告公示数据的传递
 */
@WebService(endpointInterface = "cn.edu.ncut.istc.webservice.NoticeWebService")
@Service("noticeWebService")
public class NoticeWebServiceImpl implements NoticeWebService, BaseWebService {

	@Autowired
	private NoticeService noticeService;

	@Override
	public BaseService getService() {
		return noticeService;
	}
	
	
	/*********************信息发布InformationObj相关操作***********************/
	@Override
	public String delectSelectsInformation(String xml) {
		return noticeService.delectSelectsInformation();
	}
	
	/*保存一条信息*/
	@Override
	public String saveInformation(String xml){
		return noticeService.saveInformation();
	}
	
	/**************************栏目管理**************************/
	@Override
	public String getChannelObjByChannelid(String xml){
		return noticeService.getChannelObjByChannelid();
	}
	
	
	@Override
	public String saveChannelObj(String xml){
		return noticeService.saveChannelObj();
	}
	
	@Override
	public String updateChannelObj(String xml){
		return noticeService.updateChannelObj();
	}
	
	@Override
	public String deleteChannelObjByCids(String xml){
		return noticeService.deleteChannelObjByCids();
	}
	
	@Override
	public String getAllChannel(String xml){
		return noticeService.getAllChannel();
	}
	
	
//	/*保存内容中的图片*/
//	@Override
//	public String saveFile(String xml){
//		return noticeService.saveFile();
//	}
//	
//	/*保存内容中的HTML文件*/
//	@Override
//	public String saveText(String xml){
//		return noticeService.saveText();
//	}
	
	@Override
	public String getInformationObjByid(String xml){
		return noticeService.getInformationObjByid();
	}
	
	@Override
	public String updateInformation(String xml){
		return noticeService.updateInformation();
	}
	
	@Override
	public String downloadInfoImage(String xml){
		return noticeService.downloadInfoImage();
	}
	
	@Override
	public String pulishSelectedInfors(String xml){
		return noticeService.pulishSelectedInfors();
	}
}

