package com.base.system.monitor.controller;

/**
 * 监控jvm的信息
 * 使用metrics监控Jvm的信息
 * 访问地址 /actuator/metrics/*
 * /actuator/metrics/jvm.memory.max  JVM 最大内存   单位：MB
 * /actuator/metrics/jvm.memory.committed  JVM 可用内存   单位：MB
 * /actuator/metrics/jvm.memory.used  JVM 已用内存   单位：MB
 * /actuator/metrics/jvm.buffer.memory.used  JVM 缓冲区已用内存   单位：MB
 * /actuator/metrics/jvm.buffer.count  当前缓冲区数量   单位：个
 * /actuator/metrics/jvm.threads.daemon  JVM 守护线程数量   单位：个
 * /actuator/metrics/jvm.threads.live  JVM 当前活跃线程数量   单位：个
 * /actuator/metrics/jvm.threads.peak  JVM 峰值线程数量   单位：个
 * /actuator/metrics/jvm.classes.loaded  JVM 已加载 Class 数量   单位：个
 * /actuator/metrics/jvm.classes.unloaded  JVM 未加载 Class 数量   单位：个
 * /actuator/metrics/jvm.gc.memory.allocated  GC 时, 年轻代分配的内存空间   单位：MB
 * /actuator/metrics/jvm.gc.memory.promoted  GC 时, 老年代分配的内存空间   单位：MB
 * /actuator/metrics/jvm.gc.max.data.size  GC 时, 老年代的最大内存空间   单位：MB
 * /actuator/metrics/jvm.gc.live.data.size  FullGC 时, 老年代的内存空间   单位：MB
 * /actuator/metrics/jvm.gc.pause.count  系统启动以来GC 次数   单位：次
 * /actuator/metrics/jvm.gc.pause.totalTime  系统启动以来GC 总耗时   单位：秒
 * @author ylg  2020-03-19
 */
public class JvmController {

}
