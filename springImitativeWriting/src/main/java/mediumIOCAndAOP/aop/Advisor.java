package mediumIOCAndAOP.aop;

import org.aopalliance.aop.Advice;
/**
 * 切面aop的Advice通知
 * @author liang
 *
 */
public interface Advisor {

	Advice getAdvice();
}
