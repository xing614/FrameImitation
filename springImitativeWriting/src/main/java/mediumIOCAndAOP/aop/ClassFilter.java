package mediumIOCAndAOP.aop;

/**
 * 类过滤器接口，用来判断切点参数是否与类匹配
 * @author liang
 *
 */
public interface ClassFilter {

	/**
	 * 判断是否匹配
	 * @param beanClass
	 * @return
	 */
	boolean matchers(Class beanClass);
}
