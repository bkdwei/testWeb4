package com.bkdwei.t1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/t1")
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
