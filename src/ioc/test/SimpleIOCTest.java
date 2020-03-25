package ioc.test;

import ioc.SimpleIOC;

/**
 * @author Yixi Wang
 *         Created by sinosoft on 2020/3/25.
 */
public class SimpleIOCTest {
    /**
     * 加载xml
     */
    String location = SimpleIOC.class.getClassLoader().getResource("ioc.xml").getFile();
    //创建IOC容器
    //获取容器中的bean

}
