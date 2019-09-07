package com.forloop.springboot.contoller;

import com.forloop.springboot.configuration.BasicConfiguration;
import com.forloop.springboot.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @Autowired
    private WelcomeService service;

    @Autowired
    private BasicConfiguration basicConfiguration;

    @RequestMapping("/welcome")
    public String welcome() {
        return service.retrieveWelcomeMessage();
    }

    @RequestMapping("/dynamic-configuration")
    public Map dynamicConfiguration(){
        Map map=new HashMap();
        map.put("message",basicConfiguration.getMessage());
        map.put("number",basicConfiguration.getNumber());
        map.put("value",basicConfiguration.isValue());
        return map;
    }
}
