package Ma.mySpring.IOC;

import Ma.mySpring.IOC.Aware.ApplicationContextAware;
import Ma.mySpring.IOC.Aware.BeanNameAware;
import Ma.mySpring.IOC.Definition.BeanDefinition;
import Ma.mySpring.IOC.Init.InitializingBean;
import Ma.mySpring.IOC.MaAnnotation.Scope;
import Ma.mySpring.IOC.MaAnnotation.Autowired;
import Ma.mySpring.IOC.MaAnnotation.Component;
import Ma.mySpring.IOC.MaAnnotation.ComponentScan;
import Ma.mySpring.IOC.PostProcessor.BeanPostProcessor;
import Ma.mySpring.IOC.PostProcessor.InstantiationAwareBeanPostProcessor;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 源码
 * 实现Spring容器
 * author mrs
 * create 2023-02-24 11:46
 */
public class MaApplicationContext {
    //传入的配置文件属性
    private Class configClass;

    //存放容器中的Bean的定义类,String是Bean的id
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    //存放容器中Bean的单例对象
    private ConcurrentHashMap<String,Object> singletonMap = new ConcurrentHashMap<>();
    //存储后置处理器的池子
    List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    //在创建Spring容器时传入配置文件扫描
    public MaApplicationContext(Class configClass){
        this.configClass = configClass;
        //对配置文件的Bean注入扫描注解进行业务实现
        //判断有没有Bean注入扫描注解,有就获取注解信息
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            //获取Bean注入扫描注解
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            //拿到Bean注入扫描注解的扫描路径,并变成目录格式
            String path = componentScanAnnotation.value();

            path = path.replace(".","/");
            //注意: 扫描的是target包下的代码即编译运行时的代码
            //拿到当前类的加载器,利用类加载器将上面的扫描路径变成绝对路径并获取对于的资源
            ClassLoader classLoader = MaApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);

            //AOP下的proxy包默认要扫描
            URL resource1 = classLoader.getResource("Ma/mySpring/AOP/proxy");
            File dir1 = new File((resource1.getFile()));
            List<File> aopFlie = new ArrayList<>();
            getAllFile(dir1,aopFlie);
            //先将这些文件放入要遍历的list集合中
            List<File> files = new ArrayList<>();
            for(File file : aopFlie){
                files.add(file);
            }

            File dir = new File(resource.getFile());
            if(dir.isDirectory()){
                //找到目录中的所有文件
                getAllFile(dir,files);
                for(File f : files){
                    String fileName = f.getAbsolutePath();
                    //筛选出class结尾的
                    if(fileName.endsWith(".class")){
                        //将这些文件截取成相对路径
                        String className = fileName.substring(fileName.indexOf("classes"), fileName.indexOf(".class"))
                                .substring(8).replace("\\",".");
                        //使用反射获取这些类中是否有实现Bean注入注解的注解
                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            if(clazz.isAnnotationPresent(Component.class)){
                                //实现后置处理器,先判断是否实现了BeanPostProcessor,申请一个对象放入后置处理器池中
                                if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                                    BeanPostProcessor instance = (BeanPostProcessor)clazz.newInstance();
                                    beanPostProcessorList.add(instance);
                                    continue;
                                }
                                //到这里就确定注入的Bean了
                                //这里是先生成一个Bean的定义类,因为生成Bean还有其他属性如单例还是多例
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(clazz);
                                //判断单例还是多例并对Bean定义类进行赋值
                                if(clazz.isAnnotationPresent(Scope.class)){
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                }else {
                                    beanDefinition.setScope("singleton");
                                }
                                //拿到Bean的id
                                Component component = clazz.getAnnotation(Component.class);
                                String BeanId = component.value();
                                //如果Bean的id是默认的"",Bean的id就默认是第一个字母是小写的类型名
                                if(BeanId.equals("")){
                                    BeanId = Introspector.decapitalize(clazz.getSimpleName());//bean名字的生成器
                                }
                                //存储到Bean定义类的容器中去
                                beanDefinitionMap.put(BeanId,beanDefinition);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        //扫描完毕,最后生成单例Bean的对象,放入单例池中
        for(Map.Entry<String,BeanDefinition> beanEntry : beanDefinitionMap.entrySet()){
            BeanDefinition beanDefinition = beanEntry.getValue();
            String beanId = beanEntry.getKey();
            if(beanDefinition.getScope().equals("singleton")){
                //创建单例对象并放入单例池中
                Object bean = createBean(beanId,beanDefinition);
                singletonMap.put(beanId,bean);
            }
        }
    }

    //递归的将目录中的所有的类(包括子文件全部查询出来)
    public static void getAllFile(File fileInput, List<File> allFileList) {
        // 获取文件列表
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                // 递归处理文件夹
                // 如果不想统计子文件夹则可以将下一行注释掉
                getAllFile(file, allFileList);
            } else {
                // 如果是文件则将其加入到文件数组中
                allFileList.add(file);
            }
        }
    }

    //使用Bean的id来获取Bean对象
    public Object getBean(String beanId) {
        //先根据Bean的id在生成对应的Bean定义类(Bean定义类在上面的定义类Map中获取)
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanId);
        if(beanDefinition==null){//如果没有这个Bean的id抛异常
            throw new NullPointerException();
        }
        String scope = beanDefinition.getScope();
        //然后分单例和多例来生成Bean
        if(scope.equals("singleton")){
            //单例,在单例池中获取并返回
            Object bean = singletonMap.get(beanId);
            if(bean==null){
                Object o = createBean(beanId, beanDefinition);
                singletonMap.put(beanId,o);
            }
            return bean;
        }else {
            //多例每次都创建
            return createBean(beanId,beanDefinition);
        }
    }

    //对象初始化方法
    private Object createBean(String beanId,BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getType();
        //利用反射调用午参的构造方法来创建对象
        try {
            //AOP
            for (BeanPostProcessor processor : beanPostProcessorList) {
                if(processor instanceof InstantiationAwareBeanPostProcessor){
                    ((InstantiationAwareBeanPostProcessor)(processor)).postProcessBeforeInstantiation(clazz,beanId);
                }
            }
            //Bean生命周期
            //1、Bean实例化
            Object instance = clazz.getConstructor().newInstance();
            //AOP
            for (BeanPostProcessor processor : beanPostProcessorList) {
                if(processor instanceof InstantiationAwareBeanPostProcessor){
                    ((InstantiationAwareBeanPostProcessor)(processor)).postProcessAfterInstantiation(instance,beanId);
                }
            }
            //2、看是否有依赖注入,要是有就注入依赖,这里实现@AutoWired注解功能
            //利用反射遍历字段,如果属性有@AutoWired注解,就给这个属性赋值
            for (Field f:clazz.getDeclaredFields()) {
                if(f.isAnnotationPresent(Autowired.class)){
                    f.setAccessible(true);
                    //通过属性名当Bean的id来获取Bean,就是简单的依赖注入
                    f.set(instance,getBean(f.getName()));
                }
            }
            //3、检查Ware相关接口用来信息回调
            //实现Spring中的BeanNameAware接口的回调
            if(instance instanceof BeanNameAware){
                ((BeanNameAware)instance).setBeanId(beanId);
            }
            //实现Spring中的ApplicationContextAware接口的回调
            if(instance instanceof ApplicationContextAware){
                ((ApplicationContextAware)instance).setApplicationContext(this);
            }
            //4、后置处理器的Before方法
            for (BeanPostProcessor processor : beanPostProcessorList) {
                processor.postProcessBeforeInitialization(beanId,instance);
            }
            //5、实现初始化
            if(instance instanceof InitializingBean){
                //执行接口中的方法,是项目中写的逻辑在创建Bean中执行就是当前Bean初始化
                ((InitializingBean)instance).afterPropertiesSet();
            }
            //6、后置处理器的After方法
            for (BeanPostProcessor processor : beanPostProcessorList) {
                instance = processor.postProcessAfterInitialization(beanId,instance);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
