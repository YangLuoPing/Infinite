package com.base.config.dataway;

import net.hasor.core.ApiBinder;
import net.hasor.core.DimModule;
import net.hasor.db.JdbcModule;
import net.hasor.db.Level;
import net.hasor.spring.SpringModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * dataway对标  GraphQL
 * 把数据源设置到 Hasor 容器中
 * 文档地址
 * https://www.hasor.net/web/container/event/about_event.html#id4
 * 操作界面访问地址  http://localhost:8080/dataway-ui/#/
 */
@DimModule
@Component
public class HasorConfig implements SpringModule {
    @Autowired
    DataSource dataSource = null;

    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        // .DataSource form Spring boot into Hasor
        // .初始化Hasor Jdbc 模块，并配置数据源
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
        // .custom DataQL
        //apiBinder.tryCast(QueryApiBinder.class).loadUdfSource(apiBinder.findClass(DimUdfSource.class));
        //apiBinder.tryCast(QueryApiBinder.class).bindFragment("sql", SqlFragment.class);
    }
}
