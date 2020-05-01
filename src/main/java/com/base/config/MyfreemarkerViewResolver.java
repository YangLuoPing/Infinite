package com.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Locale;

public class MyfreemarkerViewResolver extends FreeMarkerViewResolver {

    @Value("${spring.freemarker.template-loader-path}")
    private String prefix;
    @Value("${spring.freemarker.suffix}")
    private String suffix;

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        String resourceName= prefix +viewName+suffix;
        try {
            this.getApplicationContext().getResource(resourceName).getInputStream();
        }catch (final IOException e){
            if(logger.isDebugEnabled()){
                logger.trace("视图名："+resourceName+"{}不存在！");
            }else {
                logger.debug("视图名："+resourceName+"{}不存在！");
            }
        }
        return super.loadView(viewName, locale);
    }
}
