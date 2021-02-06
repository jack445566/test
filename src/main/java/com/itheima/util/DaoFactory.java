package com.itheima.util;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Proxy;

public class DaoFactory {


    public static <T> T getBean(Class<T> daoInterface){
        return (T)Proxy.newProxyInstance(
                daoInterface.getClassLoader(),
                new Class[]{daoInterface},
                (proxy,method,args)->{

                    //得到会话
                    SqlSession sqlSession = MyBatisUtils.openSession();

                    //创建 DAO 代理类
                    T mapper = sqlSession.getMapper(daoInterface);

                    //调用 DAO 接口中的方法
                    Object obj = method.invoke(mapper, args);

                    //释放资源
                    MyBatisUtils.close(sqlSession);

                    //返回值
                    return obj;
                }
        );
    }
}
