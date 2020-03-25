### 实现简单的IOC容器
1. 加载 xml 配置文件，遍历其中的标签
2. 获取标签中的 id 和 class 属性，加载 class 属性对应的类，并创建 bean
3. 遍历标签中的标签，获取属性值，并将属性值填充到 bean 中
4. 将 bean 注册到 bean 容器中

- SimpleIOC     // IOC 的实现类，实现了上面所说的4个步骤
- SimpleIOCTest    // IOC 的测试类
- Car           // IOC 测试使用的 bean
- Wheel         // 同上 
- ioc.xml       // bean 配置文件