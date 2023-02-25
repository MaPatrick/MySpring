package Ma.project.proxyBean;

import Ma.mySpring.IOC.MaAnnotation.Component;

/**
 * 使用AOP的被代理类
 * author mrs
 * create 2023-02-24 13:35
 */
@Component
public class User {
    public void  teach(){
        System.out.println("老师授课中.我是Cglib被代理方法,不需要实现接口");
    }

}
