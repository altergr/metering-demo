package com.github.altergr.meteringdemo.rest.home;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToSwaggerUi() {

        return "redirect:/swagger-ui.html";
    }
}