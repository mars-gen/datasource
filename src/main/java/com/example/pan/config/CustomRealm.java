package com.example.pan.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pan.entity.User;
import com.example.pan.mapper.FoodMapper;
import com.example.pan.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  自定义的realm
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
public class CustomRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(CustomRealm.class);
    @Autowired
    private FoodMapper foodmapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("CustomRealm.doGetAuthorizationInfo,PrincipalCollection={}",principalCollection);
        User user = (User) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String role = user.getRole(); //获取角色
        info.addRole(role);
        String perms = user.getPerms(); //获取权限
        info.addStringPermission(perms);


        return info;
    }

    /**
     * 用户调用登录接口时调用该方法,校验用户合法性
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("CustomRealm.doGetAuthenticationInfo,AuthenticationToken={}",authenticationToken);
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        String userName = authenticationToken.getPrincipal().toString();
       /* QueryWrapper<User> queryWrapper = new QueryWrapper<>(); //用于封装User对象 User是要查询的实体数据
        queryWrapper.lambda().eq(User::getUsername,userName); //eq :=  where User.getUsername=username 也就是条件查询*/
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, userName);
        List<User> users = userMapper.selectList(wrapper);
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        for (User user : users) {
            if (user == null) {
                throw new UnknownAccountException();
            }
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
        return simpleAuthenticationInfo;
    }
}
