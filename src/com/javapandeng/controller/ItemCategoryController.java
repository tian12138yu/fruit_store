package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.ItemCategory;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author tjy
 * @Date 2020/7/26 19:35
 */
@Controller
@RequestMapping("item")
public class ItemCategoryController extends BaseController {
    @Autowired
    ItemCategoryService itemCategoryService;

    @RequestMapping("findBySql")
    public String findBySql(Model model, ItemCategory itemCategory){
        String sql = "select * from item_category where isDelete = 0 and pid is null order by id";
//
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory";

    }
}
