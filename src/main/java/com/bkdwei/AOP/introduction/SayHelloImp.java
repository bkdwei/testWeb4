/**
 *
 */
package com.bkdwei.AOP.introduction;

/**
 * 引入增强的实现类
 *
 * @author bkd
 * @Date 2016年8月5日
 */
public class SayHelloImp implements ISayHello {

    /*
     * (non-Javadoc)
     * @see com.bkdwei.AOP.IIntroductionAdviceSample#sayHello()
     */
    public void sayHello() {
        System.out.println("in SayHelloImp class,saying hello.");
    }

}
