package org.fastframework.mvc;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.fast.framework.core.util.ClassUtil;
import org.fastframework.core.Config;
import org.fastframework.mvc.annotation.Controller;
import org.fastframework.mvc.annotation.RequestMapping;
import org.fastframework.mvc.annotation.RequestMethod;
import org.fastframework.mvc.bean.RequestBody;
import org.fastframework.mvc.method.HandlerMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller类收集器
 * <p>
 * Created by bysocket on 16/7/20.
 */
public class ControllerCollection {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerCollection.class);

    /**
     * Controller Map 请求 -> 方法体 的映射
     */
    public static final Map<RequestBody, HandlerMethod> methodMapping = new LinkedHashMap<>();

    /**
     * Controller扫描包的目录
     */
    private static final String scanPackage = Config.getScanPackage();

    /**
     * 初始化
     *
     */
    public static void init() {
        // 获取到 @Controller 注解的类列表
        List<Class<?>> controllerClassList =  ClassUtil.getClassListByAnnotation(scanPackage, Controller.class);
        if (CollectionUtils.isNotEmpty(controllerClassList)) {
            for (Class<?> controllerClass : controllerClassList) {
                // 获取并遍历所有 Controller 类中的所有方法
                Method[] controllerMethods = controllerClass.getMethods();
                if (ArrayUtils.isNotEmpty(controllerMethods)) {
                    for (Method controllerMethod : controllerMethods) {
                        handlerControllerMethod(controllerMethod,controllerClass);
                    }
                }
            }
        }
    }

    /**
     * 根据 Method 的注解加入 Controller Map 请求 -> 方法体 的映射
     *
     * @param controllerMethod
     * @param controllerClass
     */
    private static void handlerControllerMethod(Method controllerMethod, Class<?> controllerClass) {
        if (controllerMethod.isAnnotationPresent(RequestMapping.class)) {
            String requestPath = controllerMethod.getAnnotation(RequestMapping.class).value();
            RequestMethod requestMethod = controllerMethod.getAnnotation(RequestMapping.class).method();
            methodMapping.put(new RequestBody(requestMethod,requestPath), new HandlerMethod(controllerClass, controllerMethod));
            if(LOGGER.isDebugEnabled()){
            	LOGGER.debug("Mapped {[{}]} onto {}",requestPath,controllerClass.getName()+"."+controllerMethod.getName());
            }
        }
    }

    public static Map<RequestBody, HandlerMethod> getMethodMapping() {
    	return methodMapping;
    }
}