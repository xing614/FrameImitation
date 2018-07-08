package mediumIOCAndAOP.ioc;

import java.io.FileNotFoundException;

/**
 * 用来给BeanFactory打下手，加载和解析配置文件
 * 它的子类XmlBeanDefinitionReader 用来解析xml的
 * @author liang
 *
 */
public interface BeanDefinitionReader {
	void loadBeanDefinitions(String location) throws FileNotFoundException, Exception;
}
