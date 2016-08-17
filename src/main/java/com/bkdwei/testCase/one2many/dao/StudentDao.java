/**
 *
 */
package com.bkdwei.testCase.one2many.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.bkdwei.core.BaseDao;
import com.bkdwei.model.Student;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
@Repository
public class StudentDao extends BaseDao<Student, Serializable> {

}
