/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2020/12/25
 * @Description
 */
package com.ymdx.redisson;

/**
 * @author YaoShuangQi
 * @create 2020/12/25 14:52
 * @version 1.0.0
 * @Description 设计 高效 分布式锁（需要适合多集群环境）
 */

/**
 * 高效分布式锁所需要考虑的关键点：
 * 1.互斥
 * 2.防死锁
 * 3.高性能  优雅等待锁（a.考虑锁的粒度小，b.锁的范围控制适当）
 * 4.锁可重入
 * */