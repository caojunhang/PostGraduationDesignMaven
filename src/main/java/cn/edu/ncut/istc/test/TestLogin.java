package cn.edu.ncut.istc.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.ncut.istc.model.KBookTestObj;
import cn.edu.ncut.istc.model.KBookTrainObj;
import cn.edu.ncut.istc.model.SeriseObj;
import cn.edu.ncut.istc.model.assistant.RankObj;
import cn.edu.ncut.istc.model.view.VTrainObj;
import cn.edu.ncut.istc.service.SystemService;
import cn.edu.ncut.istc.service.weixin.QueryService;
//import net.sf.json.JSONObject;

@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestLogin {
	@Value(value = "${cn.edu.ncut.istc.yeshu32}")
	private String yeshu32;

	@Resource
	private SystemService systemService;

	@Resource
	private QueryService queryService;

	// @Test
	// public void testSer() {
	// String year = null;
	// List<SeriseObj> list = queryService.getForecast(year);
	// for (SeriseObj obj : list) {
	// System.out.println(obj.getCata());
	//
	// }
	// }

	@Test
	public void testGetTestBook() {
//		List<KBookTestObj> kBookTestList = systemService.getKBookTestList(0, 10);
//		for (KBookTestObj kBookTestObj : kBookTestList) {
//			System.out.println(kBookTestObj.getId());
//		}
		for(int i =0 ;i<=10;i++)
		{
			int y=400+(int)(Math.random()*1000);
			System.out.println(y);
		}
	}

	@Test
	public void testGetTrainBook() {
		List<KBookTrainObj> kBookTrainList = systemService.getKBookTrainList(0, 10);
		for (KBookTrainObj kBookTrainObj : kBookTrainList) {
			System.out.println(kBookTrainObj.getBookmainname());
		}
	}

	// 获取字符
	@Test
	public void testVTrainBook() {
		List<VTrainObj> kBookTrainList = systemService.getVBookList(10, 10);
		for (VTrainObj kBookTrainObj : kBookTrainList) {
			System.out.println(kBookTrainObj.getCata2());
		}
	}

	// sigmod函数实现 与 反转
	@Test
	public void process() {
		System.out.println(sigmoid(100));
	}

	private double sigmoid(double src) {
		return (double) (1.0 / (1 + Math.exp(-src)));
	}

/*	// 字符串拼接与持久化
	@Test
	public void stringProcessAndStore() throws IOException {
		List<VTrainObj> kBookTrainList = systemService.getVBookList(10, 50);

		// 获取List
		String strWord = "http://s.weibo.com/top/summary?cate=total&key=films";
		String strPerson = "http://s.weibo.com/top/summary?cate=total&key=person";
		List<RankObj> weiboWord = getWeiboInfo(strWord);
		List<RankObj> weiboPerson = getWeiboInfo(strPerson);

		for (VTrainObj kBookTrainObj : kBookTrainList) {
			WriteStringToFile2("c:\\a.txt", stringProcess(kBookTrainObj, weiboWord, weiboPerson));
		}
	}*/

	private String stringProcess(VTrainObj kBookTrainObj, List<RankObj> weiboWord, List<RankObj> weiboPerson)
			throws IOException {
		StringBuilder str = new StringBuilder();
		str.append(kBookTrainObj.getSaleeveryyear() + ";");
		str.append(kBookTrainObj.getCata2() + ";");
		str.append(kBookTrainObj.getCata3() + ";");
		str.append(kBookTrainObj.getTopiccata() + ";");
		str.append(kBookTrainObj.getLanguagetype() + ";");
		str.append(kBookTrainObj.getBoundform() + ";");
		str.append(kBookTrainObj.getPagenum() + ";");
		str.append(kBookTrainObj.getCharnum() + ";");
		str.append(kBookTrainObj.getTypecontent() + ";");
		str.append(kBookTrainObj.getRevision() + ";");
		str.append(kBookTrainObj.getImpression() + ";");
		str.append(kBookTrainObj.getReader() + ";");
		str.append(kBookTrainObj.getPublicationway() + ";");
		str.append(kBookTrainObj.getAper() + ";");// 作者
		str.append(kBookTrainObj.getPper() + ";");// 出版社

		// 增加选题名权质值
		str.append(computeWord(kBookTrainObj.getBookname(), weiboWord) + ";");

		// 增加内容简介的权值
		str.append(computeWord(kBookTrainObj.getContentsummary(), weiboWord) + ";");

		// 增加作者的权质值
		str.append(computePerson(kBookTrainObj.getFisrtauthor(), weiboPerson));

		return str.toString();
	}

	private Object computePerson(String bookname, List<RankObj> weiboPerson) {
		for (RankObj rankObj : weiboPerson) {
			if (bookname.contains(rankObj.getName())) {
				return "1;" + rankObj.getRankIndex();
			}
		}
		return "0;0";
	}

	private String computeWord(String string, List<RankObj> rankObjList) {
		for (RankObj rankObj : rankObjList) {
			if (string.contains(rankObj.getName())) {
				return "1;" + rankObj.getRankIndex();
			}
		}
		return "0;0";
	}

	/*private List<RankObj> getWeiboInfo(String strInput) throws IOException {
		int timeout = 3000;
		Document doc = Jsoup.connect(strInput).get();
		// 获取script中部分
		Elements select = doc.select("script");
		Element tds = select.get(select.size() - 2);
		String str = tds.toString();
		str = str.substring(49, str.length() - 10);
		// System.out.println(str);

		// 解析json
		JSONObject dataJson = JSONObject.fromString(str);
		String html = dataJson.getString("html");
		// System.out.println(html);

		// 抽取table中的内容
		List<RankObj> rankObjList = new ArrayList<>();
		Document htmlDoc = Jsoup.parse(html);
		Elements trs = htmlDoc.select("table").select("tr");
		for (int i = 1; i < trs.size(); i++) {
			Elements htmltds = trs.get(i).select("td");
			RankObj rankObj = new RankObj();
			for (int j = 0; j < htmltds.size(); j++) {
				// String text = htmltds.get(j).text();
				// 放一行
				if (j == 0) {
					rankObj.setRank(Integer.parseInt(htmltds.get(j).text()));
				} else if (j == 1) {
					rankObj.setName(htmltds.get(j).text());
				} else if (j == 2) {
					rankObj.setRankIndex(Integer.parseInt(htmltds.get(j).text()));
				}
			}
			rankObjList.add(rankObj);
			rankObj = null;
		}
		return rankObjList;
	}*/

	@Test
	public void testStrinPrint() {
		String str1 = "tutorials point", str2 = "http://";

		String cs1 = "int";

		// string contains the specified sequence of char values
		boolean retval = str1.contains(cs1);
		System.out.println("Method returns : " + retval);

		// string does not contain the specified sequence of char value
		retval = str2.contains("_");
		System.out.println("Methods returns: " + retval);
		System.out.println();
	}

	public void WriteStringToFile2(String filePath, String context) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(context);
			// bw.write(context);// 往已有的文件上添加字符串
			bw.write("\n");
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// not use
	public void WriteStringToFile(String filePath, String context) {
		try {
			File file = new File(filePath);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			// ps.println("http://www.jb51.net");// 往文件里写入字符串
			ps.append(context);// 在已有的基础上添加字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testString() {
		String year = "2016-1";
		String cata = "";
		List<SeriseObj> SeriseList = queryService.getForecastRank(year);
		for (SeriseObj seriseObj : SeriseList) {
			cata += "\"" + seriseObj.getCata() + "\",";
		}
		cata = cata.substring(1, cata.length() - 2);
		System.out.println(cata);
	}

	// {value:15, name:'大众'},{value:30, name:'历史题材类'},
	@Test
	public void testString2() {
		String year = "2016-1";
		String cata = "";
		List<SeriseObj> SeriseList = queryService.getForecastRank(year);
		for (SeriseObj seriseObj : SeriseList) {
			cata += "{value:" + seriseObj.getMarketshare() + ",name:'" + seriseObj.getCata() + "'},";
		}
		cata = cata.substring(0, cata.length() - 1);
		System.out.println(cata);
	}

	@Test
	public void testStrin3() {
		String year = "2016-1";

		System.out.println(year.length());
		// 6
	}

	@Test
	public void testStrin4() {
		String year = "哈哈 呵呵 嘻嘻";
		String[] as = year.split(" ");
		for (int i = 0; i < as.length; i++)
			System.out.println(as[i]);
	}

	@Test
	public void testCol() {
		String year = "20366;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;1;0;0;0;0;1;0;0;1;0;0;0;0;0;252;126;0;1;0;1;1;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;1;0;0;0;0;0.0862;0.0015;0;0;0;0;0;0";
		String[] as = year.split(";");
		System.out.println(as.length);
	}

	@Test
	public void test5() {
		String info = "haha";
		BigDecimal b = queryService.saveUserInfoAndgetId(info);
		System.out.println(b);
	}

	@Test
	public void stat() {
		System.out.println(yeshu32);
	}

}
