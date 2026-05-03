package co.com.empresa.infrastructure.config.jpa.queries;


import co.com.empresa.infrastructure.config.jpa.AbstractJpaConfig;

import co.com.empresa.infrastructure.config.jpa.DBSecretDto;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;


/**
 * JPA configuration class for query operations.
 * <p>
 * Provides the necessary configuration for database connection,
 * entity management, and integration with Hibernate specifically for read operations.
 */
@Configuration

@EnableJpaRepositories(

        basePackages = "co.com.empresa.infrastructure.adapters.output.repositories.query",

        entityManagerFactoryRef = "queryEntityManagerFactory",

        transactionManagerRef = "queryTransactionManager"

)

public class QueryJpaConfig extends AbstractJpaConfig {


    /**
     * Configures and returns a {@link LocalContainerEntityManagerFactoryBean} for query operations.
     *
     * @param dataSource    the data source to be used
     * @param hbm2ddlAuto   the database schema generation strategy
     * @param defaultSchema the default schema to be used
     * @param showSql       whether to show generated SQL statements
     * @param formatSql     whether the SQL statements should be formatted
     * @return a configured {@link LocalContainerEntityManagerFactoryBean} instance
     */
    @Bean(name = "queryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean queryEntityManagerFactory(


            @Qualifier("queryDataSource") DataSource dataSource,

            @Value("${spring.hibernate.hbm2ddl.auto:update}") String hbm2ddlAuto,

            @Value("${spring.jpa.properties.hibernate.default_schema:public}") String defaultSchema,

            @Value("${spring.jpa.show-sql:false}") boolean showSql,

            @Value("${spring.jpa.properties.hibernate.format_sql:false}") boolean formatSql) {

        return createEntityManagerFactory(dataSource, hbm2ddlAuto, defaultSchema, showSql, formatSql);

    }


    /**
     * Configures and returns a {@link DataSource} instance using HikariCP for query operations.
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
    @Bean(name = "queryDataSource")
    public DataSource queryDataSource(


            @Qualifier("queryDBSecret") DBSecretDto secret,

            @Value("${spring.datasource.query.driverClassName:org.postgresql.Driver}") String driverClass,

            @Value("${spring.datasource.query.hikari.poolName:HikariPool-Query}") String poolName,

            @Value("${spring.datasource.query.hikari.maximum-pool-size:10}") int maximumPoolSize,

            @Value("${spring.datasource.query.hikari.minimum-idle:5}") int minimumIdle,

            @Value("${spring.datasource.query.hikari.connectionTimeout:30000}") long connectionTimeout,

            @Value("${spring.datasource.query.hikari.idleTimeout:600000}") long idleTimeout) {

        return createDataSource(secret, driverClass, poolName, maximumPoolSize, minimumIdle, connectionTimeout, idleTimeout);

    }


    /**
     * Configures and returns a {@link PlatformTransactionManager} for query operations.
     *
     * @param entityManagerFactory the entity manager factory to be used
     * @return a configured {@link PlatformTransactionManager} instance
     */
    @Bean(name = "queryTransactionManager")
    public PlatformTransactionManager queryTransactionManager(


            @Qualifier("queryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return createTransactionManager(entityManagerFactory);

    }

}

























