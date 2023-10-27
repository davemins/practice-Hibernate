package com.oreilly.hh;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource({ "classpath:jdbc.properties" })
@ComponentScan({ "com.oreilly.hh" })
@EnableTransactionManagement
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        var hikariConfig = new HikariConfig("/hikari.properties");
        return new HikariDataSource(hikariConfig);
    }

    // ----------------------------------------------
    // Hibernate
    // ----------------------------------------------

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        var sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.oreilly.hh.data" });
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
                setProperty("hibernate.hbm2ddl.auto", "update");
                // setProperty("hibernate.show_sql", "true");
                // setProperty("hibernate.format_sql", "true");
            }
        };
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        var transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

}

