package com.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 自定义视图解析器
 */
@Configuration
//@EnableWebMvc
public class MyMvcConfig implements WebMvcConfigurer {
    /**
     * 自定义freemarker的解析器
     *
     * @return
     */
  /*  @Bean
    public FreeMarkerViewResolver getFreeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setRequestContextAttribute("rc");
        return resolver;
    }
*/

    /**
     * 内容裁决视图解析器
     * 该解析器不进行具体视图的解析，而是管理你注册的所有视图解析器，所有的视图会先经过它进行解析，然后由它来决定具体使用哪个解析器进行解析
     *
     * @param configurer
     */
   /* @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        *//* 是否通过请求Url的扩展名来决定media type *//*
        configurer.favorPathExtension(true)
                *//* 不检查Accept请求头 *//*
                .ignoreAcceptHeader(true)
                .parameterName("mediaType")
                *//* 设置默认的media yype *//*
                .defaultContentType(MediaType.TEXT_HTML)
                *//* 请求以.html结尾的会被当成MediaType.TEXT_HTML*//*
                .mediaType("html", MediaType.TEXT_HTML)
                *//* 请求以.json结尾的会被当成MediaType.APPLICATION_JSON*//*
                .mediaType("json", MediaType.APPLICATION_JSON);
    }*/

    /**
     * 配置视图解析器
     * 配置ksp解析器
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/static/jsp/", ".jsp");
        registry.enableContentNegotiation(new MappingJackson2JsonView());
    }

    /**
     * 静态资源处理
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("/WEB-INF/static/");
    }


    /* @Bean //放到bean中
    public ViewResolver myViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".html");
        return resolver;
    }*/

    /**
     * 默认静态资源处理器
     *
     * @param configurer
     */
   /* @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // 用来处理default servlet  静态资源的放行问题
        // 不加该方法就无法静态资源放行
        configurer.enable();
    }*/

    /**
     * 视图跳转控制器
     * 重写addViewControllers方法，并不会覆盖WebMvcAutoConfiguration（Springboot自动配置）
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器发送/test ， 就会跳转到test页面；
        registry.addViewController("/").setViewName("views/index.html");
        //registry.addViewController("/test").setViewName("test");
    }

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHanderConfig()).addPathPatterns("/**") //拦截
                .excludePathPatterns("/index.html", "/", "login.html", "/layuiadmin/**", "/interface-ui/**", "/view/**", "/img/**");//排除
    }*/
}

