package com.itheima.web.servlet;

import cn.hutool.core.util.IdUtil;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.util.BeanFactory;
import com.itheima.util.JedisUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

/**
 * @author 黑马程序员
 * 快捷键：Ctrl+Alt+O  去掉当前类中所有无效的依赖引用的类
 */
@MultipartConfig /*注意：只有加这个注解才可以获取客户端传递过来的文件流对象*/
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

    //目标：处理修改用户中心数据请求
    private void updateInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1.登录校验
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if(loginUser==null) {

            //没有登录，跳转到首页
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }


        //2.获取表单数据封装到User对象中
        User user = new User();
        Map<String, String[]> map = request.getParameterMap();
        BeanUtils.populate(user,map);


        //2.1 实现文件上传（用户头像）
        //a. 获取上传的文件对象（注意，当前类必须加入@MultipartConfig）
        Part part = request.getPart("pic");//根据表单文件域的name获取

        //b. 判断上传文件大小，大于0才进行上传操作
        if(part.getSize()>0) {

            //c. 定义一个全局唯一的文件名，使用这个文件名写入磁盘，目的是防止重名覆盖
            //例如：文件名“b17f24ff026d40949c85a24f4f375d42.jpg”
            //获取唯一值（UUID，通用唯一码）
            String uuid = IdUtil.simpleUUID();

            //获取上传文件名字字符串,格式：'form-data;name="pic";filename="2.jpg"'
            //   含义：传递过来的表单文件域name=pic，里面传递的上传文件名2.jpg
            String header = part.getHeader("content-disposition");

            //获取文件扩展名(获取到".jpg")
            String fileExtName = header.substring(header.lastIndexOf("."), header.length() - 1);

            //生成文件名
            String fileName = uuid + fileExtName;

            //d. 将上传的文件写入服务器磁盘
            //确定上传文件目录：pic
            //pic和写入磁盘的文件名相对路径
            String realPath = "pic/"+fileName;
            //获取pic目录的绝对路径
            String path = getServletContext().getRealPath(realPath);
            //写入磁盘
            part.write(path);
            //删除服务器的临时文件
            part.delete();

            //e. 将上传成功的文件相对路径封装到user对象，目的写入到数据库
            user.setPic(realPath);
        }


        //3.调用业务修改用户数据并获取最新用户数据
        User updateUser = userService.update(user);

        //4.更新session中的用户登录数据
        if(updateUser!=null){
            request.getSession().setAttribute("loginUser",updateUser);
            //5.跳转到updateInfo_ok.jsp页面显示最新的数据
            response.sendRedirect(request.getContextPath()+"/updateInfo_ok.jsp");
        }else{
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }

    }

    //目标：处理显示用户中心信息
    private void userInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1.获取登录数据
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        //2.判断是否登录
        if(loginUser!=null) {

            //2.1 登录了，跳转到home_index.jsp显示用户数据
            response.sendRedirect(request.getContextPath()+"/home_index.jsp");
        }else {
            //2.2 没有登录，跳转首页
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }

    }

    //目标：注销
    private void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //立即销毁session
        request.getSession().invalidate();

        //跳转到首页
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    //目标：处理手机验证码登录异步请求
    private void telLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1.校验验证码
        //获取手机号
        String telephone = request.getParameter("telephone");

        //获取session中的验证码
      //  String serverCheckCode = (String) request.getSession().getAttribute("SERVERCODE_" + telephone);
       //从Redis获取验证码
        Jedis jedis=JedisUtils.getJedis();
        String sessionCode = jedis.get("smsCode_" + telephone);

        //获取用户输入的验证码
        String userCode = request.getParameter("smsCode");

        //定义返回对象ResultInfo
        ResultInfo resultInfo = null;
        if(!sessionCode.equalsIgnoreCase(userCode)){
            resultInfo = new ResultInfo(false,"验证码错误");
        }else {

            //验证码使用完成后删除
            request.getSession().removeAttribute("SERVERCODE_" + telephone);

            //2 校验手机号，并获取登录用户对象
            User loginUser = userService.findByTelephone(telephone);

            if(loginUser == null) {
                //3.校验通过后，将登录用户数据写入session
                //手机号不存在
                resultInfo = new ResultInfo(false,"手机号错误");
            }else{
                //登录成功，清除缓存
                jedis.del("smsCode_"+telephone);
                request.getSession().setAttribute("loginUser",loginUser);
                resultInfo = new ResultInfo(true,"登录成功");

            }
        }
        //3.返回resultInfo对象转换为json输出
        toJsonString(response,resultInfo);
    }

    //目标：处理异步密码登录
    private void pwdLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1.获取表单所有数据封装到user对象中
        //实例对象
        User user = new User();
        //获取map
        Map<String, String[]> map = request.getParameterMap();
        //BeanUtils封装数据
        BeanUtils.populate(user,map);

        //2.调用业务登录获取返回ResulInfo数据
        ResultInfo resultInfo = userService.pwdLogin(user);

        //3.判断的登录是否成功
        if(resultInfo.getSuccess()) {
            //3.1  如果成功，将数据库用户对象写入到session中
            request.getSession().setAttribute("loginUser",resultInfo.getData());
        }
        //4.将resultInfo转换为json字符串返回
        toJsonString(response,resultInfo);
    }

    //实例业务对象
    //private UserService userService = new UserServiceImpl();
    //上面的代码具有强耦合性：因为当前Servlet依赖2个东西：UserService接口和UserServiceImpl实现类
    //目标：让Servlet只依赖UserService接口，如下使用静态工厂解决
    private UserService userService = (UserService) BeanFactory.getBean("userService");
    //最终：依赖UserService接口和静态工厂

    //目标：处理发送短信异步请求
    private void sendSms(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取手机号
        String telephone = request.getParameter("telephone");

        //2.生成随机验证码6个数字
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println("验证码："+code);

        //3.调用业务发送手机验证码，传递（手机号+验证码）；
        ResultInfo resultInfo = userService.sendSms(telephone, code);
        //ResultInfo resultInfo = new ResultInfo(true,"短信发送成功");

        //4.判断发送短信状态
        if(resultInfo.getSuccess()) {
            //5.如果短信发送成功，将验证码存储到session中
           // request.getSession().setAttribute("SERVERCODE_"+telephone,code);
            //**手机发送验证码成功后，将原来session写入修改为将验证码写入到Redis缓存
            //**获取jedis连接
            Jedis jedis= JedisUtils.getJedis();
            //写入
            jedis.setex("smsCode"+telephone,300,code);

            //关闭资源
            jedis.close();
        }
        //6.将ResultInfo转换为json返回
        toJsonString(response,resultInfo);
    }

    //目标：处理异步用户名校验请求
    private void checkUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取用户名
        String username = request.getParameter("username");

        //2.调用业务校验用户名并获取结果
        ResultInfo resultInfo = userService.checkUserName(username);

        //3.将返回结果ResultInfo对象转换为json字符串返回
        toJsonString(response,resultInfo);
    }

    //目标：处理用户注册请求
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取jedis
        Jedis jedis=JedisUtils.getJedis();
        try {

            //目标2：处理用户注册的同步请求校验验证码
            //增加操作：校验验证码，校验通过后才可以用户注册
            //a.获取用户输入的验证码
            String userCheckCode = request.getParameter("smsCode");
            //获取手机号

            //b.获取session中生成的验证码
            //String serverCheckCode = (String) request.getSession().getAttribute("SERVERCODE_" + telephone);
            //校验验证码，需要将原来读取session修改为读取Redis缓存验证码进行校验
            //读取缓存数据
            String telephone = request.getParameter("telephone");
            String sessionCode=jedis.get("smsCode_"+telephone);

            //c.比较2个验证码，如果值不一样封装返回验证码错误
            if(!userCheckCode.equalsIgnoreCase(sessionCode)){
                //封装验证码失败
                ResultInfo resultInfo=new ResultInfo(false,"验证码错误");
                request.setAttribute("resultInfo",resultInfo);

                request.getRequestDispatcher("/register.jsp").forward(request,response);
                return;
            }




            //1.获取注册数据封装到User对象中
            //实例User对象
            User user = new User();
            //获取所有请求数据的Map
            Map<String, String[]> map = request.getParameterMap();
            //使用BeanUtils封装数据
            BeanUtils.populate(user,map);

            //2.调用业务层进行用户注册获取注册结果
            ResultInfo resultInfo = userService.register(user);

            //3.判断注册结果
            if(resultInfo.getSuccess()) {
                //注册成功，将缓存的对应验证码删除
                jedis.del("smsCode_"+telephone);

                //3.1 注册成功，跳转到register_ok.jsp页面
                response.sendRedirect(request.getContextPath()+"/register_ok.jsp");
                //验证码校验通过后，删除验证码
                 request.getSession().removeAttribute("code");
            }else {
                //3.2 注册失败，将错误消息存储到请求域中，跳转register.jsp页面显示错误消息
                request.setAttribute("resultInfo",resultInfo);
                request.getRequestDispatcher("/register.jsp").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }finally {
            //关闭jedis
            jedis.close();;
        }
    }
}