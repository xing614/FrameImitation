package mediumIOCAndAOP.aop.baseOnJDK;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;

import mediumIOCAndAOP.aop.AbstractAopProxy;
import mediumIOCAndAOP.aop.AdvisedSupport;
import mediumIOCAndAOP.aop.MethodMatcher;
import mediumIOCAndAOP.aop.ReflectiveMethodInvocation;

/**
 * 基于JDK的动态代理生成器
 * @author liang
 *
 */
public class JdkDynamicAopProxy extends AbstractAopProxy implements InvocationHandler{

	public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
		super(advisedSupport);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 为目标bean生成代理对象
	 */
	@Override
	public Object getProxy() {
		// TODO Auto-generated method stub
		return Proxy.newProxyInstance(getClass().getClassLoader(), advisedSupport.getTargetSource().getInterfaces(), this);
	}

	/**
	 * InvocationHandler 接口中的 invoke 方法具体实现，封装了具体的代理逻辑
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		MethodMatcher methodMatcher = advisedSupport.getMethodMatcher();
		//使用方法匹配器methodMatcher 测试 bean 中原始方法 method 是否符合匹配规则
		if(methodMatcher !=null && methodMatcher.matchers(method, advisedSupport.getTargetSource().getTargetClass())) {
			MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
			
			//将原始Bean的方法封装成MethodInvocation实现类对象
			//将生成的对象传给 Adivce 实现类对象，执行通知逻辑
			return methodInterceptor.invoke(
					new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args));
		}else {
			//当前 method 不符合匹配规则，直接调用 bean 的原始方法 method
			return method.invoke(advisedSupport.getTargetSource().getTarget(), args);
		}
	}
	

}
