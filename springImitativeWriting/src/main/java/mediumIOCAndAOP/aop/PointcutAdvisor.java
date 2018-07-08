package mediumIOCAndAOP.aop;

/**
 * 返回切点
 * @author liang
 *
 */
public interface PointcutAdvisor extends Advisor{

	Pointcut getPointcut();
	
}
