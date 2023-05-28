package io.whatap.test.config;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.ConnectionPoolConfiguration;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
@EnableR2dbcRepositories
public class R2dbcJAsyncConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        String host = "localhost";
        int port = 3306;
        String database = "test";
        String username = "root";
        String password = "1111";
        int poolSize = 20;
        return new JasyncConnectionFactory(
                new MySQLConnectionFactory(new ConnectionPoolConfiguration(host, port, database, username, password, poolSize).getConnectionConfiguration()));
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}
