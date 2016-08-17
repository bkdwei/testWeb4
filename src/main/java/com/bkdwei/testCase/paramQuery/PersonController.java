/**
 *
 */
package com.bkdwei.testCase.paramQuery;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试带参数的查询，该模块后期可删除
 *
 * @author bkd
 * @Date 2016年8月8日
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    /*
     * @Autowired
     * private ClassesService classesService;
     */

    @Autowired
    private PersonService personService;

    @RequestMapping("/findByName")
    public void findPersonByName(final String name) {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("className", name);
        personService.findObjectByMap(map);
        //classesService.findClassesByName(name);
    }
}
