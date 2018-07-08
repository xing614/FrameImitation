package mediumIOCAndAOP.ioc;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.codegen.types.Type;

public class XmlBeanFactory implements BeanFactory{

	/**
	 * 存放BeanDefinition
	 */
	private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();
	
	/**
	 * 存放每个BeanDefinition的name
	 */
	private List<String> beanDefinitionNames = new ArrayList<>();
	
	/**
	 * 存放每个BeanPostProcessor
	 */
	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
	
	/**
	 * 用于载入每个bean
	 */
	private XmlBeanDefinitionReader beanDefinitionReader;
	
	public XmlBeanFactory(String location) throws Exception {
		beanDefinitionReader = new XmlBeanDefinitionReader();
		loadBeanDefinitions(location);
	}
	
	private void loadBeanDefinitions(String location) throws FileNotFoundException, Exception {
		beanDefinitionReader.loadBeanDefinitions(location);//加载解析xml的bean到beanDefinitionReader的registry中
		registerBeanDefinition();//注册到beanDefinitionMap和beanDefinitionNames
		registerBeanPostProcessor();//注册到beanPostProcessors
		
		
	}
	private void registerBeanDefinition() {
		for(Map.Entry<String,BeanDefinition> entry:beanDefinitionReader.getRegistry().entrySet()) {
			String name = entry.getKey();
			BeanDefinition beanDefinition = entry.getValue();
			beanDefinitionMap.put(name,beanDefinition);
			beanDefinitionNames.add(name);
		}
	}
	
	private void registerBeanPostProcessor() throws Exception {
		// TODO Auto-generated method stub
		List beans = getBeansForType(BeanPostProcessor.class);
		for(Object bean:beans) {
			beanPostProcessors.add((BeanPostProcessor)bean);
		}
	}


	/**
	 * 返回type的所有子类beans
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getBeansForType(Class type) throws Exception {
		// TODO Auto-generated method stub
		List beans = new ArrayList();
		for(String beanDefinitionName:beanDefinitionNames) {
			if(type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {//是否是type的子类或子接口
				beans.add(getBean(beanDefinitionName));
			}
		}
		return beans;
	}

	
	@Override
	public Object getBean(String name) throws Exception {
		// TODO Auto-generated method stub
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		if(beanDefinition == null) {
			throw new IllegalArgumentException("没有name为"+name+"的bean");
		}
		Object bean = beanDefinition.getBean();
		if(bean == null) {
			bean = createBean(beanDefinition);//创建bean并注入变量数据
			bean = initializeBean(bean,name);//调用BeanPostProcessor的前后方法
			beanDefinition.setBean(bean);
		}
		return bean;
	}

	private Object initializeBean(Object bean, String name) throws Exception {
		// TODO Auto-generated method stub
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }

        return bean;
	}

	/**
	 * 根据BeanDefinition创建class
	 * @param bd
	 * @return
	 * @throws Exception
	 */
	private Object createBean(BeanDefinition bd) throws Exception {
		Object bean = bd.getBeanClass().newInstance();
		applyPropertyValues(bean,bd);//将数据注入到bean中
		return bean;
		
	}

	private void applyPropertyValues(Object bean, BeanDefinition bd) throws Exception {
		// TODO Auto-generated method stub
		if(bean instanceof BeanFactoryAware) {
			((BeanFactoryAware)bean).setBeanFactory(this);
		}
		//利用反射setXXX方法注入数据
		for(PropertyValue propertyValue:bd.getPropertyValues().getPropertyValues()) {
			Object value = propertyValue.getValue();
			if(value instanceof BeanReference) {
				BeanReference bf= (BeanReference)value;
				value = getBean(bf.getName());
			}
			
			try {
				Method declaredMethod = bean.getClass().getDeclaredMethod(
						"set"+propertyValue.getName().substring(0, 1).toUpperCase()
						+propertyValue.getName().substring(1), 
						value.getClass());
				declaredMethod.setAccessible(true);
				declaredMethod.invoke(bean, value);
			} catch (NoSuchMethodException e) {
				// TODO: handle exception
				Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				declaredField.setAccessible(true);
				declaredField.set(bean, value);
			}
		}
	}
}