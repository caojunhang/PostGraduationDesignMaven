package cn.edu.ncut.istc.service;

import cn.edu.ncut.istc.common.plugin.SensitivewordFilter;
import cn.edu.ncut.istc.common.util.FileUtils;
import cn.edu.ncut.istc.dao.ProductDao;
import cn.edu.ncut.istc.dao.mongo.ProductMongoDao;
import cn.edu.ncut.istc.model.ProductMongo;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.assistant.RepeatObj;
import cn.edu.ncut.istc.model.assistant.SensitiveObj;
import cn.edu.ncut.istc.model.base.BaseModel;
import cn.edu.ncut.istc.service.base.BaseServiceImpl;
import cn.edu.ncut.istc.webservice.base.ObjectResult;
import cn.edu.ncut.istc.webservice.base.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
@Service("auditService")
public class AuditServiceImpl extends BaseServiceImpl<Object> implements AuditService{

	private final static Logger logger = Logger.getLogger(AuditServiceImpl.class);

	private static final float REPETETHRESHOLD = 0.1F; // 0.2基本就是完全重复了 计算公式: textScore / wordCount
	private static final String CONTENTREGEX = "，|。| |；|、|“|！|,|？|！";// 切割文本的正则表达式

	public final static String FAILRECHECK = "100010";	          //查重不通过	
	public final static String FAILRETRIEVAL = "100020";	      //全文检索不通过	
	public final static String FAILFILTER = "100025";	      	  //敏感词过滤不通过	
	public final static String DISTRIBUTION  = "100030";	      //已配码	
	public final static String REVISE = "100040";  		          //作品修订
	public final static String SUSPEND = "100050";   			  //作品暂停
	public final static String ABOLISH = "100060";  			  //作品废止
	 				
	
	@Autowired
	private ProductDao productDao;
/*	@Autowired
	private ProductMongoDao productMongoDao;*/
	@Override
	public String getProductWaitBarCode() {
		BaseModel result;
		result = getProductWaitBarCode(FAILRECHECK,FAILRETRIEVAL,FAILFILTER);
		return result.asXML(true);
	}
	
	private BaseModel getProductWaitBarCode(String... productstatus) {
		BaseModel result;
		try {
			int firstResult = Integer.parseInt(parameters.getParams().get(
					"firstResult"));
			int maxResult = Integer.parseInt(parameters.getParams().get(
					"maxResult"));
			//String publishids = parameters.getParams().get("orgid");

			//String fuzzy = parameters.getParams().get("fuzzy");
			Map<String, Object> params = new HashMap<String, Object>();

			String where = "(productstatus in (:productstatus)) ";
			String orderby = " ORDER BY "
					+ (String) parameters.getParams().get("orderby");

			
			params.put("productstatus", productstatus);
			result = productDao.getProductWaitAuditGrid(firstResult,
						maxResult, where, params, orderby, null);
		} catch (Exception e) {
			logger.error(e);
			Result res = new Result();
			res.setMessage("获取列表异常！");
			res.setType(1);
			result = res;
		}
		return result;
	}


	@Override
	public String getProductBarCode() {
		BaseModel result;
		result = getProductBarCode(DISTRIBUTION);
		return result.asXML(true);
	}

	/**
	 * 根据ProductId获取MongoDB中存储的作品样本
	 * 2016-4-28
	 * 李熙伟
	 * @return 重复作品ProductObj、两个作品的Product样本
     */
	@Override
	public String getProductAttachByProductId()
	{
		ObjectResult<RepeatObj> result = new ObjectResult<RepeatObj>();
		String productId = parameters.getParams().get("productid");
		RepeatObj repeatObj = new RepeatObj();
		try
		{
			if (productId != null && !"".equals(productId))
			{
				String productAttachText = getProductAttachText(productId);
				repeatObj.setSampleText(productAttachText);
				ProductObj product = productDao.find(Long.parseLong(productId));
				// 状态在文本查重之前的，需要先进行文本查重
				String repeatProductId = "";
				if (Integer.parseInt(product.getProductstatus()) < Integer.parseInt(FAILRETRIEVAL))
				{
					repeatProductId = getAttachRepeatIds(product).split("\\$")[0];// 得到重复率最高的作品
				}
				else
				{
					repeatProductId = product.getRepeatIds().split("\\$")[0];// 得到重复率最高的作品
				}
				if (repeatProductId == null || "".equals(repeatProductId))
				{
					result.setType(1);
					result.setMessage("当前作品没有重复的样本!#" + productId);
					return result.asXML(true);
				}
				String productRepeatAttachText = getProductAttachText(repeatProductId);
				repeatObj.setRepeatText(productRepeatAttachText);
				result.setType(0);
				result.setResultObject(repeatObj);
				result.setMessage("查询成功");
			}
			else
			{
				result.setType(1);
				result.setResultObject(repeatObj);
				result.setMessage("作品Id为空");
			}
			return result.asXML(true);
		}catch (Exception e)
		{
			result.setType(1);
			result.setResultObject(repeatObj);
			result.setMessage("读取作品样本失败:"+e.getMessage());
		}
		return result.asXML(true);
	}

	/**
	 * 根据ProductId进行敏感词过滤
	 * 2016-4-28
	 * 李熙伟
	 * @return 返回敏感词信息
     */
	@Override
	public String sensitiveWordFilterByProductId()
	{
		ObjectResult<SensitiveObj> result = new ObjectResult<SensitiveObj>();
		String productId = parameters.getParams().get("productid");
		SensitiveObj sensitiveObj = new SensitiveObj();
		try
		{
			if (productId != null && !"".equals(productId))
			{
				SensitivewordFilter filter = new SensitivewordFilter();
				sensitiveObj.setSensitiveWordAmount(String.valueOf(filter.getSensitiveWordMap().size()));
				String productAttachText = getProductAttachText(productId);
				sensitiveObj.setSampleText(productAttachText);
				sensitiveObj.setSampleWordAmount(String.valueOf(productAttachText.length()));
				Set<String> set = filter.getSensitiveWord(productAttachText, 1);
				sensitiveObj.setSampleSensitiveWordAmount(set.size() + "。包含:" + set);
				result.setType(0);
				result.setResultObject(sensitiveObj);
				result.setMessage("查询成功");
			}
			else
			{
				result.setType(1);
				result.setResultObject(sensitiveObj);
				result.setMessage("作品Id为空");
			}
		} catch (Exception e)
		{
			result.setType(1);
			result.setResultObject(sensitiveObj);
			result.setMessage("读取作品样本失败:" + e.getMessage());
		}
		return result.asXML(true);
	}

	private BaseModel getProductBarCode(String... productstatus) {
		BaseModel result;
		try {
			int firstResult = Integer.parseInt(parameters.getParams().get(
					"firstResult"));
			int maxResult = Integer.parseInt(parameters.getParams().get(
					"maxResult"));
			//String publishids = parameters.getParams().get("orgid");

			//String fuzzy = parameters.getParams().get("fuzzy");
			Map<String, Object> params = new HashMap<String, Object>();

			String where = "(productstatus in (:productstatus)) ";
			String orderby = " ORDER BY "
					+ (String) parameters.getParams().get("orderby");

			
			params.put("productstatus", productstatus);
			result = productDao.getProductAuditGrid(firstResult,
						maxResult, where, params, orderby, null);
		} catch (Exception e) {
			logger.error(e);
			Result res = new Result();
			res.setMessage("获取列表异常！");
			res.setType(1);
			result = res;
		}
		return result;
	}
	
	/**
	 * 读取product信息
	 * @author wc
	 * 2016年4月18日
	 */
	public String getProductObjByProductid() {
		// TODO Auto-generated method stub
		String productIdStr = parameters.getParams().get("productid");
		if(productIdStr!=null && !"".equals(productIdStr)){
			ObjectResult<ProductObj> result = new ObjectResult<ProductObj>();
			try {
				ProductObj obj = productDao.find(Long.parseLong(productIdStr));
				result.setResultObject(obj);
				result.setType(0);
				result.setMessage(obj.getTbUser().getUserid().toString());
			} catch (Exception e) {
				result.setType(1);
				result.setMessage("根据id获取作品对象失败！");
			}
			return result.asXML(true);
		}
		return null;
	}
	private String getProductAttachText(String productId)
	{
		ProductObj product = productDao.find(Long.parseLong(productId));
		String filepath = product.getProducturl();
		return FileUtils.readFileByLines(filepath);
	}

	/**
	 * 如果作品没有进行过样本查重，即状态值小于“文本查重不通过”，就需要补充文本查重流程
	 * @param product
	 * @return 如果有重复则返回ProductId列表，没有返回""
	 * @throws Exception
     */	
	private String getAttachRepeatIds(ProductObj product) 
	{
		// 设置Mongo的Collection名
		String collectionName = "product";
		String repeatIds = "";
		String repeatPercent = "";
		// 获取附件文本
		String filePath = product.getProducturl();
		String content = FileUtils.readFileByLines(filePath);
//		List<ProductMongo> productMongos = productMongoDao.getMostFamiliarProduct(content, 100, 10, collectionName);//maxScore暂时没用
//		for (ProductMongo productMongo : productMongos)
//		{
//			// 按照设定的阀值来决定是否重复
//			float textScore = productMongo.getScore();// 文本查重得到的重复分数
//			int wordCount = productMongo.getContent().split(CONTENTREGEX).length;// 单词数
//			float productThreshold = textScore / wordCount;
//			System.out.println("分数:" + textScore + ",单词数:" + wordCount + ",值:" + productThreshold);
//			if (productThreshold >= REPETETHRESHOLD)
//			{
//				repeatIds += productMongo.getProductId();
//				repeatIds += "$";
//				repeatPercent += productMongo.getScore();
//				repeatPercent += "$";
//			}
//		}
		if (!repeatIds.equals(""))
		{
			product.setRepeatIds(repeatIds);
			product.setRepeatPercent(repeatPercent);
			productDao.update(product);
		}
		return repeatIds;
	}
}
