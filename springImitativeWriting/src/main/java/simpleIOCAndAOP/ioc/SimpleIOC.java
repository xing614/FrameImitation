package simpleIOCAndAOP.ioc;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * IOC简单实现类
 * @author liang
 *
 */
public class SimpleIOC {
	private Map<String,Object> beanMap = new HashMap<String,Object>();
	
	public SimpleIOC(String location) throws Exception {
		loadBeans(location);
	}
	
	public Object getBean(String name) {
		Object bean = beanMap.get(name);
		if(bean == null)
			throw new IllegalArgumentException("没有该bean:"+name);
		return bean;
	}
	/**
	 * 加载Xml中的bean
	 * <bean id="User" class="com.User">
     *   <property name="username" value="admin" /> 这是变量值
     *   <property name="wheel" ref="wheel"/>	这是注入某个类，类id为wheel,对应某个bean
     * </bean>
	 * @param location
	 */
	private void loadBeans(String location) throws Exception{
		FileInputStream fileInputStream = new FileInputStream(location);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(fileInputStream);
		Element root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for(int i=0,leng = nodes.getLength();i<leng;i++) {
			Node node = nodes.item(i);
			if(node instanceof Element) {
				Element ele = (Element)node;
				String id = ele.getAttribute("id");
				String classes = ele.getAttribute("class");
				Class classBean = null;
				try {
					classBean = Class.forName(classes);
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	                return;
	            }
				Object bean = classBean.newInstance();//初始化
				NodeList propertyNodes = ele.getElementsByTagName("property");
				for(int j=0,proLeng=propertyNodes.getLength();j<proLeng;j++) {
					Node propertyNode = propertyNodes.item(j);
					if(propertyNode instanceof Element) {
						Element proEle = (Element)propertyNode;
						String name = proEle.getAttribute("name");
						String value = proEle.getAttribute("value");
						Field declaredField = bean.getClass().getDeclaredField(name);//bean中变量为name的字段
						declaredField.setAccessible(true);//设置为可访问
						if(value!=null && value.length()>0) {
							declaredField.set(bean, value);// 将属性值填充到相关字段中
						}else {
							String ref = proEle.getAttribute("ref");
							if(ref == null || ref.length()==0) {
								throw new IllegalAccessException("bean为"+bean+"的ref error");
							}
							declaredField.set(bean, getBean(ref));
						}
						
						registerBean(id,bean);
					}
				}
			}
		}
	}
	
	private void registerBean(String id,Object bean) {
		beanMap.put(id, bean);
	}
}
