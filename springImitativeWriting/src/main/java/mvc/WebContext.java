package mvc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用来存储当前线程中的HttpServletRequest和HttpServletResponse
 * 当需要HttpServletRequest和HttpServletResponse时，可以通过requestHodler和responseHodler获取
 * @author liang
 *
 */
public class WebContext {

	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();
	
	public static HttpServletRequest getRequest() {
		return requestLocal.get();
	}
	
	public static HttpSession getSession() {
		return requestLocal.get().getSession();
	}
	
	public static ServletContext getServletContext() {
		return requestLocal.get().getSession().getServletContext();
	}
	
	public static HttpServletResponse getResponse() {
		return responseLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}
	
	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}	
	
}
