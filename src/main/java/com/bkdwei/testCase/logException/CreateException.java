/**
 *
 */
package com.bkdwei.testCase.logException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试ExceptionLogInterceptor拦截器，生成一个异常，并被拦截器捕获
 * 
 * @author bkd
 * @Date 2016年8月3日
 */

@Controller
@RequestMapping("/exception")
public class CreateException {
    @RequestMapping("/test")
    public void generateException() {
        final int a = 12 / 0;
    }

}
