package simpleIOCAndAOP.aop;

import java.lang.reflect.Method;

/**
 * 前置通知，进入controller前运行
 * @author liang
 *
 */
public class BeforeAdvice implements Advice{

	private Object bean;
	private MethodInvocation methodInvocation;
	
	
	public BeforeAdvice(Object bean, MethodInvocation methodInvocation) {
		super();
		this.bean = bean;
		this.methodInvocation = methodInvocation;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		methodInvocation.invoke();// 在目标方法执行前调用通知
		return method.invoke(bean, args);//反射执行方法
	}

}
