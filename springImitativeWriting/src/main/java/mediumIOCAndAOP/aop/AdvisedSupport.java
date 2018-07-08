package mediumIOCAndAOP.aop;

import org.aopalliance.intercept.MethodInterceptor;
/**
 * 通知用的
 * @author liang
 *
 */
public class AdvisedSupport {

	private TargetSource targetSource;//目标元
	
	private MethodInterceptor methodInterceptor;//类似JDK的InvocationHandler
	
	private MethodMatcher methodMatcher;//方法匹配器

	public TargetSource getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(TargetSource targetSource) {
		this.targetSource = targetSource;
	}

	public MethodInterceptor getMethodInterceptor() {
		return methodInterceptor;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}

	public void setMethodMatcher(MethodMatcher methodMatcher) {
		this.methodMatcher = methodMatcher;
	}
	
	
	
}
