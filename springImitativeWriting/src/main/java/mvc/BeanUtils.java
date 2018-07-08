package mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 对java反射中操作的一些封装
 * @author liang
 *
 */
public class BeanUtils {

	/**
	 * ʹ使用class实例化
	 * @param clazz
	 * @return
	 */
	public static <T> T instanceClass(Class<T> clazz){
		if(!clazz.isInterface()) {
			try {
				return clazz.newInstance();// 使用newInstance时候，就必须保证：1、这个类已经加载；2、这个类已经连接了。而完成上面两个步骤的正是class的静态方法forName（）方法，这个静态方法调用了启动类加载器
			} catch (InstantiationException e) {//ͨ实例化异常，当试图通过newInstance()方法创建某个类的实例,而该类是一个抽象类或接口时,抛出该异常。，如果没有默认构造函数回抛异常
				// TODO: handle exception
			} catch (IllegalAccessException e) {// 安全权限异常,一般来说,是由于java在反射时调用了private方法所导致的
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 通过构造函数实例化
	 * @param ctor
	 * @param args
	 * @return
	 */
	public static <T> T instanceClass(Constructor<T> ctor, Object... args) 
			throws IllegalArgumentException, InstantiationException, 
					IllegalAccessException, InvocationTargetException{
		 makeAccessible(ctor);
		 return ctor.newInstance(args);//���ù��췽��ʵ����
	}
	
	private static void makeAccessible(Constructor<?> ctor) {
		// TODO Auto-generated method stub
		if((!Modifier.isPublic(ctor.getModifiers()) || //getModifiers获取字段的修饰符//判断是public//�����ж����η���public//getModifiers�����ɴ�Constructor�����ʾ�Ĺ��캯����Java�������η�����������ʽ���ء�
				!Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))//getDeclaringClass返回表示声明由此构造函数对象表示的构造函数的类的Class对象。
				&& !ctor.isAccessible()) {//isAccessible 返回反射对象的可访问标志的值
			ctor.setAccessible(true);//是private可用
		}
	}

	/**
	 * ����ĳ��class/���ӿڹ��з���
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @return
	 */
	public static  Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes){
		 try {
			return clazz.getMethod(methodName, paramTypes);//获得对象所声明的公开方法
		} catch (NoSuchMethodException e) {
			// TODO: handle exception
			return findDeclaredMethod(clazz, methodName, paramTypes);
		}
	}
	
	/**
	 * 返回Method
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @return
	 */
	public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes){
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);//此 Class 对象表示的类或接口声明的方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
		} catch (NoSuchMethodException e) {
			if(clazz.getSuperclass() != null) {//父类class
				return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    public static Method [] findDeclaredMethods(Class<?> clazz){
            return clazz.getDeclaredMethods();//获得某个类的所有声明的方法，即包括public、private和proteced，但是不包括父类的申明字段。
    }
    
    public static Field[] findDeclaredFields(Class<?> clazz){
        return clazz.getDeclaredFields();//获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
    }
}
