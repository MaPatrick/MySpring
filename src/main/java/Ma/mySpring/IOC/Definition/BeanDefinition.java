package Ma.mySpring.IOC.Definition;

/**
 * 源码
 * Bean的定义类
 * author mrs
 * create 2023-02-24 16:27
 */
public class BeanDefinition {
    //Bean的类型
    private Class type;
    //单例的还是多例
    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
