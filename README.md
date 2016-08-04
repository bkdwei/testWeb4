# testWeb4
自己搭建的项目

项目特点：
  集成spring和hibernate
  集成slf4j和log4j，并将不同级别的日志信息输出到不同的日志文件
  使用spring的AOP框架来支持声明式事务管理
  使用AspectJ注解拦截异常信息，并记录到日志文件:ExceptionLog.java
  做好单表的通用dao，实现简单的增删改查
  做好了简单的前后台传值功能：用户管理
  将springMVC-servlet.xml的配置信息拆分成多个spring-*.xml文件
  将数据库配置独立出来放到jdbc.properties


