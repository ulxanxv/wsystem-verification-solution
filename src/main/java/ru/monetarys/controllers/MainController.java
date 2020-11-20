package ru.monetarys.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/")
public class MainController {

    @GetMapping
    public String welcome(Model model) {
        model.addAttribute("name", ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return "welcome";
    }

}
