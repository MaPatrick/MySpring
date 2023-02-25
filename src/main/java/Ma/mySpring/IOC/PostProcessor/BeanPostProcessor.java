package Ma.mySpring.IOC.PostProcessor;

/**
 * 源码
 * 实现Spring中的后置处理器
 * author mrs
 * create 2023-02-24 23:06
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(String beanId,Object bean);
    //创建代理对象的方法,很重要!!
    Object postProcessAfterInitialization(String beanId,Object bean);
}
