package mediumIOCAndAOP.ioc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 将 xml 配置文件加载到内存中
	获取根标签 <beans> 下所有的 <bean> 标签
	遍历获取到的 <bean> 标签列表，并从标签中读取 id，class 属性
	创建 BeanDefinition 对象，并将刚刚读取到的 id，class 属性值保存到对象中
	遍历 <bean> 标签下的 <property> 标签，从中读取属性值，并保持在 BeanDefinition 对象中
	将 <id, BeanDefinition> 键值对缓存在 Map 中，留作后用
	重复3、4、5、6步，直至解析结束
 * @author liang
 *
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader{

	/**
	 * String为<bean>的id
	 */
	private Map<String,BeanDefinition> registry;
	
	
	public XmlBeanDefinitionReader() {
		this.registry = new HashMap();
	}

	/**
	 * 用来加载解析xml的bean
	 */
	@Override
	public void loadBeanDefinitions(String location) throws FileNotFoundException, Exception {
		// TODO Auto-generated method stub
		FileInputStream fileInputStream = new FileInputStream(location);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(fileInputStream);
		Element element = doc.getDocumentElement();
		parseBeanDefinitions(element);
	}
	/**
	 * 循环解析beans
	 * @param root
	 */
	private void parseBeanDefinitions(Element root) {
		NodeList nodes = root.getChildNodes();
		for(int i=0,nlen = nodes.getLength();i<nlen;i++) {
			Node node = nodes.item(i);
			if(node instanceof Element) {
				Element ele = (Element)node;
				parseBeanDefinition(ele);
			}
		}
	}
	
	/**
	 * 解析Bean
	 * @param ele
	 */
	private void parseBeanDefinition(Element ele) {
		String id = ele.getAttribute("id");
		String className = ele.getAttribute("class");
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClassName(className);
		parseProperty(ele,beanDefinition);
		registry.put(id, beanDefinition);
	}
	
	/**
	 * 循环解析property
	 * @param ele
	 * @param beanDefinition
	 */
	private void parseProperty(Element ele,BeanDefinition beanDefinition) {
		NodeList propertyNodes = ele.getElementsByTagName("property");
		for(int i=0;i<propertyNodes.getLength();i++) {
			Node item = propertyNodes.item(i);
			if(item instanceof Element) {
				Element property = (Element)item;
				String name = property.getAttribute("name");
				String value = property.getAttribute("value");
				if(value != null || value.length()>0) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name,value));
				}else {
					String ref = property.getAttribute("ref");
					if(ref ==null ||ref.length()==0) {
						throw new IllegalArgumentException("ref error");
					}
					BeanReference br = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, br));
				}
			}
		}
	}
	
	public Map<String,BeanDefinition> getRegistry(){
		return this.registry;
	}
	
	

}
