package mediumIOCAndAOP.ioc;
/**
 * 可让Bean知道自己是从那个beanfactory生成的
 * @author liang
 *
 */
public interface BeanFactoryAware {

	void setBeanFactory(BeanFactory bf) throws Exception;
}
