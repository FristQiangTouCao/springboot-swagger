package com.hontian.factory;

import com.hontian.config.ApiConfigBean;
import com.hontian.util.StringUtil;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author weed
 * @date 2020/11/16 0016 16:41
 * @description 配置文件读取器
 */
class ApiReader {

    private static String prefix = "hongtian.api.";

    private static String packages = "packages.";

    private static String defResourcePath = "application.yml";
    // 读取配置文件
    public static ApiConfigBean parseApplication(String resourcePath) {
        if(StringUtils.isEmpty(resourcePath)){ resourcePath = defResourcePath;}
        ApiConfigBean apiConfigBean = new ApiConfigBean();
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(new ClassPathResource(resourcePath, ApiReader.class.getClassLoader()));
        factory.afterPropertiesSet();
        Properties properties = factory.getObject();
        Map<String, String> packagesMap = new HashMap<>();
        properties.entrySet().forEach(item -> {
            String key = (String)item.getKey();
            if(!key.startsWith(prefix)) return;
            String substr = key.substring(prefix.length());
            if(StringUtils.isEmpty(substr)) {
                return;
            }
            if(substr.startsWith(packages)) {
                String packName = substr.substring(packages.length());
                packagesMap.put(packName,String.valueOf(item.getValue()));
            } else {
                try {
                    // 反射获取set方法
                    Method method = ApiConfigBean.class.getMethod("set" + StringUtil.firstWord2Up(substr),String.class);
                    if(method == null) return;
                    method.invoke(apiConfigBean,String.valueOf(item.getValue()));
                } catch (NoSuchMethodException e) {
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        apiConfigBean.setPackages(packagesMap);
        return apiConfigBean;
    }
}
