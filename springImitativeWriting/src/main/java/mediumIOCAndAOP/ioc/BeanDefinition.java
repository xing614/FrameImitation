package mediumIOCAndAOP.ioc;
/**
 * 用来封装读取的bean的配置
 * 根据 Bean 配置信息生成相应的 Bean 详情对象
 * <bean id="car" class="ioc.Car"><property></property><property></property></bean>
 * @author liang
 *
 */
public class BeanDefinition {

	private Object bean;
	private Class beanClass;
	private String beanClassName;
	private PropertyValues propertyValues = new PropertyValues();
	public BeanDefinition() {
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public Class getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}
	public String getBeanClassName() {
		return beanClassName;
	}
	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public PropertyValues getPropertyValues() {
		return propertyValues;
	}
	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}
	
	
}
