package mvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * 注解处理器 类似DispatcherServlet
 * @author liang
 *
 */
public class AnnotationHandlerServlet extends HttpServlet{

	/**
	 * 解析url
	 * @param request
	 * @return
	 */
	private String pareRequestURI(HttpServletRequest request){
		String path = request.getContextPath()+"/";//����·�� /test/ 
		String requestUri = request.getRequestURI();//���������ַ����Ŀͻ��˵�ַ/test/test.do
		String midUrl = requestUri.replaceFirst(path, "");//������ȥ�� test.do
		String lastUrl = midUrl.substring(0, midUrl.lastIndexOf("."));//test
		return lastUrl;
	}
	
	/**
	 * 初始化，解析web.xml指示的要扫描的包
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//获取web.xml中配置的要扫描的包
		String basePackage = config.getInitParameter("basePackage");
		System.out.println("basePackage=="+basePackage);
		//如果配置了多个包，例如：<param-value>mvc.controller,mm.controller</param-value>
		if(basePackage.indexOf(",")>0) {
			String[] packageNameArr = basePackage.split(",");
			for(String packageName:packageNameArr) {
				initRequestMapingMap(packageName);
			}
		}else {
			initRequestMapingMap(basePackage);
		}
		
		System.out.println(RequestMapingMap.getRequestMap().toString());
		System.out.println("初始化结束");
	}
	/**
	 * 获取被ImController解析的类，将被ImRequestMapping注解的方法和Path添加到RequestMapingMap(key,value)
	 * @param packageName
	 */
	private void initRequestMapingMap(String packageName){
		Set<Class<?>> setClasses = ScanClassUtil.getClasses(packageName);
		System.out.println(packageName);
		for(Class classes:setClasses) {
			if(classes.isAnnotationPresent(ImController.class)) {//被ImController注解的类
				Method[] methods = BeanUtils.findDeclaredMethods(classes);
				for(Method method:methods) {
					if(method.isAnnotationPresent(ImRequestMapping.class)) {//被ImRequestMapping注解的方法
						String annoPath = method.getAnnotation(ImRequestMapping.class).value();
						if(annoPath!=null && !"".equals(annoPath)) {
							if(RequestMapingMap.getRequestMap().containsKey(annoPath)) {
								throw new RuntimeException("RequestMapping映射的地址不允许重复！");
							}
							RequestMapingMap.put(annoPath, classes);
						}
					}
				}
			}
		}
		
		
		
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		this.excute(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.excute(request, response);
	}
	
	/**
	 * 当用户请求时(无论是get还是post请求)，会调用封装好的execute方法 ，
	 * execute会先获取请求的url，然后解析该URL，
	 * 根据解析好的URL从Map集合中取出要调用的目标类 ，再遍历目标类中定义的所有方法，
	 * 找到类中使用了RequestMapping注解的那些方法，判断方法上面的RequestMapping注解的value属性值是否和解析出来的URL路径一致,
	 * 如果一致，说明了这个就是要调用的目标方法，此时就可以利用java反射机制先实例化目标类对象，然后再通过实例化对象调用要执行的方法处理用户请求。
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
    private void excute(HttpServletRequest request, HttpServletResponse response) 
   		 throws ServletException, IOException{
    	WebContext.setRequest(request);
    	WebContext.setResponse(response);
    	//����url
    	String lastUrl = pareRequestURI(request);
    	System.out.println("lastUrl=="+lastUrl);
    	//����lastUrl��ȡ��Ӧ��
    	Class<?> className = RequestMapingMap.getClassName(lastUrl);
    	Object classInstance = BeanUtils.instanceClass(className);//����classʵ��
    	Method[] methods = BeanUtils.findDeclaredMethods(className);
    	Method method = null;
    	for(Method me:methods) {
    		if(me.isAnnotationPresent(ImRequestMapping.class)) {
    			String annoPath = me.getAnnotation(ImRequestMapping.class).value();
    			if(annoPath!=null && !"".equals(annoPath) && lastUrl.equals(annoPath.trim())) {
    				method = me;
    				break;
    			}
    		}
    	}
    	if(method!=null) {
    		try {
    			//反射执行方法
				Object retObj = method.invoke(classInstance);
				if(retObj!=null) {
					View view = (View)retObj;
					//判断要使用的跳转方式
					if(view.getDispathAction().equals(DispatchActionConstant.FORWARD)) {
						//服务端进入下一个方法
						request.getRequestDispatcher(view.getUrl()).forward(request, response);
					}else if(view.getDispathAction().equals(DispatchActionConstant.REDIRECT)) {
						response.sendRedirect(request.getContextPath()+view.getUrl());//客户端重定向
					}else{
						request.getRequestDispatcher(view.getUrl()).forward(request, response);
					}
				}
			} catch (IllegalAccessException e) {//安全权限异常,一般来说,是由于java在反射时调用了private方法所导致的
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {//非法参数异常
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {//invoke反射，当被调用的方法的内部抛出了异常而没有被捕获时，将由此异常接收
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
	 
	
}
