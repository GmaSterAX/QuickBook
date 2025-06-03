package com.softwarearchitecture.QuickBook.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private ApplicationContext applicationContext;
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("Test controller çalışıyor!");
        return "Test OK";
    }


    @GetMapping("/beans")
    @ResponseBody
    public String listBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        StringBuilder sb = new StringBuilder();
        for (String beanName : beanNames) {
            if (beanName.contains("Global") || beanName.contains("controller")) {
                sb.append(beanName).append("<br>");
            }
        }
        return sb.toString();
    }
}