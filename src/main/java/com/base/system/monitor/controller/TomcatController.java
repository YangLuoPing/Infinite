package com.base.system.monitor.controller;

/**
 * tomcat信息
 * 使用metrics监控的信息
 * 
 * /actuator/metrics/tomcat.sessions.created  tomcat 已创建 session 数  单位：个
 * /actuator/metrics/tomcat.sessions.expired  tomcat 已过期 session 数  单位：个
 * /actuator/metrics/tomcat.sessions.active.current  tomcat 当前活跃 session 数  单位：个
 * /actuator/metrics/tomcat.sessions.active.max  tomcat 活跃 session 数峰值  单位：个
 * /actuator/metrics/tomcat.sessions.rejected  超过session 最大配置后，拒绝的 session 个数  单位：个
 * /actuator/metrics/tomcat.global.sent  发送的字节数  单位：bytes
 * /actuator/metrics/tomcat.global.request.max  request 请求最长耗时  单位：秒
 * /actuator/metrics/tomcat.global.request.count  全局 request 请求次数  单位：次
 * /actuator/metrics/tomcat.global.request.totalTime  全局 request 请求总耗时  单位：秒
 * /actuator/metrics/tomcat.servlet.request.max  servlet 请求最长耗时  单位：秒
 * /actuator/metrics/tomcat.servlet.request.count  servlet 总请求次数  单位：次
 * /actuator/metrics/tomcat.servlet.request.totalTime  servlet 请求总耗时  单位：秒
 * /actuator/metrics/tomcat.threads.current  tomcat 当前线程数（包括守护线程）  单位：个
 * /actuator/metrics/tomcat.threads.config.max  tomcat 配置的线程最大数  单位：个
 * @author ylg  2020-03-19
 */
public class TomcatController {

}
