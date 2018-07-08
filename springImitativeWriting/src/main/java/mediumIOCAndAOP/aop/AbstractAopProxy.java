package mediumIOCAndAOP.aop;

/**
 * 抽象的aop代理，包含一个通知数据AdvisedSupport
 * @author liang
 *
 */
public abstract class AbstractAopProxy implements AopProxy{
	
	protected AdvisedSupport advisedSupport;

	public AbstractAopProxy(AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}
}
