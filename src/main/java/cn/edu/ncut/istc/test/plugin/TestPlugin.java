package cn.edu.ncut.istc.test.plugin;

import cn.edu.ncut.istc.common.plugin.JsonUtils;
import cn.edu.ncut.istc.common.util.FileUtils;
import cn.edu.ncut.istc.model.plugin.AuthorPrototype;
import cn.edu.ncut.istc.model.plugin.HttpFile;
import cn.edu.ncut.istc.model.plugin.ISTCPrototype;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TestPlugin
{
	private static String getTextJsonFromLocal(int productNo) throws ParseException
	{
		String json = "";
		ISTCPrototype prototype = new ISTCPrototype();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		//作品基本信息
		String text = FileUtils.readFileByLines("C:\\Users\\51195\\Desktop\\EAVP_ISTC\\" + "Product" + productNo + ".txt");
		String[] content = text.split("#####_#####");
		prototype.setProductname(content[0]);
		prototype.setContentabstract(content[1]);
		prototype.setPublishtype(content[2]);
		prototype.setContenttype(content[3]);
		prototype.setReader(content[5]);
		prototype.setPlanguage(content[4]);
		prototype.setMade(content[6]);
		prototype.setAuthor(content[7]);
		if (content[8] != null)
			prototype.setCreatetime(sf.parse(content[8]));
		else
			prototype.setCreatetime(new Date());

		prototype.setPublishid(getRandom(3));
		prototype.setSeriesname("系列" + getRandom(5));
		if (content[9] != null)
			prototype.setUpdatetime(sf.parse(content[9]));
		else
			prototype.setUpdatetime(new Date());
		prototype.setProductsource(new String[]{"网络","原创"}[new Random().nextInt(1)]);
		prototype.setChapterno(new BigDecimal(content[10]));
		prototype.setSmallmatterno(new BigDecimal(new Random().nextInt(100)));
		//Skip: ISTC码、作品状态、样本路径、样本URL、
		prototype.setUniqueid(getRandom(25));
		prototype.setIsfinish(new Random().nextBoolean());
		prototype.setAuthorUniqueId("542825RU");
		prototype.setProductOriginUrl("www.baidu.com");
		prototype.setLiteratureProductStatus("连载");
		//Skip: 上报时间

		//添加申请人登录名
		prototype.setUserLoginName("admin");
		//添加附件
		HttpFile file = new HttpFile();
		File f =  new File("C:\\Users\\51195\\Desktop\\EAVP_ISTC\\" + "Product" + productNo+".txt");
		file.setFilename(f.getName());
		byte[] fileContent = new byte[0];
		try
		{
			//content = FileUtils.readfile("C:\\Users\\51195\\Desktop\\MongoText\\" + f.getName());
			fileContent = FileUtils.readfile("C:\\Users\\51195\\Desktop\\EAVP_ISTC\\" + "Product" + productNo+".txt");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		file.setContent(Base64.encodeBase64(fileContent));
		prototype.setFile(file);
		json = JsonUtils.toJson(prototype);
		System.out.println("Client:" + json);
		return json;
	}
	private static String getTextJson()
	{
		String json = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ISTCPrototype prototype = new ISTCPrototype();
		//作品基本信息
		prototype.setProductname("全文查重客户端" + new Random().nextInt(100000));
		//prototype.setProductname("敏感词过滤不通过C");
		prototype.setContentabstract("作品内容简介");
		prototype.setPublishtype("作品分类");
		prototype.setContenttype("作品内容类别");
		prototype.setReader("读者对象");
		prototype.setPlanguage("语种");
		prototype.setMade("制作单位");
		prototype.setAuthor("作者");
		prototype.setCreatetime((new Date(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000)));
		prototype.setPublishid("冗余字段，出版社ID");
		prototype.setSeriesname("系列名称");
		prototype.setUpdatetime(new Date());
		prototype.setProductsource("作品来源");
		prototype.setChapterno(new BigDecimal(1));
		prototype.setSmallmatterno(new BigDecimal(1));
		//Skip: ISTC码、作品状态、样本路径、样本URL、
		prototype.setUniqueid(getRandom(25));
		prototype.setIsfinish(false);
		prototype.setAuthorUniqueId("WFORE8F7");
		prototype.setProductOriginUrl("www.baidu.com");
		prototype.setLiteratureProductStatus("连载");
		//Skip: 上报时间

		//添加申请人登录名
		prototype.setUserLoginName("admin");
		//添加附件
		HttpFile file = new HttpFile();
		//File dir = new File("C:\\Users\\51195\\Desktop\\MongoText\\");
		//File[] fileList = dir.listFiles();
		//Random random = new Random();
		//int n = random.nextInt(fileList.length);
		//File f =  new File("C:\\Users\\51195\\Desktop\\MongoText\\UTF8格式附件测试.txt");//fileList[n];
		String filePath = "C:\\Users\\51195\\Desktop\\UTF8格式附件测试.txt";
		File f =  new File(filePath);
		file.setFilename(f.getName());
		byte[] content = new byte[0];
		try
		{
			//content = FileUtils.readfile("C:\\Users\\51195\\Desktop\\MongoText\\" + f.getName());
			content = FileUtils.readfile(filePath);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		file.setContent(Base64.encodeBase64(content));
		prototype.setFile(file);
		json = JsonUtils.toJson(prototype);
//		System.out.println("Client:" + json);
		return json;
	}
	private static String getAuthorJson()
	{
		String json = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AuthorPrototype author = new AuthorPrototype();

		author.setAuthorrealname("作家李半仙" + getRandom(2));
		author.setIdcradno("11010519930822xxxx" + getRandom(2));
		author.setSigntime(new Date());
		author.setPenname("小李");
		author.setSignwebsite("北方工业大学");
		author.setAuthordesc("MidKiller");
		author.setRemark("备注");
		author.setUniqueid(getRandom(8));
		author.setOcode("0A1");
		author.setUniqueid(getRandom(8));
		json = JsonUtils.toJson(author);
		System.out.println(json);
		return json;
	}
	public  static String  getRandom(int k)
	{
		Random random = new Random();
		String res = "";
		int len = 0;
		while (len != k)
		{
			if(random.nextBoolean())
			{
				int n = 65 + random.nextInt(26);
				res += (char)n;
			}
			else
			{
				res += random.nextInt(10);
			}
			len++;
		}
//		System.out.println(res);
		return res;
	}
	public static void testRegister() throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		//String json = getAuthorJson();
		String json = getTextJson();
		//String json = getTextJsonFromLocal(i);
		map.put("json",json);
		//String url="http://localhost:8080/ISTCServer/productRegister/authorRegister.do";
		String url="http://localhost:8080/ISTCServer/productRegister/register.do";
		String responseContent="";
		try
		{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url); // 创建HttpPost
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : map.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			HttpResponse response=null;
			response = client.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "ISO8859-1");
				EntityUtils.consume(entity); // Consume response content
			}
			client.getConnectionManager().shutdown();
		}
		catch(ConnectException c)
		{
			responseContent="主机无法请求到地址："+url;
		}
		catch(Exception e)
		{
			responseContent="NoSendSucceed "+e.getMessage();
			e.printStackTrace();
		}
		
		System.out.println(responseContent);
	}
	public static void main(String[] args) throws Exception
	{
		//for (int i = 1; i <= 3000; i++)
		//{
			testRegister();
		//}
	}
	
}
