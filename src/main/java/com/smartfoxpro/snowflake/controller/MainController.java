package com.smartfoxpro.snowflake.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 10:30 AM
 */

@Controller
public class MainController {

    @GetMapping
    public String index() {
        return "redirect:/connection";
    }
}
