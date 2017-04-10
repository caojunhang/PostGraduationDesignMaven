package cn.edu.ncut.istc.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.ncut.istc.common.Page;
import cn.edu.ncut.istc.common.PageModel;
import cn.edu.ncut.istc.model.KBookTrainObj;
import cn.edu.ncut.istc.model.STObj;
import cn.edu.ncut.istc.model.SeriseObj;
import cn.edu.ncut.istc.model.assistant.IntegeratedQueryObj;
import cn.edu.ncut.istc.model.assistant.ValObj;
import cn.edu.ncut.istc.model.assistant.WorkMapDtoObj;
import cn.edu.ncut.istc.model.view.VCountTrainObj;
import cn.edu.ncut.istc.model.view.WorkMapObj;
import cn.edu.ncut.istc.service.SystemService;
import cn.edu.ncut.istc.service.weixin.QueryService;

@Controller
@RequestMapping("/query")
public class QueryController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private QueryService queryService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getAuthorDetail() {
		return "/searchStatics/search";
	}

	@RequestMapping(value = "/list1", method = RequestMethod.GET)
	public String getAuthorDetail1() {
		return "/searchStatics/chart_list";
	}

	/*
	 * =========================================================================
	 * ====
	 */
	@RequestMapping(value = "/forecast1", method = RequestMethod.GET)
	public ModelAndView forecast1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		String y = request.getParameter("ms");

		String h1 = y + "选题预测分类比例推荐";
		String h2 = y + "选题预测分类比例推荐";

		result.put("cata", getCata(y));
		result.put("catadata", getCataData(y));
		result.put("catapiedata", getPieData(y));

		result.put("h1", h1);
		result.put("h2", h2);

		return new ModelAndView("/forecast/forecast1", result);
	}

	@RequestMapping(value = "/time", method = RequestMethod.GET)
	public ModelAndView time(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		String infoid = (String) request.getParameter("infoid");
		// UserinfoObj userinfoObj = queryService.findInfo(infoid);
		String[] as = infoid.split(" ");
		// 语言 900 32 平装 100万
		// 查询类别使用ISBN申领时间
		STObj stObj = null;
		List<STObj> stObjlist = queryService.getISBNTime(as[0]);
		if (stObjlist != null) {
			stObj = stObjlist.get(0);
			// Double isbnTime =
			// Double.valueOf(stObj.getIsbntime()).doubleValue();

			double isbnTime = stObj.getIsbntime().doubleValue();
			ValObj val = queryService.returnVal();
			// 计算每天可以印刷本书
			// 页数 开本 装订形式
			Double benshu = null;
			if (as[2].equals("32")) {
				double l1 = Double.valueOf(val.getYeshu32()).doubleValue();
				double l2 = Double.valueOf(as[1]).doubleValue();

				benshu = l2 / l1;
			} else if (as[2].equals("64")) {
				double l1 = Double.valueOf(val.getYeshu64()).doubleValue();
				double l2 = Double.valueOf(as[1]).doubleValue();

				benshu = l2 / l1;
			}

			Double zd = null;
			if (as[3].equals("平装")) {
				zd = Double.valueOf(val.getZhuangdingxingshi32()).doubleValue() / 3600;
			} else if (as[3].equals("精装")) {
				zd = Double.valueOf(val.getZhuangdingxingshi64()).doubleValue() / 3600;
			}
			// 印刷量

			double zsjper = benshu * zd;
			double yinshua = Long.valueOf(as[4]).longValue() * 1000000 * zsjper * 0.1 * 0.6;
			yinshua = Math.round(yinshua * 100 + 0.5) / 100.0;
			int isbnTimeint = (int) isbnTime;
			int yinshuaint = (int) yinshua;
			int tianshuint = yinshuaint + isbnTimeint;
			result.put("cata", as[0]);
			result.put("isbntime", Integer.toString(isbnTimeint));
			result.put("yinshua", yinshuaint);
			result.put("tianshu", tianshuint);
		}
		else
		{
			result.put("cata", "输入的图书类别不存在或有误！请核对后重新输入。");
		}
		return new ModelAndView("/forecast/time", result);
	}
	/*
	 * =========================================================================
	 * ====
	 */
	private String getPieData(String y) {
		String year = y;
		String cata = "";
		List<SeriseObj> SeriseList = queryService.getForecastRank(year);
		for (SeriseObj seriseObj : SeriseList) {
			cata += "{value:" + seriseObj.getMarketshare() + ",name:'" + seriseObj.getCata() + "'},";
		}
		cata = cata.substring(0, cata.length() - 1);
		return cata;
	}

	private Object getCataData(String y) {
		String year = y;
		String cata = "";
		List<SeriseObj> SeriseList = queryService.getForecastRank(year);
		for (SeriseObj seriseObj : SeriseList) {
			cata += "\"" + seriseObj.getMarketshare() + "\",";
		}
		cata = cata.substring(1, cata.length() - 2);
		return cata;
	}

	private String getCata(String y) {
		// 类别
		String year = y;
		String cata = "";
		List<SeriseObj> SeriseList = queryService.getForecastRank(year);
		for (SeriseObj seriseObj : SeriseList) {
			cata += "\"" + seriseObj.getCata() + "\",";
		}
		cata = cata.substring(1, cata.length() - 2);
		return cata;
	}

	@RequestMapping(value = "/integeratequery", method = RequestMethod.GET)
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute IntegeratedQueryObj integeratedQueryObj) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String, Object> result = new HashMap<String, Object>();
		String pageNumbers = request.getParameter("pageNumber");
		String pageSizes = request.getParameter("pageSize");
		String orderby = request.getParameter("sortColumns");

		if (null == integeratedQueryObj.getStarttime() || integeratedQueryObj.getEndtime() == null) {

			integeratedQueryObj.setStarttime(df.parse(request.getParameter("starttime")));
			integeratedQueryObj.setEndtime(df.parse(request.getParameter("endtime")));
		}

		PageModel pagemodel = processPageModel(pageNumbers, pageSizes);

		List<VCountTrainObj> productObjList = systemService.integerateQuery(pagemodel.getFirstResultNumber(),
				pagemodel.getPageSize(), integeratedQueryObj, orderby);

		Page page = processPage(pageNumbers, pageSizes, integeratedQueryObj);

		result.put("productObjList", productObjList);
		result.put("integeratedQueryObj", integeratedQueryObj);

		result.put("starttime", df.format(integeratedQueryObj.getStarttime()));
		result.put("endtime", df.format(integeratedQueryObj.getEndtime()));
		result.put("page", page);
		// TODO
		return new ModelAndView("/searchStatics/search-show", result);
	}

	private Page processPage(String pageNumbers, String pageSizes, IntegeratedQueryObj integeratedQueryObj) {
		Page page = new Page();
		if (pageNumbers == null || pageNumbers.equals("")) {
			pageNumbers = "1";
		}
		if (pageSizes == null || pageSizes.equals("")) {
			pageSizes = "15";
		}
		int pageNumber = Integer.parseInt(pageNumbers);
		int pageSize = Integer.parseInt(pageSizes);
		page.pageNumber = pageNumber;
		page.pageSize = pageSize;
		page.totalCount = systemService.integerateQueryCount(integeratedQueryObj);
		return page;
	}

	private PageModel processPageModel(String pageNumbers, String pageSizes) {
		PageModel page = new PageModel();
		if (pageNumbers == null || pageNumbers.equals("")) {
			pageNumbers = "1";
		}
		if (pageSizes == null || pageSizes.equals("")) {
			pageSizes = "15";
		}
		page.setFirstResultNumber(
				page.getfirstResultNumber(Integer.parseInt(pageNumbers), Integer.parseInt(pageSizes)));
		page.setPageSize(Integer.parseInt(pageSizes));

		return page;
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public ModelAndView statistics(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute IntegeratedQueryObj integeratedQueryObj) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String pageNumbers = request.getParameter("pageNumber");
		String pageSizes = request.getParameter("pageSize");
		String orderby = request.getParameter("orderby");

		if (null == integeratedQueryObj.getStarttime() || integeratedQueryObj.getEndtime() == null) {

			integeratedQueryObj.setStarttime(df.parse(request.getParameter("starttime")));
			integeratedQueryObj.setEndtime(df.parse(request.getParameter("endtime")));
		}

		PageModel pagemodel = processPageModel(pageNumbers, pageSizes);

		// TODO
		return new ModelAndView("", result);
	}
	
	
	/**
	 * lh 2016-10-20
	 * 
	 * @return 返回前端需要的Json格式，显示Map 分别是查重不通过，敏感词不通过，已发码
	 */
	@RequestMapping(value = "/showRegionMap", method = RequestMethod.GET) //
	public ModelAndView getMapList() {
		HashMap<String, String> result = new HashMap<String, String>();

		// List<WorkMapObj> checkList = WorkMapDtoObj.getMapList("查重不通过");
		// List<WorkMapObj> sensitiveList =
		// systemService.getMapList("敏感词过滤不通过");
		// List<WorkMapObj> hascodeList = systemService.getMapList("已配码");
		//
		// result.put("check", returnString(checkList));
		// result.put("sensitiv", returnString(sensitiveList));
		// result.put("hascode", returnString(hascodeList));

		WorkMapDtoObj workMapDtoObj = systemService.getMapDto();
		result.put("check", returnString(workMapDtoObj.getCheckList()));
		result.put("sensitiv", returnString(workMapDtoObj.getSensitiveList()));
		result.put("hascode", returnString(workMapDtoObj.getHascodeList()));

		return new ModelAndView("searchStatics/region-map", result);
	}

	private String returnString(List<WorkMapObj> list) {
		Map<String, Integer> cnMap = structureCNMap();
		Map<String, Integer> dbMap = new HashMap<String, Integer>();

		for (WorkMapObj workMapObj : list) {
			dbMap.put(workMapObj.getLocationpublishers(), workMapObj.getBooksum().intValue());
		}
		cnMap.putAll(dbMap);
		String returnString = dealStringMap(cnMap);

		return returnString;
	}
	
	@RequestMapping(value = "/regionProductQuery")
	public ModelAndView regionProductQuery(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute IntegeratedQueryObj integeratedQueryObj) throws Exception {
		IntegeratedQueryObj tmp=new IntegeratedQueryObj();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String, Object> result = new HashMap<String, Object>();
		String pageNumbers = request.getParameter("pageNumber");
		String pageSizes = request.getParameter("pageSize");
		String orderby = request.getParameter("sortColumns");
		String regionname = request.getParameter("regionname");	
		String tlinfo = request.getParameter("tlinfo");
		if(tlinfo!=null)
		{
			tlinfo="("+tlinfo+")";
			if(tlinfo.contains("newbook")||tlinfo.contains("reprint"))
				integeratedQueryObj.setIstc("flagnoistc");
		}
		integeratedQueryObj.setProductstatus(tlinfo);
		//将tlinfo 按+号分割，得出数组，然后传值查询即可 
//		String tlArray = tlinfo.split("+");  
		if (null != regionname) {
			integeratedQueryObj.setRegionname(regionname);
		}
		PageModel pagemodel = processPageModel(pageNumbers, pageSizes);		
		List<VCountTrainObj> productObjList = systemService.integerateQuery(pagemodel.getFirstResultNumber(),
				pagemodel.getPageSize(), integeratedQueryObj, orderby);	
		Page page = processPage(pageNumbers, pageSizes, integeratedQueryObj);
		result.put("regionname", integeratedQueryObj.getRegionname());
		result.put("productObjList", productObjList);
		result.put("page", page);
		if(tlinfo.length()>1)
			result.put("tlinfo", tlinfo.substring(1, tlinfo.length()-1));
		
		return new ModelAndView("/searchStatics/regionproduct-show", result);
	}
	
	
	
	private Map<String, Integer> structureCNMap() {
		Map<String, Integer> cnMap = new HashMap<String, Integer>();
		cnMap.put("北京", 0);
		cnMap.put("天津", 0);
		cnMap.put("上海", 0);
		cnMap.put("重庆", 0);
		cnMap.put("河北", 0);
		cnMap.put("河南", 0);
		cnMap.put("云南", 0);
		cnMap.put("辽宁", 0);
		cnMap.put("黑龙江", 0);
		cnMap.put("湖南", 0);
		cnMap.put("安徽", 0);
		cnMap.put("山东", 0);
		cnMap.put("新疆", 0);
		cnMap.put("江苏", 0);
		cnMap.put("浙江", 0);
		cnMap.put("江西", 0);
		cnMap.put("湖北", 0);
		cnMap.put("广西", 0);
		cnMap.put("甘肃", 0);
		cnMap.put("山西", 0);
		cnMap.put("内蒙古", 0);
		cnMap.put("陕西", 0);
		cnMap.put("吉林", 0);
		cnMap.put("福建", 0);
		cnMap.put("贵州", 0);
		cnMap.put("广东", 0);
		cnMap.put("青海", 0);
		cnMap.put("西藏", 0);
		cnMap.put("四川", 0);
		cnMap.put("宁夏", 0);
		cnMap.put("海南", 0);
		cnMap.put("台湾", 0);
		cnMap.put("香港", 0);
		cnMap.put("澳门", 0);
		return cnMap;
	}

	private String dealStringMap(Map<String, Integer> cnMap) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String s : cnMap.keySet()) {
			stringBuilder.append("{name: '");
			stringBuilder.append(s);
			stringBuilder.append("',value:");
			stringBuilder.append(cnMap.get(s));
			stringBuilder.append("},");
		}
		String string = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
		return string;
	}
	
}
