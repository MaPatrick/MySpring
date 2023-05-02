package Ma.Test;

import Ma.mySpring.IOC.MaApplicationContext;
import Ma.project.config.MainConfig;
import Ma.project.proxyBean.User;

/**
 * 项目中创建Spring容器
 * author mrs
 * create 2023-02-24 12:04
 */
public class MainTest {
    public static void main(String[] args) {
        MaApplicationContext context = new MaApplicationContext(MainConfig.class);
        User user = (User)context.getBean("user");
        user.teach();

    }
}
