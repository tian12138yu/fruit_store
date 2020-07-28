package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.Manage;
import com.javapandeng.service.ManageService;
import com.javapandeng.service.impl.ManageServiceImpl;
import com.javapandeng.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


/**
 * @Author tjy
 * @Date 2020/7/26 14:31
 */

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    ManageService manageService;
    @RequestMapping("/admin")
    public String login(){
        return "/login/login";
    }

    @RequestMapping("/toLogin")
    public String tologin(Manage manage, HttpServletRequest request){
        Manage byEntity = manageService.getByEntity(manage);
        if (byEntity == null){
            return "redirect:/login/loginOut";
        }
        request.getSession().setAttribute(Consts.MANAGE,byEntity);
        return "/login/admin";
    }

    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request){
        request.getSession().setAttribute(Consts.MANAGE,null);
        return "/login/login";
    }

    @RequestMapping("uIndex")
    public String uIndex(){
        return "login/uIndex";
    }

    @RequestMapping("uLogin")
    public String uLogin(){
        return "login/uLogin";
    }
}
