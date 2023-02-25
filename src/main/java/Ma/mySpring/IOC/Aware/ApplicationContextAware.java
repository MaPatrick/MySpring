package Ma.mySpring.IOC.Aware;

import Ma.mySpring.IOC.MaApplicationContext;

/**
 * 源码
 * 实现Aware相关接口的回调机制
 * 调出Spring容器
 * author mrs
 * create 2023-02-24 21:17
 */
public interface ApplicationContextAware {
    public void setApplicationContext(MaApplicationContext applicationContext);
}
