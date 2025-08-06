package com.example.WeePetClinic.Components.dataConfig;

import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "donateEntityManagerFactory",
        transactionManagerRef = "donateTransactionManager",
        basePackages = {
                "com.example.WeePetClinic.Components.Repository.forPostSql"
        }
)
public class postSqlSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.postgres")
  public DataSourceProperties donateDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "donateDataSource")
  public HikariDataSource donateDataSource() {
    return donateDataSourceProperties().initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
  }


  @Bean(name = "donateEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean donateEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                     @Qualifier("donateDataSource") DataSource dataSource) {

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

    return builder.dataSource(dataSource)
            .properties(properties)
            .packages("com.example.WeePetClinic.Components.Model.forPostSql")
            .persistenceUnit("donate")
            .build();
  }

  @Bean(name = "donateTransactionManager")
  public PlatformTransactionManager donateTransactionManager(@Qualifier("donateEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
