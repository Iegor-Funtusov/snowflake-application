package com.smartfoxpro.snowflake.controller;

import com.smartfoxpro.snowflake.entity.DBConnection;
import com.smartfoxpro.snowflake.service.DBConnectionService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 9:11 AM
 */

@Controller
@AllArgsConstructor
@RequestMapping("/connection")
public class ConnectionController {

    private final DBConnectionService dbConnectionService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("connections", dbConnectionService.findAll());
        return "connection/connections";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("connection", new DBConnection());
        return "connection/add-connection";
    }

    @PostMapping("/addNewConnection")
    public String createNewDBConnection(@Valid DBConnection connection, BindingResult result) {
        if (result.hasErrors()) {
            return "connection/add-connection";
        }
        dbConnectionService.createNewDBConnection(connection);
        return "redirect:/connection";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateFormDBConnection(@PathVariable("id") long id, Model model) {
        DBConnection connection = dbConnectionService.findDBConnectionById(id);
        model.addAttribute("connection", connection);
        return "connection/update-connection";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid DBConnection connection, BindingResult result) {
        if (result.hasErrors()) {
            connection.setId(id);
            return "connection/update-connection";
        }
        dbConnectionService.updateDBConnection(connection);
        return "redirect:/connection";
    }
}
