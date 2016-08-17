package com.bkdwei.testCase.baseDao;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bkdwei.AOP.introduction.ISayHello;
import com.bkdwei.model.User;

/**
 * 测试基于hibernate的通用dao：BaseDao
 *
 * @author bkd
 * @Date 2016年8月17日
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService   userService;

    @RequestMapping("/addUser")
    public ModelAndView addUser(final User user) {
        userService.save(user);
        logger.info("新增用户" + user.getName());
        return new ModelAndView("user/result", "result", "save user successfully.");
    }

    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(final String id) {
        final int tmpId = Integer.parseInt(id);
        userService.delete(tmpId);
        return new ModelAndView("user/result", "result", "delete user successfully.");
    }

    @RequestMapping("/deleteUserByName")
    public ModelAndView deleteUserByName(final String name) {
        final User user = userService.findUserByName(name);
        userService.delete(user.getId());
        logger.error("删除用户" + name + "的数据");
        return new ModelAndView("user/result", "result", "delete user by name successfully.");
    }

    @RequestMapping("/getUser")
    public ModelAndView getUser(final int id) {
        final User u1 = userService.findById(Integer.valueOf(id));
        final HashMap<String, User> map = new HashMap<String, User>();
        map.put("user", u1);
        /* 引入增强 */
        final ISayHello userServiceEnhance = (ISayHello) userService;
        userServiceEnhance.sayHello();
        return new ModelAndView("user/showUser", map);

    }

    @RequestMapping("/listAll")
    public ModelAndView listAll() {
        final HashMap<String, List<User>> map = new HashMap<String, List<User>>();
        map.put("userList", userService.findAll());
        UserController.logger.info("查询所有数据i。");
        UserController.logger.error("查询所有数据e。");
        UserController.logger.debug("查询所有数据d。");
        UserController.logger.trace("查询所有数据t。");
        UserController.logger.warn("查询所有数据w。");
        return new ModelAndView("user/listAll", map);
    }

    @RequestMapping("/queryUserByName")
    public ModelAndView queryUserByName(final String name) {
        final HashMap<String, User> map = new HashMap<String, User>();
        map.put("user", userService.findUserByName(name.trim()));
        logger.debug("查询" + name + "的数据。");
        return new ModelAndView("user/showUser", map);
    }

    @RequestMapping("/updateUserByName")
    public ModelAndView saveUser(final User user) {
        final String name = user.getName();
        final User dbUser = userService.findUserByName(name);
        dbUser.setPassword(user.getPassword());
        userService.saveOrUpdate(dbUser);
        return new ModelAndView("user/result", "result", "update user by name successfully.");
    }
}
