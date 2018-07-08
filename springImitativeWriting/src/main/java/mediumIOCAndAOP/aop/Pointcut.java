package mediumIOCAndAOP.aop;

/**
 * 切点
 * @author liang
 *
 */
public interface Pointcut {

	ClassFilter getClassFilter();
	
	MethodMatcher getMethodMatcher();
	
}
