package com.example.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pan.entity.User;
import com.example.pan.idcode.Code;
import com.example.pan.mapper.UserMapper;
import com.example.pan.util.ResultHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 登录成功后，根据登录成功后的用户权限去操作接口，demo中只有admin和common角色，
 * admin可以增加、删除、更新、读取，common用户只能读取，拿用户管理类TUserController作为例子讲解
 * </p>
 *
 * @author daShen
 * @since 2022-04-05
 */
@RestController
@RequestMapping(value = "/user")
public class Usercontroller {

    private static final Logger log = LoggerFactory.getLogger(Usercontroller.class);

    @Autowired
    UserMapper userMapper;

    @RequiresRoles("admin")
    @RequiresPermissions({"update"})
    @PostMapping("/saveorupdate")
    public String saveOrUpdate(@RequestBody User user) {
        log.info("/saveorupdate,user={}",user);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            if (user == null) {
                jsonObject.put("code", Code.FAIL.getCode());
                jsonObject.put("msg", "null");
            }
            userMapper.updateById(user);
            return "success";
        } catch (Exception e) {
            log.info("system exception,e={}", e);
        }
        return jsonObject.toString();
    }

    @RequiresRoles("admin")
    @RequiresPermissions("delete")
    @GetMapping("/delete")
    public String delete(Long id){
        log.info("/delete, id={}", id);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            if (id == null){
                return ResultHandler.handler(jsonObject, Code.FAIL.getCode(), "param null");
            }
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            userMapper.delete(wrapper);
            ResultHandler.handler(jsonObject, Code.SUCCESS.getCode(), "Success");
        } catch (Exception e) {
            log.info("System Exception,e={}", e);
            return ResultHandler.handler(jsonObject, Code.EXCEP.getCode(), "System Exception");
        }
        return jsonObject.toString();
    }

    @PostMapping("/retrieve")
    public String retrieve(@RequestBody User user){
        log.info("/retrieve, TUser={}", user);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            if (user == null){
                return ResultHandler.handler(jsonObject, Code.FAIL.getCode(), "param null");
            }
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if (user.getUsername() != null){
                queryWrapper.lambda().eq(User::getUsername, user.getUsername());
            }
            if (!StringUtils.isEmpty(user.getUsername())){
                queryWrapper.lambda().eq(User::getUsername, user.getUsername());
            }
            List<User> list = userMapper.selectList(queryWrapper);
            ResultHandler.handler(jsonObject, Code.SUCCESS.getCode(), "Success", list);
        } catch (Exception e) {
            log.info("System Exception,e={}", e);
            return ResultHandler.handler(jsonObject, Code.EXCEP.getCode(), "System Exception");
        }
        return jsonObject.toString();
    }

}
