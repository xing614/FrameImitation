package mediumIOCAndAOP.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 
 * @author liang
 *
 */
public class ReflectiveMethodInvocation implements MethodInvocation{

	protected Object target;//反射的目标类
	protected Method method;//方法
	protected Object[] arguments;//方法参数
	
	
	
	public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

	@Override
	public Object[] getArguments() {
		// TODO Auto-generated method stub
		return arguments;
	}

	@Override
	public AccessibleObject getStaticPart() {
		// TODO Auto-generated method stub
		return method;
	}

	@Override
	public Object getThis() {
		// TODO Auto-generated method stub
		return target;
	}

	@Override
	public Object proceed() throws Throwable {
		// TODO Auto-generated method stub
		return method.invoke(target, arguments);
	}

	@Override
	public Method getMethod() {
		// TODO Auto-generated method stub
		return method;
	}

}
