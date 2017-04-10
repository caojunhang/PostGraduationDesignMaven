package cn.edu.ncut.istc.dao.mongo;

import java.util.List;
import java.util.Map;

/**
 * Created by lixiwei-mac on 16/4/8.
 */
public interface MongoBase<T> {
    //添加
    public void insert(T object, String collectionName);
    //根据条件查找
    public T findOne(String key ,Map<String, Object> params, String collectionName);
    //修改
    public void update(Map<String, Object> params, String collectionName);
    //创建集合
    public void createCollection(String collectionName);
    //根据条件删除
    public void remove(Map<String, Object> params, String collectionName);
    //无条件查找
    public List<T> findAll(String collectionName);
    //清空表
    public void removeCollection(String collectionName);

}