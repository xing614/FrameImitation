package mediumIOCAndAOP.aop.baseOnJDK;

import java.lang.reflect.Method;

import org.junit.Test;

import mediumIOCAndAOP.aop.AdvisedSupport;
import mediumIOCAndAOP.aop.MethodMatcher;
import mediumIOCAndAOP.aop.TargetSource;
import simpleIOCAndAOP.aop.HelloService;
import simpleIOCAndAOP.aop.HelloServiceImpl;

public class JdkDynamicAopProxyTest {

	@Test
    public void getProxy() throws Exception {
        System.out.println("---------- no proxy ----------");
        HelloService helloService = new HelloServiceImpl();
        helloService.sayHelloWorld();

        System.out.println("\n----------- proxy -----------");
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setMethodInterceptor(new LogInterceptor());

        TargetSource targetSource = new TargetSource(
                helloService, HelloServiceImpl.class, HelloServiceImpl.class.getInterfaces());
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodMatcher(new MethodMatcher() {
			
			@Override
			public boolean matchers(Method method, Class beanClass) {
				// TODO Auto-generated method stub
				return true;
			}
		});

        helloService = (HelloService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        helloService.sayHelloWorld();
    }
}
