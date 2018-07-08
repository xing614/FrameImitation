package mvc;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据存储类，当需要发送数据到客户端显示时，就可以将要显示的数据存储到ViewData类中
 * @author liang
 *
 */
public class ViewData {

	private HttpServletRequest request;
	
	public ViewData() {
		initRequest();
	}
	
	private void initRequest() {
		this.request = WebContext.getRequest();
	}
	
	public void put(String name,Object value) {
		this.request.setAttribute(name, value);
	}
}
