package mediumIOCAndAOP.ioc;

public interface BeanPostProcessor {

	//bean初始化方法调用前被调用
    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;
    //bean初始化方法调用后被调用
    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
}
