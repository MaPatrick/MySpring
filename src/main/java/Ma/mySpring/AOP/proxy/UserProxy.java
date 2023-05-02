package Ma.mySpring.AOP.proxy;

import Ma.mySpring.AOP.Aspect.userAspect;
import Ma.mySpring.IOC.MaAnnotation.Component;
import Ma.mySpring.IOC.PostProcessor.BeanPostProcessor;

/**
 * 这个包是要扫描的,我已经在容器中进行写死这个包路径进行默认扫描了,所有要添加@Component进行注入Bean
 * 简单模拟AOP的核心原理,动态代理
 * 按照下面的postProcessAfterInitialization方法的格式
 * 先判断Bean的id来看是否是我要增强的类,然后使用刚才的代理工厂对象(切面类)生成代理对象,完成增强功能
 * author mrs
 * create 2023-02-25 15:35
 */
@Component
public class UserProxy implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(String beanId, Object bean) {
        return null;
    }
    //后置处理器的after方法就是动态代理代码所在的方法,这里就模拟动态代理下面项目中的user类
    //框架中还要判断切入点、增强类,加载通知方法,然后进行动态代理(过于复杂,不进行详细模拟)
    @Override
    public Object postProcessAfterInitialization(String beanId, Object bean) {
        if(beanId.equals("user")) {//比对Bean的id名
            //使用被代理对象获取代理工厂对象
            userAspect factory = new userAspect(bean);
            //代理工厂对象调用getProxyInstance方法生成代理对象，直接使用被代理类接收(没有通用接口就直接用被代理类了)
            Object instance = factory.getProxyInstance();
            return instance;
        }
        return bean;
    }
}
