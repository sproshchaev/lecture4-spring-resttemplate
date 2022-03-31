package ru.sbp.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.sbp.jdbc.PersonDAO;

/**
 * Класс ServerConfig содержит конфигурацию Spring через Java
 *
 * @version 1.0
 * @autor Sergey Proshchaev
 */
@Configuration
@ComponentScan("ru.sbp.controllers")
@PropertySource("db.properties")
public class ServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    @Bean
    public PersonDAO personDAO() {
        logger.info("run @Bean personDAO()");
        return new PersonDAO();
    }
}
