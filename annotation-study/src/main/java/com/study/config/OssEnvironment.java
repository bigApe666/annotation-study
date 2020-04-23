package com.study.config;

import com.study.annotation.*;
import com.study.helper.ProxyHelper;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 11:20
 */
@Component
public class OssEnvironment implements CommandLineRunner, ApplicationContextAware {

    private volatile ApplicationContext applicationContext;

    private static Map<Class, List<Field>> fieldMap = new HashMap<>();

    public static Map<Class, Map<String, Integer>> getOperateMap() {
        return operateMap;
    }

    /**
     * Class 类对象
     * Method 方法
     * Integer 操作类型 1：ADD 2：UPDATE
     */
    private static Map<Class, Map<String,Integer>> operateMap = new HashMap<>();

    public static Map<Class, List<Field>> getFieldMap() {
        return fieldMap;
    }

    @Override
    public void run(String... args) throws Exception {
        initFieldMap();
        initOperateMap();
    }

    /**
     * 初始化操作map
     */
    private void initOperateMap() throws Exception {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Service.class);
        for (String key : beansWithAnnotation.keySet()) {
            Class targetObject = ProxyHelper.getTargetObject(beansWithAnnotation.get(key)).getClass();
            setOperateInMap(targetObject);
        }
    }

    /**
     * 查看类中方法，只要包含目标注解，就获取里面操作类型
     */
    private void setOperateInMap( Class<?> objectClass) {
        Map<String,Integer> methodList = new HashMap<>();
        Method[] methods = objectClass.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {

            OssBindMapping[] annotation = method.getAnnotationsByType(OssBindMapping.class);
            if (annotation!=null && annotation.length>0) {
                methodList.put(method.getName(),annotation[0].operate());
            }
        });

        operateMap.put(objectClass,methodList);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 初始化map
     */
    private void initFieldMap() throws Exception {
        //没交给Spring管理获取不到
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(OssService.class);
        for (String key : beansWithAnnotation.keySet()) {
            //得到具体的每一个类
            Class targetObject = ProxyHelper.getTargetObject(beansWithAnnotation.get(key)).getClass();

            //得到每个类声明的字段
            Field[] fields = targetObject.getDeclaredFields();
            //设置机构id字段
            setBusinessIdFieldInFieldMap(fields, targetObject);
            //设置图片字段
            setOssPictureInFieldMap(fields, targetObject);
        }

    }


    /**
     * 第一个元素存放业务id
     *
     * @param fields
     * @param objectClass
     */
    private void setBusinessIdFieldInFieldMap(Field[] fields, Class objectClass) {
        List<Field> fieldArrayList = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            OssBusinessId[] annotationsByType = field.getAnnotationsByType(OssBusinessId.class);
            if (annotationsByType.length > 0) {
                field.setAccessible(true);
                fieldArrayList.add(field);
            }
        });

        if (fieldArrayList.size() == 0) {
            throw new RuntimeException("oss业务id为空");
        }
        if (fieldArrayList.size() != 1) {
            throw new RuntimeException("oss业务id重复");
        }

        fieldMap.put(objectClass, fieldArrayList);
    }

    /**
     * 之后的字段存放图片业务字段
     *
     * @param fields
     * @param objectClass
     */
    private void setOssPictureInFieldMap(Field[] fields, Class objectClass) {
        List<Field> fieldArrayList = fieldMap.get(objectClass);
        Arrays.stream(fields).forEach(field -> {
            OssPicture[] annotationsByType = field.getAnnotationsByType(OssPicture.class);
            if (annotationsByType.length > 0) {
                field.setAccessible(true);
                fieldArrayList.add(field);
            }
        });

        fieldMap.put(objectClass, fieldArrayList);
    }
}
