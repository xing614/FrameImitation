package simpleIOCAndAOP.aop;

import org.junit.Test;

public class SimpleAOPTest {

	@Test
	public void getProxy() {
		MethodInvocation logTask = () -> System.out.println("log task start");//MethodInvocation实现类
		HelloServiceImpl helloService = new HelloServiceImpl();
		BeforeAdvice beforeAdive = new BeforeAdvice(helloService,logTask);//bean和before方法
		HelloService hsProxy = (HelloService)SimpleAOP.getProxy(helloService, beforeAdive);//JDK动态代理的是实现了接口的对象
		hsProxy.sayHelloWorld();
	}
}
