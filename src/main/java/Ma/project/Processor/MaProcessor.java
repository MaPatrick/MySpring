package Ma.project.Processor;

import Ma.mySpring.IOC.MaAnnotation.Component;
import Ma.mySpring.IOC.PostProcessor.BeanPostProcessor;

/**
 * 使用后置处理器
 * 定义自己的后置处理器,可以批量的处理所有Bean的类型
 * author mrs
 * create 2023-02-24 23:19
 */
@Component
public class MaProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanId, Object bean) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanId, Object bean) {
        return bean;
    }
}
