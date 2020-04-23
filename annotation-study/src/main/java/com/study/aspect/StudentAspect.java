package com.study.aspect;

import com.study.annotation.OssPicture;
import com.study.config.OssEnvironment;
import com.study.constant.OperateType;
import com.study.entity.BindDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 09:09
 */
@Aspect  //把这个类设置为切面类
@Component //创建一个切面类叫给spring管理
public class StudentAspect {


    //配置切入点方法
    /*因为注解只能写在方法上*/
    @Pointcut(value = "@annotation(com.study.annotation.OssBindMapping)")
    private void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.err.println("环绕开始");

        //方法签名
        //signature.getDeclaringType() 拿到方法所在的类对象 com.study.service.StudentService
        //获取方法名： signature.getName() 执行结果:newStudent
        Signature signature = joinPoint.getSignature();
        //获取方法的操作类型
        //Integer operateType = OssEnvironment.getOperateMap().get(signature.getDeclaringType()).get(signature.getName());

        //构造新的绑定集合
        List<BindDto> newBindList = new ArrayList<>();
        //根据入参添加新的绑定集合
        //addBindMapByAllArg(joinPoint, newBindList);

       /* switch (operateType) {
            case OperateType.ADD:
                //添加时，不在业务前做其他操作
                break;
            case OperateType.UPDATE:
                //获取改变前旧的bindDto

                break;
        }*/

        Object proceed = joinPoint.proceed();
        System.out.println(proceed);
        addBindMapByAllArg(joinPoint, newBindList);

        //测试异常是否回滚，注解加在service层，这里切入点设定为dao层，会被回滚
        //int i=1/0
        System.err.println("环绕结束");
        return proceed;
    }

    private void addBindMapByAllArg(ProceedingJoinPoint joinPoint, List<BindDto> bindList) {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(arg -> {
            //入参拿的到类对象(通过getClass获取类对象效率极高，因为对象都加载了，那么class肯定也加载了）
            //保存一份Map<Class,Field[]> 字节码对应 该类所有Field集合
            //System.err.println("入参类型"+arg.getClass()+" 入参值"+arg.toString());
            List<Field> fields = OssEnvironment.getFieldMap().get(arg.getClass());
            if (fields != null) {
                addBindMapToList(fields, arg, bindList);
            }
        });
    }

    /**
     * 从该字段中生成资源绑定映射
     *
     * @param fields 该入参中包含的字段
     * @param object 入参对象实例
     */
    public void addBindMapToList(List<Field> fields, Object object, List<BindDto> bindList) {

        String businessId = getBusinessId(fields, object);
        //跳过第一个，因为第一个代表businessId
        fields.stream().skip(1).forEach(field -> {

            try {
                System.err.println("环绕通知获取值"+field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //每一个字段可能会对应多个绑定:注解->业务类型->url->单图/多图
            addBindDtoByField(businessId, field, object, bindList);
        });

    }

    /**
     * 根据字段获取绑定关系
     */
    private void addBindDtoByField(String businessId, Field field, Object object, List<BindDto> bindDtoList) {

        OssPicture annotation = field.getDeclaredAnnotation(OssPicture.class);
        String bizType = annotation.bizType();
        String resourcePath = getResourcePath(field, object);
        if (StringUtils.isEmpty(resourcePath)) {
            return;
        }
        String[] urls = resourcePath.split(",");
        Arrays.stream(urls).forEach(url -> {
            BindDto bindDto = new BindDto();
            bindDto.setBusinessId(businessId);
            bindDto.setBizType(bizType);
            bindDto.setResourcePath(url);
            bindDtoList.add(bindDto);
        });

    }

    /**
     * 得到该字段的资源路径
     *
     * @param field  单个属性
     * @param object 入参实例
     * @return 返回资源路径（可能为空，也可能是多个）
     */
    private String getResourcePath(Field field, Object object) {
        try {
            Object o = field.get(object);
            if (o==null){
                return null;
            }
            return o.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到businessId
     *
     * @param fields
     * @param object
     * @return
     */
    private String getBusinessId(List<Field> fields, Object object) {
        String businessId = null;
        try {
            Object o = fields.get(0).get(object);
            if (o==null){
                return null;
            }
            businessId = o.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return businessId;
    }
}
