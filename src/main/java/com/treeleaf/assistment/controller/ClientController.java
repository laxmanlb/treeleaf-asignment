/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.treeleaf.assistment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Laxman Baniya
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    @GetMapping(value = "/login")
    public String getMainpage() {
        return "auth";
    }
}
