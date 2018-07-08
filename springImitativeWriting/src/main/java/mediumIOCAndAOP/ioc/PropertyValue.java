package mediumIOCAndAOP.ioc;
/**
 * bean的Property参数，包括 name和value
 * @author liang
 *
 */
public class PropertyValue {
	private String name;
	private Object value;//这个值为String或者BeanReference
	public PropertyValue(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}
	
}
