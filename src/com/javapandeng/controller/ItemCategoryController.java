package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.ItemCategory;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Author tjy
 * @Date 2020/7/26 19:35
 */
@Controller
@RequestMapping("itemCategory")
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

    @RequestMapping("add")
    public String add(){
        return "itemCategory/add";
    }

    @RequestMapping("exAdd")
    public String exadd(ItemCategory itemCategory){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql";

    }

    @RequestMapping("update")
    public String update(Integer id,Model model){
        ItemCategory load = itemCategoryService.load(id);
        model.addAttribute("obj",load);
        return "itemCategory/update";
    }

    @RequestMapping("exUpdate")
    public String exUpdate(ItemCategory itemCategory){
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql";
    }

    @RequestMapping("delete")
    public String delete(Integer id){
        ItemCategory load = itemCategoryService.load(id);
        load.setIsDelete(1);
        itemCategoryService.updateById(load);
        String sql = "update item_category set isDelete = 1 where pid ="+id;
        itemCategoryService.updateBysql(sql);
        return "redirect:/itemCategory/findBySql";
    }

    @RequestMapping("findBySql2")
    public String findBySql2(Model model, ItemCategory itemCategory){
        String sql = "select * from item_category where isDelete = 0 and pid =" +itemCategory.getPid() + " order by id";
//
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory2";

    }

    @RequestMapping("update2")
    public String update2(Integer id,Model model){
        ItemCategory load = itemCategoryService.load(id);
        model.addAttribute("obj",load);
        return "itemCategory/update2";
    }

    @RequestMapping("exUpdate2")
    public String exUpdate2(ItemCategory itemCategory,RedirectAttributes attr){
        itemCategoryService.updateById(itemCategory);
        attr.addAttribute("pid",itemCategory.getPid());
        return "redirect:/itemCategory/findBySql2";
    }

    @RequestMapping("/add2")
    public String add2(Integer pid,Model model){
        model.addAttribute("pid",pid);
        return "itemCategory/add2";
    }

    @RequestMapping("exAdd2")
    public String exAdd2(ItemCategory itemCategory, RedirectAttributes attr){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        attr.addAttribute("pid",itemCategory.getPid());
        return "redirect:/itemCategory/findBySql2";
    }

    @RequestMapping("delete2")
    public String delete2(Integer id, RedirectAttributes attr){
        ItemCategory load = itemCategoryService.load(id);
        load.setIsDelete(1);
        itemCategoryService.updateById(load);
        attr.addAttribute("pid",load.getPid());
        return "redirect:/itemCategory/findBySql2";
    }
}
