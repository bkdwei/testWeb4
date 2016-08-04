package com.bkdwei.ControllerTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello2 {
    @InitBinder
    void myInit() {
        System.out.println("init!");
    }

    @RequestMapping("/sayHello2")
    public void sayHello() {
        System.out.println("in hello method!");
    }
}
