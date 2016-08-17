package com.bkdwei.testCase.helloController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 测试springmvc的前后台传值
 *
 * @author bkd
 * @Date 2016年8月17日
 */
@Controller
@RequestMapping("/hello")
public class Hello1 {

    @RequestMapping("/returnHello")
    public ModelAndView returnHello() {
        System.out.println("in return hello method!");
        return new ModelAndView("hello");
    }

    @RequestMapping("/sayHello1")
    public void sayHello() {
        System.out.println("in hello method!");
    }
}
