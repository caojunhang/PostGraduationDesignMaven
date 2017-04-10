package cn.edu.ncut.istc.common.plugin;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取工具
 * Created by lixiwei on 2016/4/13.
 */
public class ConfigSingleton {
    private final static Logger logger = Logger
            .getLogger(ConfigSingleton.class);

    private static ConfigSingleton instance = new ConfigSingleton();

    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private ConfigSingleton() {
        properties = new Properties();
        InputStream is = null;
        try {
            is = ConfigSingleton.class
                    .getResourceAsStream("/config.properties");
            properties.load(is);
        } catch (Exception e) {
            logger.debug("装载配置文件出错，具体堆栈信息如下：");
            logger.debug(e.toString());
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                logger.debug(e.toString());
            }
        }
    }

    public static synchronized ConfigSingleton getInstance() {
        return instance;
    }
}