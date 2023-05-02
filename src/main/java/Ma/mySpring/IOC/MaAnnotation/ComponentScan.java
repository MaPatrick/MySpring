package Ma.mySpring.IOC.MaAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 源码
 * 实现Bean注入扫描注解
 * author mrs
 * create 2023-02-24 12:13
 */
//指定注解在可以标在哪里,扫描注解只能写在类上面
@Target(ElementType.TYPE)
//指定注解生效时间,在运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentScan {
    //value属性,扫描路径属性,空字符串作为默认值
    String value() default "";
}
