package Ma.mySpring.IOC.PostProcessor;

/**
 * 源码
 * 实现用于AOP的后置处理器
 * author mrs
 * create 2023-02-25 10:02
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    /**
     * 作用(很重要的方法,这里的判断就不实现了):
     * 看是否创建过Bean,创建过就返回、加载切面类、判断增强类、看是否创建过代理对象,创建过就返回
     */

    Object postProcessBeforeInstantiation(Class<?> var1, String var2);

    boolean postProcessAfterInstantiation(Object var1, String var2);

}
