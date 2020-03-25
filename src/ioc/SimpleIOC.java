package ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yixi Wang
 *         Created by sinosoft on 2020/3/25.
 */
public class SimpleIOC {
    //存放所有bean的Map
    private Map<String, Object> beanMap = new HashMap<>();

    public SimpleIOC() {
    }

    /**
     * 从beanMap中获取bean
     */
    public Object getBean(String name) {
        Object bean = beanMap.get(name);
        if (bean==null) {
            throw new IllegalArgumentException("there is no bean with name " + name);
        }
        return bean;
    }

    private void loadBeans(String location) throws Exception {
        //加载xml文件
        InputStream inputStream = new FileInputStream(location);
        //文件工厂类，用于加载构建xml文档
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //工厂生产创建者
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        //xml转为Document对象
        Document doc = docBuilder.parse(inputStream);
        //根节点对象<beans>
        Element root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        //遍历节点<bean>
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                //获取<bean>节点属性值
                Element els = (Element) node;
                String id = els.getAttribute("id");
                String className = els.getAttribute("class");

                //类加载
                Class beanClass = null;
                try {
                    beanClass = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                //创建类实例
                Object bean = beanClass.newInstance();
                //获取<property>节点属性值
                NodeList propertyNodes = els.getElementsByTagName("property");
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node property = propertyNodes.item(j);
                    if (property instanceof Element) {
                        Element propertyElement = (Element) property;
                        String name = propertyElement.getAttribute("name");
                        String value = propertyElement.getAttribute("value");
                        Field declareFile = beanClass.getDeclaredField(name);
                        //设置 类私有属性 可访问
                        declareFile.setAccessible(true);

                        if (value != null && value.length() > 0) {
                            declareFile.set(bean, value);
                        } else {
                            //是否该属性有通过ref引用的值
                            String ref = propertyElement.getAttribute("ref");
                            if (ref==null && ref.length()==0) {
                                throw new IllegalArgumentException("\"ref config error\"");
                            }
                            // 将引用填充到相关字段中
                            declareFile.set(bean,getBean(ref));
                        }
                        //注册bean到beanMap
                        beanMap.put(id, bean);
                    }
                }
            }
        }
    }
}
