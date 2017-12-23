package com.dobro.remind.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/remind")
public class RemindController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getRemind(ModelMap model) {
        return "Hello Remind!";
    }
}
