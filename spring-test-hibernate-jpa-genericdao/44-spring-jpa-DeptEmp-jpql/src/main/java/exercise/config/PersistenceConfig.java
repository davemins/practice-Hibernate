package exercise.config;

import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;

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
@PropertySource({ "classpath:persistence.properties" })
@ComponentScan({ "exercise.repository", "exercise.service" })
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "exercise.repository")
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        var hikariConfig = new HikariConfig("/hikari.properties");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(new String[] { "exercise.model" });
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(additionalProperties());

        return entityManagerFactory;
    }

    final Properties additionalProperties() {
        var hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty("hibernate.hbm2ddl.import_files_sql_extractor",
                "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
        hibernateProperties.setProperty("hibernate.id.optimizer.pooled.prefer_lo", "true");
        //hibernateProperties.setProperty("hibernate.show_sql", "true");
        //hibernateProperties.setProperty("hibernate.foramt_sql", "true");

        return hibernateProperties;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

}

