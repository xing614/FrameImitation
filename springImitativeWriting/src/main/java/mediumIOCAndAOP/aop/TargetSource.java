package mediumIOCAndAOP.aop;
/**
 * 目标源，用来保存一个类的class,接口,信息
 * @author liang
 *
 */
public class TargetSource {

	private Class<?> targetClass;
	private Class<?>[] interfaces;
	private Object target;
	public TargetSource(Object target, Class<?> targetClass, Class<?>[] interfaces) {
		super();
		this.targetClass = targetClass;
		this.interfaces = interfaces;
		this.target = target;
	}
	public Class<?> getTargetClass() {
		return targetClass;
	}
	public Class<?>[] getInterfaces() {
		return interfaces;
	}
	public Object getTarget() {
		return target;
	}
	
	
}
