package com.base.config;

/**
 * JDbc 多数据源
 */
//@Configuration
public class JdbcDataSourceConfig {


    /*
       创建一个Spring配置类，定义两个DataSource用来读取配置文件中中的不同配置。
       主数据源配置为spring.datasource.primary开头的配置，第二数据源配置为spring.datasource.secondary开头的配置。
       */
   /* @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.dynamic.datasource.master")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.dynamic.datasource.slavetempo")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }*/

    /*
   对JdbcTemplate的支持比较简单，只需要为其注入对应的datasource即可.
   在创建JdbcTemplate的时候分别注入名为primaryDataSource和secondaryDataSource的数据源来区分不同的JdbcTemplate。
   */
  /*  @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }*/

}
