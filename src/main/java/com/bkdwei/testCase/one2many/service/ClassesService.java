/**
 *
 */
package com.bkdwei.testCase.one2many.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkdwei.core.BaseService;
import com.bkdwei.model.Classes;
import com.bkdwei.testCase.baseDao.UserDao;
import com.bkdwei.testCase.one2many.dao.ClassesDao;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
@Service
public class ClassesService extends BaseService<Classes> {

    public static void main(final String args[]) {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "bkdwei");
        new UserDao().findObjectByMap(map);
        new ClassesDao().findObjectByMap(map);
    }

    @Autowired
    private ClassesDao classesDao;

    public Classes findClassesByName(final String name) {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("ClassName", name);
        return classesDao.findObjectByMap(map);
    }
}
