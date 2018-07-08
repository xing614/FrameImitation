package mediumIOCAndAOP.aopConnectIocByAspectJ;

import org.aopalliance.aop.Advice;

import mediumIOCAndAOP.aop.Pointcut;
import mediumIOCAndAOP.aop.PointcutAdvisor;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    private Advice advice;//要用的过滤器
    
    public void setExpression(String expression) {
        pointcut.setExpression(expression);
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

}
