package cn.edu.ncut.istc.service;

import cn.edu.ncut.istc.dao.ChannelDao;
import cn.edu.ncut.istc.dao.InformationDao;
import cn.edu.ncut.istc.dao.ProductDao;
import cn.edu.ncut.istc.model.ChannelObj;
import cn.edu.ncut.istc.model.InformationObj;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.assistant.FileAssistantObj;
import cn.edu.ncut.istc.model.assistant.InfoAssistantObj;
import cn.edu.ncut.istc.service.base.BaseFileHandle;
import cn.edu.ncut.istc.service.base.BaseServiceImpl;
import cn.edu.ncut.istc.webservice.base.ObjectResult;
import cn.edu.ncut.istc.webservice.base.Result;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;


/**
 * @Description NoticeServiceImpl用来实现所有istc信息公示功能相关的webservice的业务  
 *              使用场合：NoticeServiceImpl调用基本的Dao为istc信息公示数据操作服务。 
 */
@Transactional
@Service("noticeService")
public class NoticeServiceImpl extends BaseServiceImpl<Object> implements NoticeService {
	@Resource
	protected SessionFactory sessionFactory;
	
	@Autowired
	private InformationDao informationDao;
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	private String filePath ="";
	
	//private String filePath = this.getClass().getClassLoader().getResource("/").getPath().replace("WEB-INF/classes/", "informationImages/");
	
	@Override
	public InformationObj getInformationById (BigDecimal infid){
		return informationDao.find(infid);
	}
	
	@Override
	public List<InformationObj> queryInformationList(){
//			Map<String, Object> params = new HashMap<String, Object>();
//		    params.put("inftype", "10,11,14,15");
//		    params.put("infstatus", "22");
		    String where = "infstatus = 22 and inftype in(10,11,14,15)";
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("infdate", "DESC");
			
			return informationDao.findAll(where, null, orderby);
	}
	
		
	/*保存要发布的信息*/
	@Override
	public String saveInformation(){
          Result result=new Result();		
		  String message="";
		  InfoAssistantObj infoAssistantObj=(InfoAssistantObj)parameters.getDataObject();
		  try{
			  
			  if(infoAssistantObj.getInformationobj().getIspic().equals("1"))
			  {
				  String filename=String.valueOf(System.currentTimeMillis())+infoAssistantObj.getTitleImage().getFileext();
				  message=saveTitleImage(infoAssistantObj.getTitleImage(),filename);//保存标题图片
				  infoAssistantObj.getInformationobj().setPicurl(filename);
			  }
			  InformationObj information=infoAssistantObj.getInformationobj();
			  information.setDelaydate(null);
			  informationDao.save(information);
			  message=saveImage(infoAssistantObj.getImageList());//保存内容图片
			  message=saveText(infoAssistantObj.getHtmltext(),information.getInfid().toString());
			  result.setType(0);
			  result.setMessage(message);
		  }catch(Exception e){
			  message=e.toString();
			  result.setType(1);
		  }		 		  
		  return result.asXML(true);
	}
	
	

	public InformationObj insertInformation(InformationObj informationObj){
		String message="信息保存成功";
		//informationObj=(InformationObj)parameters.getDataObject();
		try{
			informationDao.save(informationObj);
		}catch(Exception e){
		}
		return informationObj;
	}
	
	
	public String saveTitleImage(FileAssistantObj titleImage,String filename){
		String message="";
		try{		
			FileAssistantObj robj=titleImage.saveBlock(filePath, filename);
		}catch(Exception e){
			message=e.toString();
		}
		return message;
	}
	
	public String saveImage(ArrayList<FileAssistantObj> imageList){
		String message="图片上传成功！";
		//FileAssistantObj obj=(FileAssistantObj)parameters.getDataObject();
		// List<FileAssistantObj> fileList=(List<FileAssistantObj>)parameters.getDataObject();
		try{
			if(imageList != null){
		    	for(FileAssistantObj obj:imageList){				
					 String fileName=obj.getFileid();
					 FileAssistantObj robj=obj.saveBlock(filePath,fileName);
				}
		    } 	
		}catch(Exception e){
			message=e.toString();
			System.out.print(message);
		}
				
		return message;
	}

	public String saveText(String htmlText,String infoId){
		String message="上传成功！";
		try{
		    String filepath=getFilePath();
		    FileWriter writer=new FileWriter(filepath+"/info"+infoId+".txt");
		    writer.write(htmlText);
		    writer.flush();
		    writer.close();
		}catch(Exception e){
			message=e.toString();
		}
	    
		return message;
	}
	
	
	private String getFilePath() throws IOException{
			Properties prop = new Properties();  
		    String filepath = "";
			InputStream in = BaseFileHandle.class.getResourceAsStream("../../../../../../config.properties");
	        prop.load(in);   
	        filepath = prop.getProperty("cn.edu.ncut.istc.informationuploadpath").trim(); 
			return filepath;	
	}
	
	
	@Override
	public String readText(String infoId){
		String content="";
		BufferedReader br;
		try{
			//String encoding = "UTF-8";
		    String filepath=getFilePath();
			File file = new File(filepath+"/info"+infoId+".txt");
	//		File file = new File("H:/information/info"+infoId+".txt");
			if (file.isFile() && file.exists()) { 
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));   
                BufferedReader bufferedReader = new BufferedReader(read);   
                String lineTXT = null;   
                while ((lineTXT = bufferedReader.readLine()) != null) {   
                      content=content+" "+lineTXT.toString().trim();   
                  }   
                read.close();   
            }else{   
               content="暂时无内容！敬请期待！" ;
            }   
		}catch(Exception e){
		}
		return content;
	}
	
	/*********************栏目管理***********************/
	/**
	 * 功能：根据cid获取栏目对象
	 * @author 王帅  2013-04-24
	 */
	@Override
	public String getChannelObjByChannelid() {
		ObjectResult<ChannelObj> result = new ObjectResult<ChannelObj>();
		try {
			ChannelObj obj = channelDao.find(new BigDecimal(parameters.getParams().get("cid")));
			result.setResultObject(obj);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("获取栏目对象出错！");
		}
		return result.asXML(true);
	}
	
	/**
	 * 功能：新建ChannelObj
	 * @author 王帅  2013-04-25
	 */
	@Override
	public String saveChannelObj() {
		Result result = new Result();
		try {
			ChannelObj obj=(ChannelObj)parameters.getDataObject();
			channelDao.save(obj);
			result.setType(0);
			result.setMessage("栏目新增成功！");
		} catch (Exception e) {
			result.setType(1); 
			result.setMessage("栏目新增失败！");
		}
		return result.asXML(true);
	}
	
	/**
	 * 功能：保存修改的栏目
	 * @author 王帅  2013-04-25
	 */

	@Override
	public String updateChannelObj() {

		Result result = new Result();
		try {
			ChannelObj obj = (ChannelObj)parameters.getDataObject();
			channelDao.update(obj);
			result.setType(0);
			result.setMessage("栏目修改成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("栏目修改失败！");
		}
		return result.asXML(true);
	}
	
	/**
	 * 功能：删除选中的ChannelObj
	 * @author 王帅  2013-04-25
	 */
	
	@Override
	public String deleteChannelObjByCids() {
		// TODO Auto-generated method stub
		
		Result result = new Result();		
		try{
			String[] channelids = parameters.getParams().get("cids").split(",") ;
			for (String str : channelids) { 
				BigDecimal id = new BigDecimal(str) ;
				channelDao.delete(id);	
				String where="cparentid = :id";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", id);
				List<ChannelObj> list = channelDao.findAll(where, params, null);
				for(ChannelObj obj : list)
				{
					obj.setCparentid(new BigDecimal(0));
				}				
			}
			result.setType(0);
			result.setMessage("栏目删除成功！");
		}catch(Exception e){
			result.setType(1);
			result.setMessage("栏目删除失败！");
		}
		return result.asXML(true); 
	}
	
	/**
	 * 功能：获取所有栏目
	 * @author 王帅  2013-04-26
	 */
	
	@Override
	public String getAllChannel(){		
		ObjectResult<List<ChannelObj>> result = new ObjectResult<List<ChannelObj>>();
		try{
			List<ChannelObj> channelList = channelDao.findAll();
			result.setResultObject(channelList);	
		}catch(Exception e){
			result.setType(1);
			result.setMessage("获取栏目失败！");		
		}
		return result.asXML(true);
	}
	
	@Override
	public List<ChannelObj> getAllChannelList(String where){	
		try{
			List<ChannelObj> channelList = channelDao.findAll(where, null, null);
			return channelList;
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public List<InformationObj> getinformationsList(Long id, int firstRsultNum, int lastRsultNum){	
		List<InformationObj> infoList = null;
		try{
			String sql = "select * from (select rownum as rownumber, tmp.* from (select *  from tb_information t where t.infstatus = 22 and t.inftype = "+id+") tmp)"
					+ " where rownumber >= "+firstRsultNum+" and rownumber < = " + lastRsultNum;
			
			infoList = this.getList(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return infoList;
	}
	
	@Override
	public int getinformationsListCount(Long id){	
		int totalCount = 0;
		try{
			String sql = "select count(1) as totleCount from tb_information t where t.infstatus = 22 and t.inftype = "+id;
			List result = this.getMapList(sql);
			if(result.size()>0){
				Map temp = (Map)result.get(0);
				totalCount = Integer.parseInt(temp.get("TOTLECOUNT").toString());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return totalCount;
	}


	@Override
	public List<InformationObj> getAllinformations(){	
		List<InformationObj> infoList = null;
		try{
			infoList = informationDao.getAllinformations();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return infoList;
	}
	
	@Override
	public String getInformationObjByid(){
		ObjectResult<InformationObj> result = new ObjectResult<InformationObj>();
		try{
			String id = parameters.getParams().get("id");
			InformationObj obj = informationDao.find(new BigDecimal(id));
			String message = readText(id);
			result.setResultObject(obj);
			result.setType(0);
			result.setMessage(message);
		}catch(Exception e){
			result.setType(1);
			result.setMessage(e.toString());
		}
		return result.asXML(true);
	}
	
	@Override
	public List<InformationObj> getinformationByInftype(BigDecimal inftype){
		try{
			String sql = "select * from (select rownum as rownumber, tmp.* from (select *  from tb_information t where t.infstatus = 22 and t.inftype = "+inftype+") tmp) where rownumber < 10";
			
			List<InformationObj> informations = this.getList(sql);
			return informations;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}	
	
	/**
	 * 根据SQL查询返回结果集List<Map>
	 * 返回类型为InformationObj的结果集
	 * @param sql
	 * @return
	 */
	private List getList(String sql) {
		Query  sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(InformationObj.class);
		List list = sqlQuery.list();
		return list;
	}
	
	/**
	 * 根据SQL查询返回结果集List<Map>
	 * 返回类型为MAP的结果集
	 * @param sql
	 * @return
	 */
	private List getMapList(String sql) {
		Query  sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = sqlQuery.list();
		return list;
	}
	
	@Override
	public String updateInformation(){
		Result result = new Result();
		try{
			InfoAssistantObj obj = (InfoAssistantObj)parameters.getDataObject();
			InformationObj information = obj.getInformationobj();
			 if(obj.getInformationobj().getIspic().equals("1") && obj.getTitleImage()!=null)//普通图片新闻
			  {
				  String filename=String.valueOf(System.currentTimeMillis())+obj.getTitleImage().getFileext();			
				  saveTitleImage(obj.getTitleImage(),filename);//保存标题图片
				  obj.getInformationobj().setPicurl(filename);
			  }
			 informationDao.update(information);
			String message = saveText(obj.getHtmltext(),String.valueOf(information.getInfid()));
			message=saveImage(obj.getImageList());
			result.setType(0);
			result.setMessage("修改成功！");
		}catch(Exception e){
			result.setType(1);
			result.setMessage("修改失败！");
		}
		return result.asXML(true);
	}

	@Override
	public String downloadInfoImage() {
		ObjectResult<List<FileAssistantObj>> result = new ObjectResult<List<FileAssistantObj>>();
		String id = parameters.getParams().get("id");
		String message = "获取图片成功!";
		try {
			InformationObj info = informationDao.find(new BigDecimal(id));
			List<FileAssistantObj> filelist = new ArrayList<FileAssistantObj>();
			if(info.getImagename() !=null || !info.getImagename().equals("")){
				String[] paths = info.getImagename().split("#");
				for(String path : paths){
					String newpath = filePath + path;
					if(!new File(newpath).exists()){
						message = "获取图片失败！";
					}
					else{
						FileAssistantObj file = new FileAssistantObj("", newpath);
						file.setFileext(path.substring(path.lastIndexOf(".")+1));
						file.setSelfpath(path);
						FileInputStream fileInputStream = new FileInputStream(newpath);
						byte[] b = new byte[fileInputStream.available()];
						fileInputStream.read(b);
						file.setBlockData(b);
						fileInputStream.close();
						filelist.add(file);
					}
				}
			}
			else{
				message = "获取图片失败！";
			}
			result.setResultObject(filelist);
			result.setType(0);
			result.setMessage(message);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage(e.toString());
		}
		return result.asXML(true);
	}
	
	@Override
	public String pulishSelectedInfors(){
		Result result = new Result();
		try{
			String[] ids = parameters.getParams().get("ids").split(",");
			for(String id : ids){
				InformationObj obj = informationDao.find(new BigDecimal(id));
				if(obj.getInfstatus().toString().equals("21")){
					obj.setInfstatus(new BigDecimal(22));
				}
				else{
					obj.setInfstatus(new BigDecimal(21));
				}
				informationDao.update(obj);
			}
			result.setType(0);
			result.setMessage("操作成功!");
		}catch(Exception e){
			result.setType(1);
			result.setMessage("操作失败！");
		}
		return result.asXML(true);
	}
	
	@Override
	public ChannelObj getChannelByChannelid(Long id){
		try{
			String where = "CID = :CID";
			Map<String , Object> param = new HashMap<String , Object>();
			param.put("CID", id);
			ChannelObj obj = channelDao.find(new BigDecimal(id));
			return obj;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	
	/*********************信息发布InformationObj相关操作***********************/
	@Override
	public String delectSelectsInformation()
	{
		Result result =new Result();
		try{
			String[] tempInformationList=parameters.getParams().get("informSelects").split(",");
			for(String temp:tempInformationList)
			{
				informationDao.delete(new BigDecimal(temp));
			}
			result.setType(0);
			result.setMessage("删除成功");
		}
		catch(Exception e)
		{
			result.setType(1);
			result.setMessage("删除失败！");
		}
	return result.asXML(true);
	}

	@Override
	public List<InformationObj> getistcinformationsList(Long id,int firstResultNum,int lastResultNum){	
		List<InformationObj> infoList = null;
		try{
			String sql = "select * from (select rownum as rownumber, tmp.* from (select *  from tb_information t where t.infstatus = 22 and t.inftype = "+id+" order by infdate desc) tmp)"
					+ " where rownumber >= "+firstResultNum+" and rownumber < = " + lastResultNum;			
			infoList = this.getList(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return infoList;
	}
	
	@Override
	public ProductObj getproductById(Long id){
		try{
			String where = "ProductId = :ProductId";
			Map<String , Object> param = new HashMap<String , Object>();
			param.put("ProductId", id);
			ProductObj obj = productDao.find(id);
			return obj;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}