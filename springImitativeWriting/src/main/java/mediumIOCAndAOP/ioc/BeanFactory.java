package mediumIOCAndAOP.ioc;
/**
 * bean工厂类，与spring同
 * @author liang
 *
 */
public interface BeanFactory {

	Object getBean(String beanId) throws Exception;
}
