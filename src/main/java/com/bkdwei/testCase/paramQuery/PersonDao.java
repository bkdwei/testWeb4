/**
 *
 */
package com.bkdwei.testCase.paramQuery;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.bkdwei.core.BaseDao;
import com.bkdwei.model.Person;

/**
 * @author bkd
 * @Date 2016年8月8日
 */
@Repository
public class PersonDao extends BaseDao<Person, Serializable> {

}
