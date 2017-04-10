package cn.edu.ncut.istc.service;

import cn.edu.ncut.istc.common.plugin.ConfigSingleton;
import cn.edu.ncut.istc.common.plugin.SensitivewordFilter;
import cn.edu.ncut.istc.dao.*;
import cn.edu.ncut.istc.dao.mongo.ProductMongoDao;
import cn.edu.ncut.istc.model.*;
import cn.edu.ncut.istc.model.plugin.AuthorPrototype;
import cn.edu.ncut.istc.model.plugin.HttpFile;
import cn.edu.ncut.istc.model.plugin.ISTCPrototype;
import cn.edu.ncut.istc.service.base.BaseServiceImpl;
import cn.edu.ncut.istc.service.web.AuthorOrgService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service("applyService")
public class ApplyServiceImpl extends BaseServiceImpl<Object> implements ApplyService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserDao userDao;
	// @Autowired
	// private ProductMongoDao productMongoDao;
	@Autowired
	private OrganizationinfoDao organizationinfoDao;
	@Autowired
	private AuthorDao authorDao;
	@Autowired
	private AuthorOrgDao authorOrgDao;
	@Autowired
	private InformationDao informationDao;
	@Autowired
	private AuthorOrgService authorOrgService;
	@Autowired
	private AuditDao auditDao;
	private static final String FILECHARSET = "UTF-8";// 文本文件编码格式
	private static final float REPETETHRESHOLD = 0.1F; // 0.2基本就是完全重复了 计算公式:
														// textScore / wordCount
	private static final String CONTENTREGEX = "，|。| |；|、|“|！|,|？|！";// 切割文本的正则表达式

	public final static String FAILRECHECK = "100010"; // 查重不通过
	public final static String FAILRETRIEVAL = "100020"; // 全文检索不通过
	public final static String FAILFILTER = "100025"; // 敏感词过滤不通过
	public final static String DISTRIBUTION = "100030"; // 已配码
	public final static String REVISE = "100040"; // 作品修订
	public final static String SUSPEND = "100050"; // 作品暂停
	public final static String ABOLISH = "100060"; // 作品废止
	public final static String PUSHTOMONGO = "100070"; // 样本推送Mongo失败

	/**
	 * 接口调用查重、存储等方法
	 *
	 * @param prototype
	 * @return
	 */
	@Override
	public String checkText(ISTCPrototype prototype) {
		String resMsg = "SUCCEED";
		String collectionName = "product";
		Map<String, Object> params = new HashMap<String, Object>();
		String userLoginName = prototype.getUserLoginName();// 提取用户登录名
		// 检查是否存在相应的用户
		params.put("LOGINNAME", userLoginName);
		params.put("USERSTATUS", "1");
		List<UserObj> userList = userDao.findAll("LOGINNAME = :LOGINNAME AND USERSTATUS = :USERSTATUS", params, null);
		if (userList.size() != 1)
			return "Failed:查找到用户个数为 " + userList.size() + " 个";

		// 检查是否存在对应的作者
		String authorUniqueId = prototype.getAuthorUniqueId();
		params.clear();
		params.put("UNIQUEID", authorUniqueId);
		List<AuthororgnewObj> authors = authorOrgDao.findAll("UNIQUEID= :UNIQUEID", params, null);
		if (authors.size() != 1)
			return "Failed:查找到作者个数为 " + authors.size() + " 个";

		// 抽取ProductObj
		ProductObj product = new ProductObj();
		resMsg = extractProduct(prototype, product);
		if (!resMsg.contains("SUCCEED"))
			return resMsg;

		// 客户作品唯一ID查重（防止网络问题重复推送数据）
		String uniqueId = product.getUniqueid();
		if (productResend(uniqueId)) {
			return "Failed:作品重复提交:UniqueId = " + uniqueId;
		}
		try {
			if (productIsRepeat(product))// 关键字查重
			{
				product.setProductstatus(FAILRECHECK);
				resMsg = "关键字查重未通过";
			} else if (attachmentIsRepeat(prototype, product))// 关键字不重复，进行样本查重
			{
				product.setProductstatus(FAILRETRIEVAL);
				resMsg = "样本查重未通过";
			} else if (containUglyWord(prototype))// 关键字、样本查重通过，进行敏感词过滤
			{
				product.setProductstatus(FAILFILTER);
				resMsg = "敏感词过滤未通过";
			}
		} catch (Exception e) {
			return "Failed:查重出现异常," + e.getMessage();
		}
		// 存储文件到本地
		try {
			if (prototype.getFile() == null)
				return "Failed:没有上传附件";
			String filePath = saveFilesToLocal(prototype.getFile());
			if (filePath != null && !filePath.equals(""))
				product.setProducturl(filePath);
			else
				return "Failed:文件存储出现异常";
		} catch (Exception e) {
			return "Failed:文件存储出现异常," + e.getMessage();
		}
		// 发码
		if (product.getProductstatus() == null || product.getProductstatus().equals("")) // 如果作品状态是空，那就证明查重都通过了
		{
			resMsg = getIstcCode(prototype, product);
			if (resMsg.contains("SUCCEED"))
				product.setProductstatus(DISTRIBUTION);
		}

		// 保存结构化数据
		productDao.save(product);
		// 保存流转记录
		AuditObj auditObj = new AuditObj();
		auditObj.setProduct(product);
		Map<String, String> statusMap = new HashMap<String, String>();

		statusMap.put(FAILRECHECK, "查重不通过");
		statusMap.put(FAILRETRIEVAL, "全文检索不通过");
		statusMap.put(FAILFILTER, "敏感词过滤不通过");
		statusMap.put(DISTRIBUTION, "已配码");
		statusMap.put(REVISE, "作品修订");
		statusMap.put(SUSPEND, "作品暂停");
		statusMap.put(ABOLISH, "作品废止");
		statusMap.put(PUSHTOMONGO, "样本推送Mongo失败");
		auditObj.setOpinion(statusMap.get(product.getProductstatus()));

		auditObj.setAdate(new Date());
		if (product.getProductstatus().equals(DISTRIBUTION))
			auditObj.setAstatus(new BigDecimal(1));
		else
			auditObj.setAstatus(new BigDecimal(41));

		auditDao.save(auditObj);
		// 保存Information表
		if (product.getProductstatus().equals(DISTRIBUTION)) {
			InformationObj infomation = new InformationObj();
			infomation.setInfname("作品:" + product.getProductname() + ",已发码");
			infomation.setInftype(new BigDecimal("2"));
			infomation.setInfdate(product.getCreatetime());
			infomation.setInfstatus(new BigDecimal("22"));
			infomation.setMemo(product.getAuthor());
			infomation.setIspic("0");

			infomation.setPid(new BigDecimal(product.getProductid().toString()));
			informationDao.save(infomation);
			String pathname = ConfigSingleton.getInstance().getProperties().getProperty("cn.edu.ncut.istc.checkdciurl");
			resMsg = "SUCCEED#ISTC码:" + product.getIstc() + "#网站公示:" + pathname + infomation.getInfid()
					+ "&type=%27common%27&";
		}
		// 保存样本文本到Mongo
		// 创建ProductMongo对象，存储到Mongo
		// 只有所有查重通过的情况下,并且已经成功发码,才进行MongoDB的存储
		if (!resMsg.contains("SUCCEED"))
			return resMsg;
		try {
			ProductMongo productMongo = new ProductMongo();
			productMongo.setProductId(Integer.parseInt(product.getProductid().toString()));
			productMongo.setContent(transHttpFileToString(prototype.getFile().getContent()));
			// productMongoDao.insert(productMongo, collectionName);
		} catch (Exception e) {
			resMsg = "Failed:保存附件解析文本到Mongo异常," + e.getMessage();
			product.setProductstatus(PUSHTOMONGO);
		}
		return resMsg;

	}

	/**
	 * 作者登记
	 *
	 * @param prototype
	 * @return
	 */
	@Override
	public String checkAuthor(AuthorPrototype prototype) {
		// 1.校验
		String uniqueId = prototype.getUniqueid();
		if (uniqueId == null)
			return "Failed:作者唯一标识uniqueId为空!";
		List<AuthororgnewObj> authorOrgList = authorOrgDao.findAll("UNIQUEID = '" + uniqueId + "' ", null, null);
		if (authorOrgList.size() != 0)
			return "Failed:该作者已登记,个数为 " + authorOrgList.size() + " 个";

		Map<String, Object> params = new HashMap<String, Object>();
		String authorRealName = prototype.getAuthorrealname();
		String idcard = prototype.getIdcradno();
		params.clear();
		params.put("authorrealname", authorRealName);
		params.put("idcardno", idcard);
		List<AuthornewObj> authorList = authorDao.findAll(" authorrealname = :authorrealname and idcardno = :idcardno ",
				params, null);
		if (authorList.size() > 0) {
			// 只存副表
			AuthornewObj authorObj = authorList.get(0);
			AuthororgnewObj authorOrgObj = new AuthororgnewObj();
			authorOrgObj.setAuthorstatus(new BigDecimal(1));
			authorOrgObj.setPenname(prototype.getPenname());
			authorOrgObj.setSignwebsite(prototype.getSignwebsite());
			// 查询签约网站的id
			params.clear();
			params.put("signwebsite", prototype.getSignwebsite());
			List<OrganizationinfoObj> orgList = organizationinfoDao.findAll(" orgfullname = :signwebsite", params,
					null);
			if (orgList.size() <= 0) {
				return "Faild:网站信息有误,没有查询到对应的网站名称";
			}
			OrganizationinfoObj org = orgList.get(0);
			authorOrgObj.setSignwebsiteid(org.getOrgid());
			authorOrgObj.setSigntime(prototype.getSigntime());
			authorOrgObj.setAuthordesc(prototype.getAuthordesc());
			authorOrgObj.setRemark(prototype.getRemark());
			authorOrgObj.setTbAuthornew(authorObj);
			authorOrgObj.setUniqueid(prototype.getUniqueid());
			authorOrgDao.save(authorOrgObj);
		} else {
			// 主表副表都存
			AuthornewObj authornewObj = new AuthornewObj();
			authornewObj.setAuthorrealname(prototype.getAuthorrealname());
			authornewObj.setIdcardno(prototype.getIdcradno());
			authorDao.save(authornewObj);
			AuthororgnewObj orgObj = new AuthororgnewObj();
			orgObj.setAuthorstatus(new BigDecimal(1));
			orgObj.setPenname(prototype.getPenname());
			orgObj.setSignwebsite(prototype.getSignwebsite());
			// 查询签约网站的id
			params.clear();
			params.put("signwebsite", prototype.getSignwebsite());
			List<OrganizationinfoObj> orgList = organizationinfoDao.findAll(" orgfullname = :signwebsite", params,
					null);
			if (orgList.size() <= 0) {
				return "Faild:网站信息有误,没有查询到对应的网站名称";
			}
			OrganizationinfoObj org = orgList.get(0);
			orgObj.setSignwebsiteid(org.getOrgid());
			orgObj.setSigntime(prototype.getSigntime());
			orgObj.setAuthordesc(prototype.getAuthordesc());
			orgObj.setRemark(prototype.getRemark());
			orgObj.setTbAuthornew(authornewObj);
			orgObj.setUniqueid(prototype.getUniqueid());
			authorOrgDao.save(orgObj);
		}
		return "SUCCEED";
	}

	/**
	 * 作品查重，如果重复返回true，反之返回false
	 *
	 * @param productObj
	 * @return
	 */
	public Boolean productIsRepeat(ProductObj productObj) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("PRODUCTNAME", productObj.getProductname());
		params.put("AUTHOR", productObj.getAuthor());
		// params.put("CHAPTERNO",productObj.getChapterno());
		// params.put("SMALLMATTERNO",productObj.getSmallmatterno());

		String where = "PRODUCTNAME=:PRODUCTNAME and AUTHOR=:AUTHOR ";

		List<ProductObj> productList = productDao.findAll(where, params, null);
		if (productList.size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 作品是否被重复推送，依据作品的UniqueId
	 *
	 * @param productUniqueId
	 *            客户作品唯一标识
	 * @return 重复返回true，否则返回false
	 */
	private Boolean productResend(String productUniqueId) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("UNIQUEID", productUniqueId);
		String where = "UNIQUEID=:UNIQUEID ";

		List<ProductObj> productList = productDao.findAll(where, params, null);
		if (productList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 作品附件是否包含敏感词
	 *
	 * @param prototype
	 * @return 包含敏感词返回true, 否则返回false
	 */
	private Boolean containUglyWord(ISTCPrototype prototype) throws Exception {
		SensitivewordFilter filter = new SensitivewordFilter();
		System.out.println("敏感词的数量：" + filter.getSensitiveWordMap().size());
		byte[] base64content = prototype.getFile().getContent();
		String content = new String(Base64.decodeBase64(base64content), FILECHARSET);
		System.out.println("待检测语句字数：" + content.length());
		long beginTime = System.currentTimeMillis();
		Set<String> set = filter.getSensitiveWord(content, 1);
		long endTime = System.currentTimeMillis();
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		// string = filter.replaceSensitiveWord(string, minMatchTYpe,"*");
		// System.out.println("过滤后的语句："+string);
		System.out.println("耗时 " + (endTime - beginTime) / 1000.0);
		if (set.size() == 0)
			return false;
		return true;
	}

	/**
	 * 使用MongoDB进行附件查重 Tip: 在关键字查重之后再进行样本查重
	 *
	 * @param product
	 * @return 重复返回true，否则返回false
	 */
	private Boolean attachmentIsRepeat(ISTCPrototype prototype, ProductObj product) throws Exception {
		// 设置Mongo的Collection名
		String collectionName = "product";
		String repeatIds = "";
		String repeatPercent = "";
		// 将Base64文本解析
		byte[] base64content = prototype.getFile().getContent();
		String content = new String(Base64.decodeBase64(base64content), FILECHARSET);
		// System.out.println("解密后内容:" + content);
		// List<ProductMongo> productMongos =
		// productMongoDao.getMostFamiliarProduct(content, 100, 10,
		// collectionName);//maxScore暂时没用
		/*for (ProductMongo productMongo : productMongos) {
			// 按照设定的阀值来决定是否重复
			float textScore = productMongo.getScore();// 文本查重得到的重复分数
			int wordCount = productMongo.getContent().split(CONTENTREGEX).length;// 单词数
			float productThreshold = textScore / wordCount;
			System.out.println("分数:" + textScore + ",单词数:" + wordCount + ",值:" + productThreshold);
			if (productThreshold >= REPETETHRESHOLD) {
				repeatIds += productMongo.getProductId();
				repeatIds += "$";
				repeatPercent += productMongo.getScore();
				repeatPercent += "$";
			}
		}
		if (!repeatIds.equals("")) {
			product.setRepeatIds(repeatIds);
			product.setRepeatPercent(repeatPercent);
			return true;
		}*/
		return false;
	}

	/**
	 * 发码
	 *
	 * @param prototype
	 *            从Prototype获取发码需要的字段
	 * @param product
	 *            将ISTC码set到Product
	 * @return 发码结果
	 */
	private String getIstcCode(ISTCPrototype prototype, ProductObj product) {
		try {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);

			String orgid = product.getTbUser().getOrgid();
			OrganizationinfoObj organizationinfoObj = organizationinfoDao.find(orgid);
			String ocode = organizationinfoObj.getOcode();
			int nowautoistc = Integer.parseInt(organizationinfoObj.getNowautoistc());

			// 校验码
			int validateCode = new Random().nextInt(9);
			String istc = String.format("%s-%s-%04d-%08d-%01d", "ISTC", (null == ocode) ? "000" : ocode, year,
					nowautoistc, validateCode);

			nowautoistc += 1;
			organizationinfoObj.setNowautoistc(String.format("%08d", nowautoistc));
			organizationinfoDao.update(organizationinfoObj);
			product.setIstc(istc);
			return "SUCCEED";
		} catch (Exception e) {
			return "Failed:获得自动分配ISTC码失败！";
		}
	}

	/**
	 * 保存附件到本地
	 *
	 * @author 李熙伟 2016年4月13日
	 */
	private String saveFilesToLocal(HttpFile file) throws Exception {
		String finalPath = "";
		// 保存上传文件
		String pathname = ConfigSingleton.getInstance().getProperties().getProperty("cn.edu.ncut.istc.information");
		Calendar now = Calendar.getInstance();
		pathname = pathname + "/" + now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/"
				+ now.get(Calendar.DAY_OF_MONTH);

		File fp = new File(pathname);
		if (!fp.exists()) {
			fp.mkdirs();// 目录不存在的情况下，创建目录。
		}

		String fileName = file.getFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		if (!suffix.equals(".txt"))
			throw new Exception("仅支持.txt格式的附件");
		// 先将附件Base64解码后再存储
		byte[] decodeFile = Base64.decodeBase64(file.getContent());
		if (fileName == null) {
			throw new Exception("文件未上传");
		} else {
			// 生成服务端本地服务名 格式 文件名_UUID
			fileName = UUID.randomUUID().toString() + "_" + fileName;
			// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
			FileOutputStream fio = new FileOutputStream(pathname + "/" + fileName);
			fio.write(decodeFile);
			finalPath = pathname + "/" + fileName;
		}
		return finalPath;
	}

	/**
	 * 抽取Product信息
	 *
	 * @param prototype
	 *            接口收到的原型
	 * @param product
	 *            抽取结果product对象
	 * @return 返回结果 SUCCEED/错误详情
	 */
	private String extractProduct(ISTCPrototype prototype, ProductObj product) {
		try {
			product.setProductname(prototype.getProductname());
			product.setContentabstract(prototype.getContentabstract());
			product.setPublishtype(prototype.getPublishtype());
			product.setContenttype(prototype.getContenttype());
			product.setReader(prototype.getReader());
			product.setPlanguage(prototype.getPlanguage());
			product.setMade(prototype.getMade());
			product.setAuthor(prototype.getAuthor());
			product.setCreatetime(prototype.getCreatetime());
			product.setPublishid(prototype.getPublishid());
			product.setSeriesname(prototype.getSeriesname());
			product.setUpdatetime(prototype.getUpdatetime());
			product.setProductsource(prototype.getProductsource());
			product.setChapterno(prototype.getChapterno());
			product.setSmallmatterno(prototype.getSmallmatterno());
			product.setSubmitetime(new Date());
			product.setProductYear(new BigDecimal(new SimpleDateFormat("yyyy").format(new Date())));
			// Skip: ISTC码、作品状态、样本路径、样本URL、
			product.setUniqueid(prototype.getUniqueid());
			product.setIsfinish(prototype.getIsfinish());
			product.setLiteratureProductStatus(prototype.getLiteratureProductStatus());
			product.setProductoriginurl(prototype.getProductOriginUrl());
			// 关联用户
			UserObj user = new UserObj();
			String loginName = prototype.getUserLoginName();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("LOGINNAME", loginName);
			List<UserObj> userList = userDao.findAll("LOGINNAME=:LOGINNAME", params, null);
			if (userList.size() == 1) {
				user = userList.get(0);
				product.setTbUser(user);
			} else if (userList.size() > 1)
				return "Failed:查找到多个用户!请检查!";
			else
				return "Failed:没有查找到相应用户!请检查!";

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return e.getMessage().toString();
		}
		return "SUCCEED";
	}

	private String transHttpFileToString(byte[] file) throws Exception {
		byte[] decodeFile = Base64.decodeBase64(file);
		String content = new String(decodeFile, FILECHARSET);
		return content;
	}
}
