package Ma.mySpring.IOC.Aware;

/**
 * 源码
 * 实现Aware相关接口的回调机制
 * 调出Bean的id
 * author mrs
 * create 2023-02-24 20:50
 */
public interface BeanNameAware {
    public void setBeanId(String name);
}
