package com.joins.helloworld.controller;

import lombok.extern.java.Log;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
public class LoginController {
    @GetMapping("/login")
    public void login() {
    }

    @GetMapping("/accessDenied")
    public void accessDenied() {
    }

    @GetMapping("/logout")
    public void logout() {
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/adminSecret")
    public void forAdminSecret() {
        log.info("admin secret");
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping("/managerSecret")
    public void forManegerSecret() {
        log.info("manager secret");
    }
}