package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;



    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }




    @PostMapping("/new")
    public String newUser(@ModelAttribute("user") User user,
                          @RequestParam("roles") ArrayList<Long> roles) {

        Set<Role> roleSet = new HashSet<>((Collection<? extends Role>)
                roleService.getRolesById(roles));
        user.setRoles(roleSet);
        userService.save(user);
//            @ModelAttribute("user") User user
//            , @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles
//    ){
//        if (selectedRoles != null) {
//            Set<Role> roles = new HashSet<>();
//            Role role = new Role();
//            for (String elemRoles : selectedRoles) {
//                roles.add(roleService.getRoleById());
//            }
//            user.setRoles(roles);
//        }
        return "redirect:/admin/";
    }

    @GetMapping("/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("admin/new");
        mav.addObject("user", user);

        List<Role> roles = roleService.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @GetMapping("{id}/edit")
    public ModelAndView editUser(@PathVariable(name = "id") Long id) {
        User user = userService.showUser(id);
        ModelAndView mav = new ModelAndView("admin/edit");
        mav.addObject("user", user);

        List<Role> roles = roleService.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/";
    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@RequestParam(value = "id") Long id, Model model) {
//        model.addAttribute("user", userService.showUser(id));
//        model.addAttribute("roles", userService.getRoles());
//        return "/admin/edit";
//    }

    @GetMapping("/")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "/admin/users";
    }
    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user) {
        userService.remove(user);
        return "redirect:/admin/";
    }

    @GetMapping("/user/{id}")
    public String showUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.showUser(id));
        return "/admin/user";
    }

}
