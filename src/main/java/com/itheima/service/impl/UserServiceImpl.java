package com.itheima.service.impl;

import com.aliyuncs.CommonResponse;
import com.itheima.dao.UserDao;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.util.DaoFactory;
import com.itheima.util.Md5Utils;
import com.itheima.util.SmsUtils;

public class UserServiceImpl implements UserService {

    //实例dao类
    private UserDao userDao = DaoFactory.getBean(UserDao.class);

    public ResultInfo register(User user) {
        //1.校验用户名是否已被注册
        //调用dao根据用户名查找用户
        User dbUser = userDao.findByUserName(user.getUsername());
        //校验
        if (dbUser != null) {
            return new ResultInfo(false, "用户名已存在");
        }
        //2.校验手机号是否被注册
        //调用dao根据手机号查找用户
        dbUser = userDao.findByTelephone(user.getTelephone());
        if (dbUser != null) {
            return new ResultInfo(false, "手机号已注册");
        }
        //3.对密码数据加密（MD5）得到密文
        /*
         * 为什么对密码加密？
         *   答：防止数据库管理员泄密数据。
         * 通用加密技术：MD5
         * 介绍：消息摘要第5版加密算法
         * 特点：不可逆的加密算法，不能解密
         * 密码验证原理： 加用户输入的密码加密后与对应数据库的密文对比
         * 使用步骤：使用MD5工具类对字符串生成密文
         * */
        String md5 = Md5Utils.encodeByMd5(user.getPassword());
        //将密文代替原始密码
        user.setPassword(md5);

        //4.进行注册操作
        //调用dao插入用户数据
        Integer count = userDao.insert(user);

        //5.返回结果
        if (count > 0) {
            return new ResultInfo(true, "注册成功");
        } else {
            return new ResultInfo(false, "注册失败");
        }
    }

    //校验用户名是否存在
    public ResultInfo checkUserName(String username) {
        //1.调用dao根据用户名查找用户
        User dbUser = userDao.findByUserName(username);

        if (dbUser == null) {
            //2.用户名对象为null，说明没有被注册
            return new ResultInfo(true, "用户名可用");
        } else {
            //3.用户对象不为null，说明已被注册
            //返回false:代表用户名已被占用
            return new ResultInfo(false, "用户名已被占用");
        }
    }

    //目标发送短信业务方法
    public ResultInfo sendSms(String telephone,String code){
        //1.调用smsUtils工具类发送短信

//        return new ResultInfo(true,"短信发送成功");
        CommonResponse response = SmsUtils.send(telephone, "黑暗旅游网", "SMS_205136431", code);
        System.out.println("response = " + response);

        //2.判断发送短信状态
        if (response.getHttpStatus()==200){
        //2.1 为200,返回短信发送成功
            return new ResultInfo(true,"短信发送成功");
        }else {
        //2.2 否则，返回短信发送失败
            return new ResultInfo(false,"短信发送失败");
        }
    }

    @Override
    public ResultInfo pwdLogin(User user) {
        /**
         * 用户名与密码登录的业务方法
         *
         * @param user 用户数据包含账号与密码
         * @return ResultInfo 返回的数据封装对象
         */
        //1.校验用户名
        //根据用户名调用dao查询用户对象
        User loginUser = userDao.findByUserName(user.getUsername());

        //用户对象为null，代表用户名不存在，返回登录失败
        if (loginUser==null){
            return  new ResultInfo(false,"用户名错误");
        }

        //2.校验密码
        //分析：
        // 数据库中的密码是密文(loginUser.getPassword())
        // 用户输入的密码是明文(user.getPassword())
        // 先将明文密码加密
        String md5 = Md5Utils.encodeByMd5(user.getPassword());

        //// 对比2个密文,不相等说明密文错误
        if (!md5.equals(loginUser.getPassword())){
            return  new ResultInfo(false,"用户名或密码登录错误");
        }

        //3.返回结果对象（返回数据库用户对象）
        return new ResultInfo(true,"登录成功",loginUser);
    }

    /**
     * 根据手机号查找用户对象
     *
     * @param telephone
     * @return User
     * 快捷键： Ctrl+i , 快速实现没有实现的方法
     */
    @Override
    public User findByTelephone(String telephone) {
        return userDao.findByUserName(telephone);
    }

    /**
     * 更新用户数据，返回最新数据库用户信息
     *
     * @param user 需要修改数据的用户对象
     * @return User 返回修改后的用户数据
     */
    @Override
    public User update(User user) {
        userDao.update(user);
        User dbUser=userDao.findByUid(user.getUid());
        return dbUser;
    }
}
















