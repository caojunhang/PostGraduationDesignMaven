package cn.edu.ncut.istc.test.plugin;

import cn.edu.ncut.istc.common.util.FileUtils;
import cn.edu.ncut.istc.dao.mongo.ProductMongoDao;
import cn.edu.ncut.istc.model.ProductMongo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author lixiwei-mac
 * @create 12:01
 */
@Component
public class TestMongoProduct
{

    @Autowired
    private static ProductMongoDao productMongoDao;
    private static ClassPathXmlApplicationContext app;
    private static String collectionName;
//    @Autowired
    private static MongoTemplate mongoTemplate;

    @BeforeClass
    public static void initSpring() {
        try {
            app = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/applicationContext.xml"});
            productMongoDao = (ProductMongoDao) app.getBean("productMongoDaoImpl");
            mongoTemplate = (MongoTemplate) app.getBean("mongoTemplate");

            collectionName = "product";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdd() {

        //添加一百个product
        for (int i = 0; i < 10; i++) {
            ProductMongo product = new ProductMongo();
            int t = new Random().nextInt(999999999);
            product.setProductId(t);
            product.setContent("吃饺子吗,"+t);
            productMongoDao.insert(product, collectionName);
        }
//        System.out.println(productMongoDao.findAll(collectionName));
    }
    @Test
    public void testRemoveCollection()
    {
        productMongoDao.removeCollection(collectionName);
    }
    @Test
    public void testFind()
    {

        Map<String, Object> params = new HashMap<String, Object>();
        String key = "productId";
        params.put(key,new Integer(5));
        ProductMongo productMongo = productMongoDao.findOne(key,params, collectionName);
        System.out.println(productMongo.toString());
    }
    @Test
    public void testCreateFullTextIndex()
    {
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("content")
                .named("content_index")
                .withLanguageOverride("zhs")
//                .withDefaultLanguage("zhs")
                .build();
        mongoTemplate.indexOps(collectionName).ensureIndex(textIndex);
    }
    //分词与模糊查询分值关系测试

    // 全文检索测试
    @Test
    public void testFullText()
    {
        // 创建全文索引



        //搜索
//        Query query = Query.query(Criteria.where("productId").exists(true)).
//                addCriteria(TextCriteria.
//                        forDefaultLanguage(). // effectively the same as forDefaultLanguage() here
//                        matching(" 123890njxn983x98n123xd n789y 879b 78b9g 吃饺子吗 bg n97898 b7g 9g8 796f")
//                );
//
//        List<ProductMongo> result = mongoTemplate.find(query,ProductMongo.class,collectionName);
//        for (ProductMongo product : result)
//        {
//            System.out.println(product.toString());
//        }
//        Sort sort = new Sort("score");
//        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("spring", "data");
//        List<FullTextDocument> result = repository.findAllBy(criteria, sort);
//
//        criteria = TextCriteria.forDefaultLanguage().matching("film");
//        Page<FullTextDocument> page = repository.findAllBy(criteria, new PageRequest(1, 1, sort));
//        List<FullTextDocument> result = repository.findByTitleOrderByScoreDesc("mongodb", criteria);
    }
//    @Test
//    public void testUpdate() {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("id", 1);//此处Value必须要和Model数据类型一致
//        User user = userDao.findOne(params, collectionName);
//        System.out.println("user.name===" + user.getName());
//        System.out.println("=============update==================");
//        params.put("name", "hello");
//        userDao.update(params, collectionName);
//        user = userDao.findOne(params, collectionName);
//        System.out.println("user.name===" + user.getName());
//    }
//
//    @Test
//    public void testRemove()
//    {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("id", 2);
//        userDao.remove(params, collectionName);
//        User user = userDao.findOne(params, collectionName);
//        System.out.println("user==" + user);
//    }
//    @Test
//    public void findAll()
//    {
//        List<User> mongoUsers = userDao.findAll(collectionName);
//        for (User user : mongoUsers)
//        {
//            System.out.println(user.toString());
//        }
//    }

//    @Test
//    public void addArticles() throws IOException
//    {
//        String collection = "articles";
//        String path = "/Users/lixiwei-mac/Desktop/";
//        for (int i = 1 ; i <= 6 ; i++)
//        {
//            File file = new File(path + i + ".txt");
//            String content = FileUtils.readFileToString(file);
//            Article article = new Article();
//            article.setId(i);
//            article.setContent(content);
//            articleDao.insert(article,collection);
//        }
//    }
    public static void main(String[] args)
    {
        String str = FileUtils.readFileByLines("C:\\Users\\51195\\Desktop\\MongoText\\UTF8格式附件测试.txt");
        int length = str.split("，|。| ").length;
        System.out.println("length:" + length);
    }
}

