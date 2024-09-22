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
        basePackages = "com.example.demo.repository.campaign",
        entityManagerFactoryRef = "campaignEntityManagerFactory",
        transactionManagerRef = "campaignTransactionManager"
)
public class CampaignDataSourceConfig {

    @Value("${spring.datasource.campaign.url}")
    private String url;

    @Value("${spring.datasource.campaign.username}")
    private String userName;

    @Value("${spring.datasource.campaign.password}")
    private String password;

    @Value("${spring.datasource.campaign.driver}")
    private String driver;

    @Bean
    @Primary
    public DataSource campaignDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean campaignEntityManagerFactory(
            @Qualifier("campaignDataSource") DataSource campaignDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(campaignDataSource);
        em.setPackagesToScan("com.example.demo.entities.campaign");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    @Primary
    public JpaTransactionManager campaignTransactionManager(
            @Qualifier("campaignEntityManagerFactory") LocalContainerEntityManagerFactoryBean campaignEntityManagerFactory) {
        return new JpaTransactionManager(campaignEntityManagerFactory.getObject());
    }

}