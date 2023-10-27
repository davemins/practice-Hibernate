package exercise.config;

import java.util.Properties;

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
    public LocalSessionFactoryBean sessionFactory() {
        var sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "exercise.model" });
        sessionFactory.setHibernateProperties(additionalProperties());

        return sessionFactory;
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
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        var transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }

}

