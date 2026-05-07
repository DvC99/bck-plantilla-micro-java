package co.com.empresa.infrastructure.config.jpa.command;


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
 * JPA configuration class for command operations.
 * <p>
 * Provides the necessary configuration for database connection,
 * entity management, and integration with Hibernate specifically for write operations.
 */
@Configuration


@EnableJpaRepositories(

        basePackages = {

                "co.com.empresa.infrastructure.adapters.output.repositories.command",

                "co.com.empresa.infrastructure.event"

        },

        entityManagerFactoryRef = "commandEntityManagerFactory",

        transactionManagerRef = "commandTransactionManager"

)

public class CommandJpaConfig extends AbstractJpaConfig {


        /**
     * Configures and returns a {@link LocalContainerEntityManagerFactoryBean} for command operations.
     *
     * @param dataSource    the data source to be used
     * @param hbm2ddlAuto   the hibernate hbm2ddl auto strategy
     * @param defaultSchema the default database schema
     * @param showSql       whether to show SQL in the logs
     * @param formatSql     whether to format SQL in the logs
     * @return a configured {@link LocalContainerEntityManagerFactoryBean}
     */
    @Bean(name = "commandEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean commandEntityManagerFactory(


            @Qualifier("commandDataSource") DataSource dataSource,

            @Value("${spring.hibernate.hbm2ddl.auto:update}") String hbm2ddlAuto,

            @Value("${spring.jpa.properties.hibernate.default_schema:public}") String defaultSchema,

            @Value("${spring.jpa.show-sql:false}") boolean showSql,

            @Value("${spring.jpa.properties.hibernate.format_sql:false}") boolean formatSql) {

        return createEntityManagerFactory(dataSource, hbm2ddlAuto, defaultSchema, showSql, formatSql);

    }


        /**
     * Configures and returns a {@link DataSource} instance using HikariCP for command operations.
     *
     * @param secret             the database secret containing credentials and URL
     * @param driverClass        the JDBC driver class name
     * @param poolName           the name of the Hikari connection pool
     * @param maximumPoolSize    the maximum number of connections in the pool
     * @param minimumIdle        the minimum number of idle connections in the pool
     * @param connectionTimeout  the maximum time to wait for a connection from the pool
     * @param idleTimeout        the maximum time a connection can sit idle in the pool
     * @return a configured {@link DataSource} instance
     */
    @Bean(name = "commandDataSource")
    public DataSource commandDataSource(


            @Qualifier("commandDBSecret") DBSecretDto secret,

            @Value("${spring.datasource.command.driverClassName:org.postgresql.Driver}") String driverClass,

            @Value("${spring.datasource.command.hikari.poolName:HikariPool-Command}") String poolName,

            @Value("${spring.datasource.command.hikari.maximum-pool-size:10}") int maximumPoolSize,

            @Value("${spring.datasource.command.hikari.minimum-idle:5}") int minimumIdle,

            @Value("${spring.datasource.command.hikari.connectionTimeout:30000}") long connectionTimeout,

            @Value("${spring.datasource.command.hikari.idleTimeout:600000}") long idleTimeout) {

        return createDataSource(secret, driverClass, poolName, maximumPoolSize, minimumIdle, connectionTimeout, idleTimeout);

    }


        /**
     * Configures and returns a {@link PlatformTransactionManager} for command operations.
     *
     * @param entityManagerFactory the entity manager factory to be used
     * @return a configured {@link PlatformTransactionManager} instance
     */
    @Bean(name = "commandTransactionManager")
    public PlatformTransactionManager commandTransactionManager(


            @Qualifier("commandEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return createTransactionManager(entityManagerFactory);

    }

}
