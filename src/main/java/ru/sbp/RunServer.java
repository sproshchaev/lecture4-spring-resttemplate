package ru.sbp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbp.config.ServerConfig;

/**
 * Класс RunServer запускает  WEB-сервер со Spring Boot
 *
 * @version 1.0
 * @autor Sergey Proshchaev
 */
@SpringBootApplication
public class RunServer {
    private static final Logger logger = LoggerFactory.getLogger(RunServer.class);

    public static void main(String[] args) {

        logger.info("Server is running...");

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ServerConfig.class);

        SpringApplication.run(RunServer.class, args);

    }
}
