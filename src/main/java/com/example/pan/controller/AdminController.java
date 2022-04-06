package com.example.pan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pan.entity.FilePath;
import com.example.pan.entity.User;
import com.example.pan.entity.admin;
import com.example.pan.mapper.FoodMapper;
import com.example.pan.mapper.FoodMapper_test;
import com.example.pan.mapper.PathMapper;
import com.example.pan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
@Controller
public class AdminController {
    @Autowired
    private FoodMapper_test foodMapper_test;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PathMapper pathMapper;
    @PostMapping("adminlogin")
    public String adminlogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        QueryWrapper<admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        List<admin> list = foodMapper.selectList(wrapper);
        if (list.size() == 0) {
            return "redirect:/adminlogin.html";
        } else {
            if (!list.get(0).getPassword().equals(password)) {
                return "redirect:/adminlogin.html";
            }else {
                session.setAttribute("username", username);
                session.setAttribute("password", password);
                return "redirect:/main.html";
            }
        }
    }

    @ResponseBody
    @GetMapping("getalluser")
    public List<User> getalluser(HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session == null) {
            return null;
        } else {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            return userMapper.selectList(wrapper);
        }
    }

    @ResponseBody
    @PostMapping("deleteuser")
    public String deleteuser(HttpServletRequest request)
    {
        try {
            String username=request.getParameter("username");
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            userMapper.delete(wrapper);
            return "delete 成功";
        }
        catch (Exception e)
        {
            return "delete 失败";
        }

    }
    @GetMapping("updateuser")
    public String updateuser(String username, Model model)
    {
        if(username.equals(null)||username.equals("")){}
        else
        {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            User user=userMapper.selectOne(wrapper);
            model.addAttribute("user",user);
        }
        return "updateuser";
    }
    @PostMapping("updateuser")
    public String updateuser(HttpServletRequest request,Model model)
    {
        try {
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            String details=request.getParameter("details");
            String phone=request.getParameter("phone");
            String perms = request.getParameter("perms");
            String role = request.getParameter("role");
            User user=new User(username,password,details,Long.parseLong(phone),perms,role);
            model.addAttribute("user",user);
            if(username.equals(null)||password.equals(null)||details.equals(null)||phone.equals(null))
            {
                model.addAttribute("msg","不许为空");
                return "updateuser";
            }
            else
            {
                QueryWrapper<User> wrapper = new QueryWrapper<>();
                wrapper.eq("username", username);
                userMapper.update(user, wrapper);
                model.addAttribute("msg","修改成功");
                return "updateuser";
            }}
        catch (Exception e)
        {
            model.addAttribute("msg","请注意格式");
            model.addAttribute("user",new User());
            return "updateuser";
        }

    }
    @ResponseBody
    @GetMapping("searchuser")
    public List<User> searchuser(String mohu,String details)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("mohu", mohu);
        map.put("details", details);
        return userMapper.selectByMap(map);

    }
    @ResponseBody
    @GetMapping("admingetpath")
    public List<FilePath> admingetpath()
    {
        QueryWrapper<FilePath> wrapper = new QueryWrapper<>();
        return pathMapper.selectList(wrapper);
    }

    @GetMapping ("changepwdadmin")
    public String changepasswordadmin(HttpServletRequest request,Model model)
    {
        model.addAttribute("name",request.getSession().getAttribute("username"));
        return "changepwdadmin";
    }
    @PostMapping("changepwdadmin")
    public ModelAndView changpasswordadmin(HttpServletRequest request)
    {
        ModelAndView model=new ModelAndView("changepwdadmin");
        HttpSession session=request.getSession();
        String passold=request.getParameter("passold");System.out.println(passold);
        String passnew1=request.getParameter("passnew1");System.out.println(passnew1);
        String passnew2=request.getParameter("passnew2");System.out.println(passnew2);
        System.out.println(session.getAttribute("username")+" "+session.getAttribute("password"));
        if(passold.equals(null)||passnew1.equals(null)||passnew2.equals(null))
        {
            model.addObject("msg","None  is not allowed");

            System.out.println("null");
        }
        else if(!passnew1.equals(passnew2))
        {
            model.addObject("msg","two  password is not equall!");
            System.out.println("passnew");
        }
        else if (passold.equals(session.getAttribute("password")))
        {
            foodMapper_test.changpasswordadmin((String) session.getAttribute("username"),passnew1);
            model.addObject("msg","update succed ");
            session.setAttribute("password",passnew1);
        }
        else{model.addObject("msg","password is wrong");System.out.println("true");}
        model.addObject("name",session.getAttribute("username"));
        return model;
    }

    @GetMapping("adduser")
    public String adduser1(HttpServletRequest request,Model model)
    {
        model.addAttribute("msg","");
        return "adduser";
    }

    @PostMapping("adduser")
    public String adduser(HttpServletRequest request,Model model)
    {
        try {
            HttpSession session=request.getSession();
            String username=(String) session.getAttribute("username");
            if(username.equals(null)||username.equals(""))
            {
                model.addAttribute("msg","请正确登陆后操作");
            }
            String name=request.getParameter("username");
            String password=request.getParameter("password");
            String details=request.getParameter("details");
            String phones=request.getParameter("phone");
            String perms = request.getParameter("perms");
            String role = request.getParameter("role");
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", name);
            List<User>userList= userMapper.selectList(wrapper);
            if(userList.size()>0){model.addAttribute("msg","用户名已存在，请从新输入");return "adduser";}
            long phone=Long.parseLong(phones);
            User user=new User(name,password,details,phone,perms,role);
            foodMapper_test.insertuser(user);
            model.addAttribute("msg","添加成功");
            return "adduser";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","注意格式");
            return "adduser";
        }

    }
}
