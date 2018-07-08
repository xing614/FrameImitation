package mediumIOCAndAOP.aopConnectIocByAspectJ;

import org.junit.Test;

import mediumIOCAndAOP.ioc.XmlBeanFactory;
import simpleIOCAndAOP.aop.HelloService;

public class XmlBeanFactoryTest {
    @Test
    public void getBean() throws Exception {
        System.out.println("--------- AOP test ----------");
        String location = getClass().getClassLoader().getResource("ioc.xml").getFile();
        XmlBeanFactory bf = new XmlBeanFactory(location);
        HelloService helloService = (HelloService) bf.getBean("helloService");
        helloService.sayHelloWorld();
    }
}
