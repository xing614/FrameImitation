package mediumIOCAndAOP.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来保存Property注入的变量与数据
 * 例如<property name="name" value="xiaoqi">
 * @author liang
 *
 */
public class PropertyValues {
	private List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();
    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }
}
