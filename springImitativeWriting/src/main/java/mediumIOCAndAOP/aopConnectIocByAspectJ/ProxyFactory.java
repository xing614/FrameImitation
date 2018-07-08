package mediumIOCAndAOP.aopConnectIocByAspectJ;

import mediumIOCAndAOP.aop.AdvisedSupport;
import mediumIOCAndAOP.aop.AopProxy;
import mediumIOCAndAOP.aop.baseOnJDK.JdkDynamicAopProxy;
/**
 * AopProxy 实现类的工厂类
 * @author liang
 *
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy {

	@Override
	public Object getProxy() {
		// TODO Auto-generated method stub
		return createAopProxy().getProxy();
	}
	
	private AopProxy createAopProxy() {
        return new JdkDynamicAopProxy(this);
    }

}
