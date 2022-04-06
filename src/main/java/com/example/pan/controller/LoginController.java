package com.example.pan.controller;

import com.example.pan.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-05
 */
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @RequestMapping(value = "/login")
    public String login(User user) {
        log.info("/login,user={}", user);
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            //进行验证  这里可以捕获异常 返回对应信息
            subject.login(token);

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "login error";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有授权";
        }
        return "login success";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        log.info("/logout");
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            User username = (User) SecurityUtils.getSubject().getPrincipal();
            log.info("username={}", username);
        }
        return "redirect:/index.html";
    }

}
