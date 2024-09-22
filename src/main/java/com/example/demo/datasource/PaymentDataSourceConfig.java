package com.example.demo.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.demo.repository.payment",
        entityManagerFactoryRef = "paymentEntityManagerFactory",
        transactionManagerRef = "paymentTransactionManager"
)
public class PaymentDataSourceConfig {

    @Value("${spring.datasource.payment.url}")
    private String url;

    @Value("${spring.datasource.payment.username}")
    private String userName;

    @Value("${spring.datasource.payment.password}")
    private String password;

    @Value("${spring.datasource.payment.driver}")
    private String driver;

    @Bean
    @Primary
    public DataSource paymentDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean paymentEntityManagerFactory(
            @Qualifier("paymentDataSource") DataSource paymentDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(paymentDataSource);
        em.setPackagesToScan("com.example.demo.entities.payment");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    @Primary
    public JpaTransactionManager paymentTransactionManager(
            @Qualifier("paymentEntityManagerFactory") LocalContainerEntityManagerFactoryBean paymentEntityManagerFactory) {
        return new JpaTransactionManager(paymentEntityManagerFactory.getObject());
    }

}