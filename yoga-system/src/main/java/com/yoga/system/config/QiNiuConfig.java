package com.yoga.system.config;

import com.qiniu.storage.Region;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Data
public class QiNiuConfig {


    private String accessKey;
    private String secretKey;
    private String bucket;
    private Region region;
    private String domainOfBucket;
    private long expireInSeconds;

    private QiNiuConfig(){ //单例设计模式
        Yaml yaml = new Yaml();
        try {
            InputStream in = QiNiuConfig.class.getClassLoader().getResourceAsStream("application.yml");//或者app.yaml
            Map<String, Object> map = yaml.loadAs(in, Map.class);
            accessKey = ((Map<String, Object>) map.get("qiniu")).get("access-key").toString();
            secretKey = ((Map<String, Object>) map.get("qiniu")).get("secret-key").toString();
            bucket = ((Map<String, Object>) map.get("qiniu")).get("bucket").toString();
            domainOfBucket = ((Map<String, Object>) map.get("qiniu")).get("domain-of-bucket").toString();
            expireInSeconds = Long.parseLong(((Map<String, Object>) map.get("qiniu")).get("expire-in-seconds").toString());
            String zoneName = ((Map<String, Object>) map.get("qiniu")).get("region").toString();
            if(zoneName.equals("region0")){
                region = Region.region0();
            }else if(zoneName.equals("region1")){
                region = Region.region1();
            }else if(zoneName.equals("region2")){
                region = Region.region2();
            }else if(zoneName.equals("regionAs0")){
                region = Region.regionAs0();
            }else if(zoneName.equals("regionNa0")){
                region = Region.regionNa0();
            }else{
                throw new Exception("Region对象配置错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static QiNiuConfig instance = new QiNiuConfig();
    public static QiNiuConfig getInstance() {
        return instance;
    }
}


