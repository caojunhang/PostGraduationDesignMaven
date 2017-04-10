package cn.edu.ncut.istc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.ncut.istc.service.NoticeService;
import cn.edu.ncut.istc.common.Page;
import cn.edu.ncut.istc.model.ChannelObj;
import cn.edu.ncut.istc.model.InformationObj;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.service.SystemService;
import cn.edu.ncut.istc.service.TestService;

@Controller
@RequestMapping("/information")

public class InformationController {
	@Autowired
	private NoticeService noticeservice;
	/**
	 * 跳转首页
	 * ***/
	@RequestMapping(value="/webmain_showdata.do", method=RequestMethod.GET)
	 public ModelAndView index_showData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<InformationObj>informationList=noticeservice.queryInformationList();
		ArrayList<String> contentList = new ArrayList<String>();//存放中心动态的新闻内容
		ArrayList<String> picNewsList=new ArrayList<String>();
		int informationCount = 0;
		int picinformationCount = 0;
		for(InformationObj information :informationList ){			
			if(information.getInftype().equals(BigDecimal.valueOf(11)) && informationCount < 6){
				String content=noticeservice.readText(information.getInfid().toString());
				 content=clearWordFormat(content);
	                //去除内容中的HTML标签
	            content=delHTMLTag(content);
				contentList.add(content);	
				informationCount++;
			}
			if(information.getInftype().equals(BigDecimal.valueOf(10)) && picinformationCount < 4){
				if(information.getInfstatus().equals(BigDecimal.valueOf(22))){
					if(information.getIspic() != null && information.getIspic().equals("1")){
						String content=noticeservice.readText(information.getInfid().toString());
						 content=clearWordFormat(content);
			                //去除内容中的HTML标签
			                content=delHTMLTag(content);
			                picNewsList.add(content);
			                picinformationCount++;
					}
				}
			}
			
			if(informationCount==6 && picinformationCount == 4) break;
		}
		
		
		HashMap result = new HashMap();
		result.put("informations", informationList);
		result.put("informationCount", 6);
		result.put("picinformationCount", 4);
		result.put("contentList",contentList);
		result.put("picNewsList", picNewsList);
		return new ModelAndView("../../index", result);
		
	}
	
	
	
	/**
	 * 跳转微信首页
	 * ***/
	@RequestMapping(value="/weixinmain_showdata.do", method=RequestMethod.GET)
	 public ModelAndView index_weixin_showData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<InformationObj>informationList=noticeservice.queryInformationList();
		ArrayList<String> contentList = new ArrayList<String>();//存放中心动态的新闻内容
		ArrayList<String> picNewsList=new ArrayList<String>();
		int informationCount = 0;
		int picinformationCount = 0;
		for(InformationObj information :informationList ){			
			if(information.getInftype().equals(BigDecimal.valueOf(11)) && informationCount < 6){
				String content=noticeservice.readText(information.getInfid().toString());
				 content=clearWordFormat(content);
	                //去除内容中的HTML标签
	            content=delHTMLTag(content);
				contentList.add(content);	
				informationCount++;
			}
			if(information.getInftype().equals(BigDecimal.valueOf(10)) && picinformationCount < 4){
				if(information.getInfstatus().equals(BigDecimal.valueOf(22))){
					if(information.getIspic() != null && information.getIspic().equals("1")){
						String content=noticeservice.readText(information.getInfid().toString());
						 content=clearWordFormat(content);
			                //去除内容中的HTML标签
			                content=delHTMLTag(content);
			                picNewsList.add(content);
			                picinformationCount++;
					}
				}
			}
			
			if(informationCount==6 && picinformationCount == 4) break;
		}
		
		
		HashMap result = new HashMap();
		result.put("informations", informationList);
		result.put("informationCount", 6);
		result.put("picinformationCount", 4);
		result.put("contentList",contentList);
		result.put("picNewsList", picNewsList);
		return new ModelAndView("../../indexWechat", result);
		
	}
	
	
	
	
	
	/**
	 * 显示信息页面
	 * **/
	@RequestMapping(value="/webcontent_showdata.do", method=RequestMethod.GET)
	 public ModelAndView content_showData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String where = "CSHOWTYPE in (1,2,3)";
       List<ChannelObj> channels = noticeservice.getAllChannelList(where);
       List<InformationObj> informations = noticeservice.getAllinformations();
       HashMap result = new HashMap();
       result.put("channels", channels);
       result.put("informations", informations);
       result.put("informationCount", 15);
       String type= request.getParameter("type");
       if("video".equals(type)) { 
           result.put("type","video");
           return new ModelAndView("/information/webContent", result);
       }
       else
       {

           Long id = new Long(request.getParameter("infid"));
           InformationObj information = noticeservice.getInformationById(new BigDecimal(id));

           Long cid=information.getInftype().longValue();
           if(cid<=3&&cid>=1){
               Long pid=information.getPid().longValue();
               ProductObj product=noticeservice.getproductById(pid);
               result.put("product",product);
               return new ModelAndView("/information/webproductdetail", result);
               
           }
           else{

           	String content=noticeservice.readText(information.getInfid().toString());
               result.put("type","common");
               result.put("information",information);
               result.put("content",content);
               return new ModelAndView("/information/webContent", result);	
           }
       }

   }	
   /**
    * ISTC二级页面显示更多信息
    * **/    
	 @RequestMapping(value="webmore_showdata.do",method=RequestMethod.GET)
	    public ModelAndView webmore_showdata(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    	String pageNumbers= request.getParameter("pageNumber");
	    	String  pageSizes= request.getParameter("pageSize");
	    	if(pageNumbers == null||pageNumbers.equals("")){
	    		pageNumbers = "1";
	    	}
	    	if(pageSizes == null||pageSizes.equals("")){
	    		pageSizes = "15";
	    	}
	    	int pageNumber = Integer.parseInt(pageNumbers);
	    	int pageSize = Integer.parseInt(pageSizes);
	    	Page page = new Page();
	    	
	    	List<InformationObj> informations = null;
	    	List<InformationObj> informationsList = null;
	    	String where = "CSHOWTYPE in (1,2,3)";
	        List<ChannelObj> channels = noticeservice.getAllChannelList(where);
	        
	        HashMap<String,Object> result = new HashMap<String,Object>() ;
	        result.put("channels", channels);
	        result.put("informationCount", "15");
	        Long id=new Long(request.getParameter("cid"));
	        String temp=request.getParameter("type");
	        
	        int firstResultNum = page.getfirstResultNumber(pageNumber, pageSize);
	        int lastResultNum = page.getlastResultNumber(pageNumber, pageSize);
	        
	        if("video".equals(temp)) {
	        	 ChannelObj channel = noticeservice.getChannelByChannelid(id);
	             informations = noticeservice.getAllinformations();
	             result.put("informations", informations);
	            return new ModelAndView("/information/webMore",result);
	        }else{      
	        	
	        informationsList = noticeservice.getistcinformationsList(id,firstResultNum,lastResultNum);
	        ChannelObj channel = noticeservice.getChannelByChannelid(id);
	        informations = noticeservice.getAllinformations();
	        page.pageNumber = pageNumber;
	        page.pageSize = pageSize;
	        page.totalCount = noticeservice.getinformationsListCount(id);
	        result.put("informations", informations);
	        result.put("channel1",channel);
	        result.put("informationsList",informationsList);
	        result.put("type","common");
	        result.put("inftype",id);
	        result.put("page",page);
	    	return new ModelAndView("/information/webMore",result);
	        }

	    }
	 /*
    取出文章内容原有的样式
    */
    public static String clearWordFormat(String content) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(content);
        content = m.replaceAll("");
        //把<P></P>转换成</div></div>保留样式
        content = content.replaceAll("(<p)([^>]*>.*?)(<\\/p>)", "<div$2</div>");
        content = content.replaceAll("(<strong)([^>]*>.*?)(<\\/strong>)", "<div$2</div>");
        content = content.replaceAll("(<h3)([^>]*>.*?)(<\\/h3>)", "<div$2</div>");
        content.replaceAll("/s" , "");

        //把<P></P>转换成</div></div>并删除样式
        //content = content.replaceAll("(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>");
        //删除不需要的标签
        content = content.replaceAll("<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>", "");
        //删除不需要的属性
        content = content.replaceAll("<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>", "<$1$2>");
        //删除<STYLE TYPE="text/css"></STYLE>及之间的内容

        return content;
    }

    public String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";//定义script的正则表达式
		String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>";	//定义style的正则表达式
		String regEx_html="<[^>]+>";//定义HTML标签的正则表达式

		//htmlStr=htmlStr.replace("<br />", "\n");

		Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
		Matcher m_script=p_script.matcher(htmlStr);
		htmlStr=m_script.replaceAll("");//过滤script标签

		Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
		Matcher m_style=p_style.matcher(htmlStr);
		htmlStr=m_style.replaceAll("");	//过滤style标签

		Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
		Matcher m_html=p_html.matcher(htmlStr);
		htmlStr=m_html.replaceAll("");	//过滤html标签

		htmlStr=htmlStr.replace("&nbsp;", " ");
		htmlStr=htmlStr.replace("&ldquo;", "\"");
		htmlStr=htmlStr.replace("&rdquo;", "\"");
        return "&nbsp;&nbsp;"+htmlStr.trim();
		//return htmlStr.trim();
    }
}
