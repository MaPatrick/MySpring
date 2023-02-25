package Ma.project.Processor;

import Ma.mySpring.IOC.MaAnnotation.Component;
import Ma.mySpring.IOC.PostProcessor.InstantiationAwareBeanPostProcessor;

/**
 * 使用AOP的那个后置处理器
 * author mrs
 * create 2023-02-25 10:30
 */
@Component
public class AOPMaProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanId, Object bean) {
        System.out.println("执行BeforeInitialization后置处理器方法");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanId, Object bean) {
        System.out.println("执行AfterInitialization后置处理器方法");
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> var1, String var2) {
        System.out.println("执行BeforeInstantiation后置处理器方法");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object var1, String var2) {
        System.out.println("执行AfterInstantiation后置处理器方法");
        return false;
    }
}
