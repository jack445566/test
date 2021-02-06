package com.itheima.util;

import com.itheima.service.UserService;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class BeanFactory {


    public static Object getBean(String interfaceName){

        try {
      //1.实现解析dom4j解析xml文件的核心对象SAXReader
        SAXReader saxReader=new SAXReader();

        //2.获取配置文件beans.xml的输入流
        InputStream inputStream = BeanFactory.class.getResourceAsStream("/beans.xml");

        //3.加载配置文件输入流获取到xml的文档对象Document
            Document document = saxReader.read(inputStream);

            // 4.解析数据得到实现类全名
                //表达式含义介绍：
                //  "//" 全局搜索
                //  "@class" 查找标签里面的属性名class
                //  "[@id='userService']" 查找筛选属性名id的值为userService
            String xpath="//bean[@id='"+interfaceName+"']/@class";

            //xml中每个标签，属性，文本都是一个节点对象Node
            //标签类型：Element
            //属性类型：Attribute
            //文本类型：Text
            //他们的父类是Node
            //执行xpath表达式，获取class属性对象
            Attribute attr = (Attribute) document.selectSingleNode(xpath);

            //获取class属性值
            String implClassFullName = attr.getValue();

            //5.使用反射传输实现类全名对应的对象返回
            return Class.forName(implClassFullName).getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        throw  new RuntimeException(e);
        }

    }

    public static void main(String[] args){
        UserService userService= (UserService) BeanFactory.getBean("userService");
        System.out.println(userService);
    }
}
