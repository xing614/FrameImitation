package mediumIOCAndAOP.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器，判断切点参数与方法是否匹配
 * @author liang
 *
 */
public interface MethodMatcher {

	boolean matchers(Method method,Class beanClass);
	
}
