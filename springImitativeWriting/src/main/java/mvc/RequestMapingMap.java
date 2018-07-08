package mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于存储方法的访问路径
 * AnnotationHandleServlet初始化时会将类(使用Controller注解标注的那些类)中使用了RequestMapping注解标注的那些方法的访问路径存储到RequestMapingMap中。
 * @author liang
 *
 */
public class RequestMapingMap {
	//用来保存path和类对应关系
	private static Map<String,Class<?>> requestMap = new HashMap<String,Class<?>>();
	
	public static Class<?> getClassName(String path){
		
		return requestMap.get(path);
	}
	
	public static void put(String path,Class<?> className) {
		requestMap.put(path, className);
	}
	
	public static Map<String,Class<?>> getRequestMap(){
		return requestMap;
	}
}
