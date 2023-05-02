package Ma.mySpring.IOC.Init;

/**
 * 源码
 * 实现Bean的初始化方法
 * author mrs
 * create 2023-02-24 22:51
 */
public interface InitializingBean {
    //初始化方法
    public void afterPropertiesSet();
}
