package mvc;
/**
 * 视图类
 * 包含跳转的路径、展示到页面的数据、跳转方式
 * @author liang
 *
 */
public class View {

	private String url;//跳转路径
	
	private String dispathAction = DispatchActionConstant.FORWARD;
	
	public View(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDispathAction() {
		return dispathAction;
	}

	public void setDispathAction(String dispathAction) {
		this.dispathAction = dispathAction;
	}
	
	
	
}
