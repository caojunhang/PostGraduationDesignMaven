package cn.edu.ncut.istc.test.plugin;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.junit.Test;

import java.util.Set;

/**
 * @author lixiwei-mac
 * @create 11:44
 */
public class TestMongodb {
    @Test
    public void testMongodb() {
        try {
            // 连接到 mongodb 服务
            Mongo mongo = new Mongo("10.17.199.41", 27017);
            //根据mongodb数据库的名称获取mongodb对象 ,
            DB db = mongo.getDB("istc");
            Set<String> collectionNames = db.getCollectionNames();
            // 打印出test中的集合
            for (String name : collectionNames) {
                System.out.println("collectionName===" + name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}