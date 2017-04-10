package cn.edu.ncut.istc.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import com.google.gson.Gson;

import cn.edu.ncut.istc.model.assistant.RankObj;
//import net.sf.json.JSONObject;

public class TestJsoup {

	@Test
	public void AnalysisHTMLByString() {
		String html = "<p><a href=\"a.html\">a</p><p> 文本</p>";
		Document doc = Jsoup.parse(html);
		Elements ele = doc.getElementsByTag("p");
		for (Element e : ele) {
			// System.out.println(e.text());

		}

	}

	// @Test
	// public void AnlysisHTMLByFile() throws IOException {
	// File file = new File(System.getProperty("user.dir") + "\\a.html");
	// Document doc = Jsoup.parse(file, "UTF-8");
	// Elements eles = doc.getElementsByTag("a");
	// for (Element e : eles) {
	// System.out.println(e.text());
	// System.out.println(e.attr("href"));
	// }
	// Element ele = doc.getElementById("btn");
	// System.out.println(ele.html());
	//
	// }

	// 获取热搜名字
//	@Test
//	public void AnlysisHTMLByURL() throws IOException {
//		int timeout = 3000;
//		Document doc = Jsoup.connect("http://s.weibo.com/top/summary?cate=total&key=person").get();
//		// Document doc =
//		// Jsoup.connect("http://s.weibo.com/top/summary?cate=total&key=films").get();s
//		// 获取script中部分
//		Elements select = doc.select("script");
//		Element tds = select.get(select.size() - 2);
//		String str = tds.toString();
//		str = str.substring(49, str.length() - 10);
//		// System.out.println(str);
//
//		// 解析json
//		JSONObject dataJson = JSONObject.fromString(str);
//		String html = dataJson.getString("html");
//		System.out.println(html);
//
//		// 抽取table中的内容
//		List<RankObj> rankObjList = new ArrayList<>();
//		Document htmlDoc = Jsoup.parse(html);
//		Elements trs = htmlDoc.select("table").select("tr");
//		for (int i = 1; i < trs.size(); i++) {
//			Elements htmltds = trs.get(i).select("td");
//			RankObj rankObj = new RankObj();
//			for (int j = 0; j < htmltds.size(); j++) {
//				// String text = htmltds.get(j).text();
//				// 放一行
//				if (j == 0) {
//					rankObj.setRank(Integer.parseInt(htmltds.get(j).text()));
//				} else if (j == 1) {
//					rankObj.setName(htmltds.get(j).text());
//				} else if (j == 2) {
//					rankObj.setRankIndex(Integer.parseInt(htmltds.get(j).text()));
//				}
//			}
//			rankObjList.add(rankObj);
//			rankObj = null;
//		}
//
//		for (int i = 0; i < rankObjList.size(); i++) {
//			System.out.println(rankObjList.get(i).getRank()+"  "+rankObjList.get(i).getName()+"  "+rankObjList.get(i).getRankIndex());
//		}
//	}

	// 获取热搜词
	/*@Test
	public void AnlysisWordHTMLByURL() throws IOException {
		int timeout = 3000;
		Document doc = Jsoup.connect("http://s.weibo.com/top/summary?cate=total&key=films").get();

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

		for (int i = 0; i < rankObjList.size(); i++) {
			System.out.println(rankObjList.get(i).getName());
		}
		// System.out.println(doc);
		// 获取A标签个数
		// System.out.println("共有超链接：" + doc.getElementsByTag("a").size());

		// System.out.println("获取指定ID:" +
		// doc.getElementById("navigator").html());

		// Elements eles = doc.select("#navigator");
		// for (Element ele : eles) {
		// System.out.println(ele.html());
		// }
	}*/

	@Test
	public void test() {
		String string = "<script>STK && STK.pageletM && STK.pageletM.view";
		// System.out.println(string.length());
		String string2 = "<script/>";
		// System.out.println(string2.length());
	}

}
