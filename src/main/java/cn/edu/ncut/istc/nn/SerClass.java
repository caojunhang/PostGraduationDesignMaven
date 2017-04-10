package cn.edu.ncut.istc.nn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
/**
 * 使用Java序列化把对象存储到文件中，再从文件中读出来 注意读取的时候，读取数据的顺序一定要和存放数据的顺序保持一致
 * 
 * @author HK
 * 
 */
public class SerClass extends TestCase {
	
	
	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		// 创建一个User对象
		User user = new User();
		user.setId(1);
		user.setName("HK");
		// 创建一个List对象
		List<String> list = new ArrayList<String>();
		list.add("My name");
		list.add(" is");
		list.add(" Mr XP.Wang");
		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream("C:/wxp.txt"));
			os.writeObject(user);// 将User对象写进文件
			os.writeObject(list);// 将List列表写进文件
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("C:/wxp.txt"));
			User temp = (User) is.readObject();// 从流中读取User的数据
			System.out.println(temp.getId());
			System.out.println(temp.getName());
			List tempList = (List) is.readObject();// 从流中读取List的数据
			for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
				System.out.print(iterator.next());
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
class User implements Serializable {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
