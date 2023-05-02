package Ma.mySpring.IOC.MaAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 源码
 * 实现单例或者多例的创建
 * author mrs
 * create 2023-02-24 16:10
 */
//指定注解在可以标在哪里,扫描注解只能写在类上面
@Target(ElementType.TYPE)
//指定注解生效时间,在运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    //定义单例还是多例,"singleton"单例,"prototype"多例
    String value() default "";
}
