
package com.example.WeePetClinic.Components.dataConfig;

import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "clinicEntityManagerFactory",
        transactionManagerRef = "clinicTransactionManager",
        basePackages = {
                "com.example.WeePetClinic.Components.Repository"
        }
)


public class sourceConfig {
  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.sql")
  public DataSourceProperties clinicDataSourceProperties() {
    return new DataSourceProperties();
  }


  @Primary
  @Bean(name = "clinicDataSource")
  public HikariDataSource clinicDataSource() {
    return clinicDataSourceProperties().initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
  }


  @Primary
  @Bean(name = "clinicEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean clinicEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                         @Qualifier("clinicDataSource") DataSource dataSource) {

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "none");
    properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");

    return builder.dataSource(dataSource)
            .properties(properties)
            .packages("com.example.WeePetClinic.Components.Model")
            .persistenceUnit("clinic")
            .build();
  }


  @Primary
  @Bean(name = "clinicTransactionManager")
  public PlatformTransactionManager clinicTransactionManager(@Qualifier("clinicEntityManagerFactory") EntityManagerFactory clinicEntityManagerFactory) {
    return new JpaTransactionManager(clinicEntityManagerFactory);
  }
}
