package Ma.mySpring.AOP.Aspect;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * 实现AOP的切面类
 * 这里也是代理工厂
 * 就是在最后重新intercept方法选择性的放入增强方法的通知
 * author mrs
 * create 2023-02-25 15:43
 */
public class userAspect implements MethodInterceptor {
    //维护一个被代理对象
    private Object target;
    //构造器,聚合被代理类对象
    public userAspect(Object target) {
        this.target = target;
    }
    //返回一个代理对象,是target对象的代理对象
    public Object getProxyInstance(){
        //1、创建工具类
        Enhancer enhancer = new Enhancer();
        //2、设置父类
        enhancer.setSuperclass(target.getClass());
        //3、设回调函数
        enhancer.setCallback(this);
        //4、创建子类对象,即代理对象
        return enhancer.create();
    }
    //重写intercept
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("目标方法前的通知方法");
        Object invoke = method.invoke(target, objects);//传入的是被代理类对象和参数
        System.out.println("目标方法后的通知方法");
        return invoke;//这个返回是因为有的被代理方法有返回值,此时我们也要把它的返回值也给返回！
    }
}

