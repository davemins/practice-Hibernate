package com.oreilly.hh;

import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
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
    // JPA
    // ----------------------------------------------

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan(new String[] { "com.oreilly.hh" });
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(hibernateProperties());

        return emf;
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
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}

