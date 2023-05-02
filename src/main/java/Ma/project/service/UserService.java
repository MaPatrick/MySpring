package Ma.project.service;

import Ma.mySpring.IOC.Aware.ApplicationContextAware;
import Ma.mySpring.IOC.Aware.BeanNameAware;
import Ma.mySpring.IOC.Init.InitializingBean;
import Ma.mySpring.IOC.MaApplicationContext;
import Ma.mySpring.IOC.MaAnnotation.Autowired;
import Ma.mySpring.IOC.MaAnnotation.Component;
import Ma.mySpring.IOC.MaAnnotation.Scope;

/**
 * 使用Spring框架项目的业务层
 * author mrs
 * create 2023-02-24 11:43
 */
@Scope("prototype")//多例
@Component("userService")
public class UserService implements BeanNameAware, ApplicationContextAware, InitializingBean {
    @Autowired
    private OrderService orderService;
    public MaApplicationContext applicationContext;

    public UserService() {
        System.out.println("userService实例化");
    }

    //Bean的id回调
    @Override
    public void setBeanId(String name) {
        System.out.println("userService的id回调: "+name);
    }
    //Spring容器的回调
    @Override
    public void setApplicationContext(MaApplicationContext applicationContext) {
        System.out.println("Spring容器回调: "+applicationContext);
    }
    //初始化方法
    @Override
    public void afterPropertiesSet() {
        //这里面做想做的事情
        System.out.println("userService的初始化");
    }
}