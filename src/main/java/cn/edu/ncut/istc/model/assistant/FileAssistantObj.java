package cn.edu.ncut.istc.model.assistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import antlr.collections.impl.LList;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

/**
 * 文件辅助对象类，用来保存文件的基本信息
 * 
 * @author pilchard
 * 
 */
public class FileAssistantObj extends BaseModel implements
		java.io.Serializable, Comparable<FileAssistantObj> {
	public FileAssistantObj(String fileid, String filebasepath) {
		super();
		this.fileid = fileid;
		this.filebasepath = filebasepath;

	}

	protected transient static final Logger logger = Logger
			.getLogger(FileAssistantObj.class);
	private String fileid;
	// 文件扩展名
	private String fileext;
	// 记录上传的文件总块数
	private Integer totalblocks;
	// 记录已经完成上传的文件块的编号
	private ArrayList<Integer> readyBlockids = new ArrayList<Integer>();
	// 记录已经完成上传的文件块的编号
	private ArrayList<Integer> neeBlockids = new ArrayList<Integer>();
	private String localpath;
	// 文件上传完成标识0:未完成；1:已完成，但是未合并；2：完成并合并成功
	private Integer finishflag;
	// 文件总大小
	private Long filesize;
	// 文件hash编码
	private String hashcode;
	// 文件根目录
	private String filebasepath;
	// 当前传输的块号
	private Integer currentBlocknum;
	// 当前传输块数据
	private byte[] blockData;
	// 是否新文件
	private Boolean isNew;
	//自定义目录
	private String selfpath;
	
	
	private String path = null;
	

	public ArrayList<Integer> getNeeBlockids() {
		return neeBlockids;
	}

	public void setNeeBlockids(ArrayList<Integer> neeBlockids) {
		this.neeBlockids = neeBlockids;
	}

	public String getLocalpath() {
		return localpath;
	}

	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFileext() {
		return fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	public Integer getTotalblocks() {
		return totalblocks;
	}

	public void setTotalblocks(Integer totalblocks) {
		this.totalblocks = totalblocks;
	}

	public ArrayList<Integer> getReadyBlockids() {
		return readyBlockids;
	}

	public void setReadyBlockids(ArrayList<Integer> readyBlockids) {
		this.readyBlockids = readyBlockids;
	}

	public Integer getFinishflag() {
		return finishflag;
	}

	public void setFinishflag(Integer finishflag) {
		this.finishflag = finishflag;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getFilebasepath() {
		return filebasepath;
	}

	public void setFilebasepath(String filebasepath) {
		this.filebasepath = filebasepath;
	}

	public Integer getCurrentBlocknum() {
		return currentBlocknum;
	}

	public void setCurrentBlocknum(Integer currentBlocknum) {
		this.currentBlocknum = currentBlocknum;
	}

	public byte[] getBlockData() {
		return blockData;
	}

	public void setBlockData(byte[] blockData) {
		this.blockData = blockData;
	}
	

	public String getSelfpath() {
		return selfpath;
	}

	public void setSelfpath(String selfpath) {
		this.selfpath = selfpath;
	}
	
	//文件名称
	private String filename;
	private String statusPath;

	@Override
	/**
	 * 比较是否同一个FileAssistantObj 类
	 */
	public int compareTo(FileAssistantObj o) {
		// TODO Auto-generated method stub
		return this.getFileid().compareTo(o.getFileid());
	}

	public void WriteObject() {

		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(new File(
					filebasepath + File.separator + this.path + File.separator
							+ "status")));
			out.writeObject(this);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("读写文件失败!");
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("读写文件失败!");
			}
		}
	}

	public FileAssistantObj readObject() {
		FileAssistantObj o = null;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(new File(
					filebasepath + File.separator + this.path + File.separator
							+ "status")));
			o = (FileAssistantObj) in.readObject();
		} catch (Exception e) {
			logger.error("读写文件失败!");
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("读写文件失败!");
			}
		}
		return o;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("FileAssistantObj", FileAssistantObj.class);
		xstream.setMode(XStream.NO_REFERENCES);

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}

	// 文件如果较大可以用多线程实现
	private boolean unionFile() {
		boolean flag = false;
		File fin = null;
		FileOutputStream out = null;
		// 构建文件输出流
		try {
			File fout = new File(filebasepath + File.separator + this.path
					+ File.separator + filename + fileext);
			byte[] buffer = new byte[1024];
			out = new FileOutputStream(fout);
			long off = 0;
			for (int i = 1; i <= totalblocks; i++) {
				// 打开文件输入流
				fin = new File(filebasepath + File.separator + this.path
						+ File.separator + i + "");
				FileInputStream in = new FileInputStream(fin);
				// 从输入流中读取数据，并写入到文件数出流中
				int c = 0;
				while ((c = in.read(buffer)) != -1) {
					out.write(buffer, 0, c);
				}
				in.close();
				fin.delete();
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error(e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
		File status = new File(statusPath);
		status.delete();
		
		return flag;
	}

	public FileAssistantObj saveBlock(String fname) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH)+1; 
		int date = c.get(Calendar.DATE); 
		
		path = Integer.toString(year)+ File.separator + Integer.toString(month)+ File.separator + Integer.toString(date);
		String directory = this.filebasepath + File.separator + this.path;
		statusPath = directory + File.separator + "status";
		
	    // 判断文件是否存在
		FileAssistantObj obj = null;
		filename = fname;
		
		
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
		else{
			if(this.isNew&&file.isDirectory()){
				File status = new File(statusPath);
				status.delete();
//				//清空目录
//				for(File f:file.listFiles()){
//					f.delete();
//				}
			}
		}
		
		// 写块
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(directory + File.separator
					+ this.currentBlocknum));
			out.write(this.blockData);
			out.flush();
			out.close();
			// 更新文件状态
			File status = new File(statusPath);
			obj = updateStatus(status, obj);

		} catch (Exception e) {
			obj.setFinishflag(0);
			logger.error(e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {

				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
		if (obj != null)
			obj.WriteObject();
		return obj;

	}
	public FileAssistantObj saveBlock(String filepath,String filename) {
		File file = new File(filepath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 写块
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(filepath+"/"+filename));
			out.write(this.blockData);    
			out.flush();
			out.close();
		} catch (Exception e) {
			this.setFinishflag(0);
			logger.error(e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		this.setFinishflag(2);
		this.getReadyBlockids().add(this.currentBlocknum);
		return this;

	}


	private FileAssistantObj updateStatus(File status, FileAssistantObj obj) {
		// 更新状态文件
		// 新文件重新上传
		if (this.getIsNew()) {
			status.delete();
		}
		if (status.exists()) {
			obj = this.readObject();
			obj.readObject();
			// 新块
			if (!obj.getReadyBlockids().contains(this.currentBlocknum)) {
				obj.getReadyBlockids().add(this.currentBlocknum);
				obj.setFinishflag(1);
				if (obj.getReadyBlockids().size() >= (obj.getTotalblocks())) {
					if (obj.getTotalblocks() == 1) {
						obj.setFinishflag(2);
						// 文件改名即可
						this.renameFile(obj);
					} else if (unionFile())
						obj.setFinishflag(2);
				}
			}
		} else {
			obj = this;
			obj.getReadyBlockids().add(this.currentBlocknum);
			obj.setFinishflag(1);
			if (obj.getReadyBlockids().size() >= (obj.getTotalblocks())) {
				if (obj.getTotalblocks() == 1) {
					obj.setFinishflag(2);
					// 文件改名即可
					this.renameFile(obj);
				} else if (unionFile())
					obj.setFinishflag(2);
			}
		}
		obj.setBlockData(null);
		return obj;
	}

	private void renameFile(FileAssistantObj obj) {
		//将obj.getFileid()改成按日期分文件夹的path
		File file = new File(obj.getFilebasepath() + File.separator
				+ this.path + File.separator + obj.getCurrentBlocknum());
//		File dest = new File(obj.getFilebasepath() + File.separator
//				+ obj.getFileid() + File.separator + obj.getFileid()
//				+ obj.getFileext());
		File dest = new File(obj.getFilebasepath() + File.separator
				+ this.path + File.separator + filename
				+ obj.getFileext());
		
		if (dest.exists())
			dest.delete();
		file.renameTo(dest);
	}
}
