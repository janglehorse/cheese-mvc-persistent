package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by adminbackup on 4/10/17.
 */

@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    MenuDao menuDao;
    @Autowired
    CheeseDao cheeseDao;


    @RequestMapping(value="")
    public String index(Model model){

        model.addAttribute("title", "Menu List");
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";

    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String add(Model model){

        model.addAttribute("title", "Add New Menu");
        model.addAttribute(new Menu());

        return "menu/add";

    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String processAddMenuForm(@ModelAttribute @Valid Menu newMenu,
                                     Errors errors,
                                     Model model
                                     ){

        if(errors.hasErrors()){
            model.addAttribute("title", "Add New Menu");
            return "menu/add";

        }

        menuDao.save(newMenu);

        return "redirect:view/" + newMenu.getId();

    }

    @RequestMapping("view/{menuId}")
    public String viewMenu(Model model,
                           @PathVariable int menuId){

        Menu theMenu = menuDao.findOne(menuId);
        model.addAttribute("title", theMenu.getName());
        model.addAttribute("menu", theMenu);
        return "menu/detail";
    }

    @RequestMapping(value="add-item/{menuId}", method=RequestMethod.GET)
    public String addItem(Model model,
                          @PathVariable int menuId){

        AddMenuItemForm form = new AddMenuItemForm(menuDao.findOne(menuId), cheeseDao.findAll());
        model.addAttribute("form", form);
        model.addAttribute("title", "Add Cheese to " + menuDao.findOne(menuId).getName());
        return "menu/add-item";
    }

    @RequestMapping(value="add-item", method = RequestMethod.POST)
    public String addItem(Model model,
                          @ModelAttribute @Valid AddMenuItemForm form,
                          Errors errors
                          ){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Cheese to " + menuDao.findOne(form.getMenuId()));
            model.addAttribute("form", form);
            return "menu/add-item";
        }

        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());
        Menu theMenu = menuDao.findOne(form.getMenuId());
        theMenu.addItem(theCheese);

        menuDao.save(theMenu);

        return "redirect:view/" + form.getMenuId();
    }

}
