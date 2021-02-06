package com.itheima.service;

import com.aliyuncs.CommonResponse;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.util.Md5Utils;
import com.itheima.util.SmsUtils;

public interface UserService {
    //注册业务方法
    ResultInfo register(User user);

    //校验用户名是否存在
    ResultInfo checkUserName(String username) ;

    //目标发送短信业务方法
     ResultInfo sendSms(String telephone,String code);

    /**用户名与密码登录的业务方法
     * @param user 用户号数据包含账号与密码
     * @return ResultInfo 返回的数据封装对象
     */
    ResultInfo pwdLogin(User user);

    /**
     * 根据手机号查找用户对象
     * @param telephone
     * @return User
     */
    User findByTelephone(String telephone);

    /**
     * 更新用户数据，返回最新数据库用户信息
     * @param user   用户输入的数据
     * @return User 返回数据库用户
     */
    User update(User user);

}
