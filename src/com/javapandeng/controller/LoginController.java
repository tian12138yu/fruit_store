package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.*;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.service.ItemService;
import com.javapandeng.service.ManageService;
import com.javapandeng.service.UserService;
import com.javapandeng.service.impl.ManageServiceImpl;
import com.javapandeng.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author tjy
 * @Date 2020/7/26 14:31
 */

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    ManageService manageService;
    @Autowired
    ItemCategoryService itemCategoryService;
    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;

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

    @RequestMapping("/uIndex")
    public String uIndex(Model model, Item item, HttpServletRequest request){
        String sql1 = "select * from item_category where isDelete=0 and pid is null order by name";
        List<ItemCategory> fatherList = itemCategoryService.listBySqlReturnEntity(sql1);
        List<CategoryDto> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(fatherList)){
            for(ItemCategory ic:fatherList){
                CategoryDto dto = new CategoryDto();
                dto.setFather(ic);
                //查询二级类目
                String sql2 = "select * from item_category where isDelete=0 and pid="+ic.getId();
                List<ItemCategory> childrens = itemCategoryService.listBySqlReturnEntity(sql2);
                dto.setChildrens(childrens);
                list.add(dto);
                model.addAttribute("lbs",list);
            }
        }
        //折扣商品
        List<Item> zks = itemService.listBySqlReturnEntity("select * from item where isDelete=0 and zk is not null order by zk desc limit 0,10");
        model.addAttribute("zks",zks);

        //热销商品
        List<Item> rxs = itemService.listBySqlReturnEntity("select * from item where isDelete=0 order by gmNum desc limit 0,10");
        model.addAttribute("rxs",rxs);

        return "login/uIndex";
    }
    /**普通用户注册*/
    @RequestMapping("/res")
    public String res(){
        return "login/res";
    }

    /**执行普通用户注册*/
    @RequestMapping("/toRes")
    public String toRes(User u){
        userService.insert(u);
        return "login/uLogin";
    }

    @RequestMapping("uLogin")
    public String uLogin(){
        return "login/uLogin";
    }

    /**执行普通用户登录*/
    @RequestMapping("/utoLogin")
    public String utoLogin(User u,HttpServletRequest request){
        User byEntity = userService.getByEntity(u);
        if(byEntity==null){
            return "redirect:/login/res";
        }else {
            request.getSession().setAttribute("role",2);
            request.getSession().setAttribute(Consts.USERNAME,byEntity.getUserName());
            request.getSession().setAttribute(Consts.USERID,byEntity.getId());
            return "redirect:/login/uIndex";
        }
    }

    /**前端用户退出*/
    @RequestMapping("/uTui")
    public String uTui(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login/uIndex.action";
    }
}
