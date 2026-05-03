package co.com.empresa.infrastructure.config.jpa;


import co.com.empresa.infrastructure.constants.EntitiesConstants;

import com.zaxxer.hikari.HikariConfig;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.JpaTransactionManager;

import org.springframework.orm.jpa.JpaVendorAdapter;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;

import java.util.Properties;


/**
 * Abstract base class for JPA configurations.
 * <p>
 * Provides common helper methods to create {@link LocalContainerEntityManagerFactoryBean},
 * {@link DataSource} (using HikariCP), and {@link PlatformTransactionManager} instances
 * to avoid duplication in command and query JPA configurations.
 */
public abstract class AbstractJpaConfig {


    /**
     * Creates and configures a {@link LocalContainerEntityManagerFactoryBean} instance.
     *
     * @param dataSource    the data source to be used
     * @param hbm2ddlAuto   the database schema generation strategy
     * @param defaultSchema the default schema to be used
     * @param showSql       whether to show generated SQL statements
     * @param formatSql     whether the SQL statements should be formatted
     * @return a configured {@link LocalContainerEntityManagerFactoryBean} instance
     */
    protected LocalContainerEntityManagerFactoryBean createEntityManagerFactory(


            DataSource dataSource,

            String hbm2ddlAuto,

            String defaultSchema,

            boolean showSql,

            boolean formatSql) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource);

        em.setPackagesToScan(EntitiesConstants.PACKAGE_ENTITIES);


        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);


        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);

        properties.setProperty("hibernate.default_schema", defaultSchema);

        properties.setProperty("hibernate.show_sql", String.valueOf(showSql));

        properties.setProperty("hibernate.format_sql", String.valueOf(formatSql));

        em.setJpaProperties(properties);


        return em;

    }


    /**
     * Creates and configures a {@link DataSource} instance using HikariCP.
     *
     * @param secret             the database secret containing credentials and URL
     * @param driverClass        the JDBC driver class name
     * @param poolName           the name of the connection pool
     * @param maximumPoolSize    the maximum size of the connection pool
     * @param minimumIdle        the minimum number of idle connections in the pool
     * @param connectionTimeout  the maximum wait time (ms) to obtain a connection from the pool
     * @param idleTimeout        the maximum time (ms) a connection can remain idle in the pool
     * @return a configured {@link DataSource} instance
     */
    protected DataSource createDataSource(


            DBSecretDto secret,

            String driverClass,

            String poolName,

            int maximumPoolSize,

            int minimumIdle,

            long connectionTimeout,

            long idleTimeout) {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(secret.getUrl());

        config.setUsername(secret.getUsername());

        config.setPassword(secret.getPassword());

        config.setDriverClassName(driverClass);

        config.setPoolName(poolName);

        config.setMaximumPoolSize(maximumPoolSize);

        config.setMinimumIdle(minimumIdle);

        config.setConnectionTimeout(connectionTimeout);

        config.setIdleTimeout(idleTimeout);

        return new HikariDataSource(config);

    }


    /**
     * Creates and configures a {@link PlatformTransactionManager} instance.
     *
     * @param entityManagerFactory the entity manager factory to be used
     * @return a configured {@link PlatformTransactionManager} instance
     */
    protected PlatformTransactionManager createTransactionManager(


            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);

    }

}























