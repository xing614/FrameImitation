package mediumIOCAndAOP.aopConnectIocByAspectJ;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

import mediumIOCAndAOP.aop.TargetSource;
import mediumIOCAndAOP.ioc.BeanFactory;
import mediumIOCAndAOP.ioc.BeanFactoryAware;
import mediumIOCAndAOP.ioc.BeanPostProcessor;
import mediumIOCAndAOP.ioc.XmlBeanFactory;

/**
 * AOP与IOC产生联系的具体实现类
 * 实现了 BeanPostProcessor 和 BeanFactoryAware 接口。
 * BeanFactory 在注册 BeanPostProcessor 接口相关实现类的阶段，会将其本身注入到 AutoProxyCreator 中，
 * 为后面 AOP 给 bean 生成代理对象做准备。
 * BeanFactory 初始化结束后，AOP 与 IOC 桥梁类 AutoProxyCreator 也完成了实例化，
 * 并被缓存在 BeanFactory 中，静待 BeanFactory 实例化 bean。
 * 当外部产生调用，BeanFactory 开始实例化 bean 时，AutoProxyCreator工作细节：
 * 
 * 从 BeanFactory 查找实现了 PointcutAdvisor 接口的切面对象，切面对象中包含了实现 Pointcut 和 Advice 接口的对象。
 * 使用 Pointcut 中的表达式对象匹配当前 bean 对象。如果匹配成功，进行下一步。否则终止逻辑，返回 bean。
 * JdkDynamicAopProxy 对象为匹配到的 bean 生成代理对象，并将代理对象返回给 BeanFactory。
 * @author liang
 *
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware{

	private XmlBeanFactory xmlBeanFactory;
	
	@Override
	public void setBeanFactory(BeanFactory bf) throws Exception {
		// TODO Auto-generated method stub
		xmlBeanFactory = (XmlBeanFactory) bf;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
		// TODO Auto-generated method stub
        /* 这里两个 if 判断很有必要，如果删除将会使程序进入死循环状态，
         * 最终导致 StackOverflowError 错误发生
         */
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return bean;
        }
        if (bean instanceof MethodInterceptor) {
            return bean;
        }

        // 1.  从 BeanFactory 查找 AspectJExpressionPointcutAdvisor 类型的对象
        List<AspectJExpressionPointcutAdvisor> advisors =
                xmlBeanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {

            // 2. 使用 Pointcut 对象匹配当前 bean 对象
            if (advisor.getPointcut().getClassFilter().matchers(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
                advisedSupport.setTargetSource(targetSource);

                // 3. 生成代理对象，并返回
                return advisedSupport.getProxy();
            }
        }

        // 2. 匹配失败，返回 bean
        return bean;
	}

}
