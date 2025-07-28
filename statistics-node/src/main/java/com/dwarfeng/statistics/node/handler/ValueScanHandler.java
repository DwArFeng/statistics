package com.dwarfeng.statistics.node.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ValueScanHandler implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueScanHandler.class);

    @Autowired
    private ApplicationContext applicationContext;

    private final Set<String> propertyNames = new HashSet<>();

    // 要扫描的配置文件目录
    private static final String CONFIG_DIR = "conf";

    // 要排除的属性前缀
    private static final Set<String> EXCLUDED_PREFIXES = new HashSet<>(Arrays.asList("example"));

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("==== 上下文刷新完成，开始扫描 @Value 注解 ====");

        // 获取所有 bean 名称
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            try{
                Object bean = applicationContext.getBean(beanName);
                scanBean(bean);
            }catch(Exception e){
            }
        }

        printResults();

        Map<String, Object> customProperties = scanConfigDirectory(CONFIG_DIR);
        logCustomProperties(customProperties);
    }

    private void scanBean(Object bean) {
        Class<?> clazz = bean.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(org.springframework.beans.factory.annotation.Value.class)) {
                    String value = field.getAnnotation(org.springframework.beans.factory.annotation.Value.class).value();
                    extractPropertyNames(value);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private void extractPropertyNames(String valueExpression) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\$\\{(.*?)(:|})");
        java.util.regex.Matcher matcher = pattern.matcher(valueExpression);
        while (matcher.find()) {
            propertyNames.add(matcher.group(1));
        }
    }

    private void printResults() {
        LOGGER.info("共找到 {} 个@Value 配置属性",propertyNames.size());
    }

    private Map<String, Object> scanConfigDirectory(String directoryPath) {
        Map<String, Object> allProperties = new HashMap<>();
        File configDir = new File(directoryPath);

        if (!configDir.exists() || !configDir.isDirectory()) {
            throw new IllegalArgumentException("配置目录不存在: " + directoryPath);
        }

        // 递归遍历目录下的所有配置文件
        for (File file : Objects.requireNonNull(configDir.listFiles())) {
            if (file.isFile() && isConfigFile(file)) {
                try {
                    Map<String, Object> fileProperties = loadConfigFile(file);
                    allProperties.putAll(fileProperties);
                } catch (IOException e) {
                    LOGGER.error("加载配置文件失败: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
            else if (file.isDirectory()) {
                allProperties.putAll(scanConfigDirectory(file.getAbsolutePath()));
            }
        }

        return filterCustomProperties(allProperties);
    }

    private boolean isConfigFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".properties") ||
                fileName.endsWith(".yml") ||
                fileName.endsWith(".yaml");
    }

    private Map<String, Object> loadConfigFile(File file) throws IOException {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".properties")) {
            Resource resource = new FileSystemResource(file);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            return props.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            Map.Entry::getValue
                    ));
        }
        else if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
            try (FileInputStream input = new FileInputStream(file)) {
                Yaml yaml = new Yaml();
                Map<String, Object> yamlMap = yaml.load(input);
                return flattenMap("", yamlMap);
            }
        }

        return Collections.emptyMap();
    }

    // 将嵌套的 YAML 结构展平为单层 Map
    private Map<String, Object> flattenMap(String prefix, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();

            if (entry.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) entry.getValue();
                result.putAll(flattenMap(key, nestedMap));
            } else {
                result.put(key, entry.getValue());
            }
        }

        return result;
    }

    private Map<String, Object> filterCustomProperties(Map<String, Object> allProperties) {
        return allProperties.entrySet().stream()
                .filter(entry -> EXCLUDED_PREFIXES.stream()
                        .noneMatch(prefix -> entry.getKey().startsWith(prefix)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void logCustomProperties(Map<String, Object> properties) {
        if (properties.isEmpty()) {
            LOGGER.warn("未在目录中找到自定义配置属性: {}" ,CONFIG_DIR);
            return;
        }
        LOGGER.info("共找到 {} 个自定义配置属性", properties.size());

        for(String p:propertyNames){
            boolean isExist = false;
            for(Map.Entry<String,Object> entry:properties.entrySet()){
                if(entry.getKey().equals(p)){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                LOGGER.warn("{} 无配置",p);
            }
        }
    }
}