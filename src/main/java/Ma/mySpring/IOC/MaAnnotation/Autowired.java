package Ma.mySpring.IOC.MaAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 源码
 * 实现简单的自动装配的注解
 * author mrs
 * create 2023-02-24 19:44
 */
//指定注解在可以标在哪里,扫描注解只能写在字段上面
@Target(ElementType.FIELD)
//指定注解生效时间,在运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

}
