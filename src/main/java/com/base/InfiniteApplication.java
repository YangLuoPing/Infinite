package com.base;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// spring配置类

/**
 * @SpringBootConfiguration ：sprring boot配置类
 * @Configuration : spring配置类
 * @Component ：有此注解代表是一个spring组件
 * @EnableAutoConfiguration 自动配置
 * @AutoConfigurationPackage 自动配置包
 * @Import({Registrar.class}) 自动配置 ‘包注册’
 * @Import({AutoConfigurationImportSelector.class}) 自动配置导入选择
 * getAutoConfigurationEntry方法下的
 * List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes); 获取所有的配置
 * SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
 * return EnableAutoConfiguration.class; 加载配置
 * 配置的核心文件 META-INF/spring.factories.（spring-boot-autoconfigure）
 */
//, DataSourceAutoConfiguration.class  datasource 快速配置类
//RedisAutoConfiguration.class,RedisRepositoriesAutoConfiguration.class  redis 快速配置类
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
//排除 原生Druid的快速配置类。
@ServletComponentScan//druid监控页面是一个servlet，需要让SpingBoot支持servlet
@EnableAsync //开启异步注解功能
@EnableScheduling //开启基于注解的定时任务
@EnableHasor()      // 在Spring 中启用 Hasor
@EnableHasorWeb()   // 将 hasor-web 配置到 Spring 环境中，Dataway 的 UI 是通过 hasor-web 提供服务。
public class InfiniteApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfiniteApplication.class, args);
	}

}
