/**
 *
 */
package com.bkdwei.ExceptionAOP;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author bkd
 * @Date 2016年8月3日
 */

@Controller
@RequestMapping("/exception")
public class ExceptionDao {
    @RequestMapping("/test")
    public void generateException() {
        final int a = 12 / 0;
    }

}
