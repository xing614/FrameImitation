package simpleIOCAndAOP.ioc;

import org.junit.Test;

public class SimpleIOCTest {

	@Test
	public void getBean() throws Exception {
		String location = SimpleIOC.class.getClassLoader().getResource("ioc.xml").getFile();
		SimpleIOC sim = new SimpleIOC(location);
		Wheel wheel = (Wheel)sim.getBean("wheel");
		System.out.println(wheel);
		Car car = (Car)sim.getBean("car");
		System.out.println(car);
	}
}
