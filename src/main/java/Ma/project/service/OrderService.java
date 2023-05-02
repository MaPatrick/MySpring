package Ma.project.service;

import Ma.mySpring.IOC.MaAnnotation.Component;
import Ma.mySpring.IOC.MaAnnotation.Scope;

/**
 * 依赖注入使用的类
 * author mrs
 * create 2023-02-24 19:28
 */
@Component
public class OrderService {
    public OrderService() {
        System.out.println("orderService实例化");
    }
}
