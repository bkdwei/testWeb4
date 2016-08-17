/**
 *
 */
package com.bkdwei.utils;

import org.slf4j.Logger;

/**
 * 重构每个类的logger对象，后面找到这部分反射再更改
 *
 * @author bkd
 * @Date 2016年8月17日
 */
public class Log4JUtils {
    //TODO 见上
    public static Logger getLogger() {
        return null;
        /*
         * Logger logger = null;
         * if (null == logger) {
         * //Java8 废弃了Reflection.getCallerClass()
         * logger =
         * // logger = LogFactory.getLog(Reflection.getCallerClass().getName());
         * // logger.debug("调用者类名" + Reflection.getCallerClass().getName());
         * }
         */
        // return LoggerFactory.getLogger(Call);
    }
}
