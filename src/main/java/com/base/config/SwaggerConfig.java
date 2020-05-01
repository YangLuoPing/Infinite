package com.base.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


/**
 * swagger2 访问地址 http://localhost:8080/swagger-ui.html
 * bootstrap-ui 访问地址http://localhost:8080/doc.html
 * 常用注解
 * <p>
 * Swagger注解    简单说明
 *
 * @Api(tags = "xxx模块说明")	作用在模块类上
 * @ApiOperation("xxx接口说明") 作用在接口方法上
 * @ApiModel("xxxPOJO说明") 作用在模型类上：如people实体类
 * @ApiModelProperty(value = "xxx属性说明",hidden = true)	作用在类方法和属性上，hidden设置为true可以隐藏该属性
 * @ApiParam("xxx参数说明") 作用在参数、方法和字段上，类似@ApiModelProperty
 */
@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
@EnableSwaggerBootstrapUI  // SwaggerBootstrapUI 增强
public class SwaggerConfig {
    //配置docket以配置Swagger具体参数
    // Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swaggger。
    @Bean
    public Docket docket(Environment environment) {
        // 设置要显示swagger的环境
        Profiles of = Profiles.of("dev", "test");
        // 判断当前是否处于该环境
        // 通过 enable() 接收此参数判断是否要显示
        boolean b = environment.acceptsProfiles(of);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(b) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                //.groupName("jdbc") // 配置分组,配置多个分组只需要配置多个docket即可
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.base"))
                // 配置如何通过path过滤,即这里只扫描请求以/kuang开头的接口
                //.paths(PathSelectors.ant("/kuang/**"))
                //.any() // 任何请求都扫描
                //.none() // 任何请求都不扫描
                //.regex(final String pathRegex) // 通过正则表达式控制
                //.ant(final String antPattern) // 通过ant()控制
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "Swagger学习", // 标题
                "学习演示如何配置Swagger", // 描述
                "v1.0", // 版本
                "http://terms.service.url/组织链接", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }
}
