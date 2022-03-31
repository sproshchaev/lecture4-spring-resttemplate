package ru.sbp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbp.compare.Person;
import ru.sbp.jdbc.PersonDAO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Класс ServerController содержит методы, обрабатывающие GET, POST запросы
 *
 * @version 1.0
 * @autor Sergey Proshchaev
 */
@RestController
@RequestMapping("/serverController")
public class ServerController {

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    @Value("${pathDBProperties}")
    private String pathDBProperties;

    /**
     * Метод getProcessing возвращает все записи из таблицы Person
     * url запроса: http://localhost:8080/serverController/get
     *
     * @return - ResponseEntity
     */
    @GetMapping("/get")
    public ResponseEntity<String> getProcessing() {

        logger.info("GET method started");

        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(pathDBProperties);

        Collection<Person> list = new ArrayList<>();
        list = personDAO.selectFromTableAllPerson();

        logger.info("GET method finished");

        return new ResponseEntity<>("All Persons " + list.toString(), HttpStatus.OK);
    }

    /**
     * Метод postProcessing вызывает метод addToTablePerson класса PersonDAO
     * url запроса: http://localhost:8080/serverController/post
     *
     * @return - ResponseEntity
     */
    @PostMapping("/post")
    public ResponseEntity<String> postProcessing(@RequestBody Person person) {

        logger.info("POST method started");

        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(pathDBProperties);
        boolean result = personDAO.addToTablePerson(person);

        logger.info("POST method finished. result=" + result);

        return new ResponseEntity<>("Person added=" + result, HttpStatus.OK);
    }

    /**
     * Метод putProcessing вызывает метод deleteFromTablePerson класса PersonDAO
     * url запроса: http://localhost:8080/serverController/delete
     *
     * @return - ResponseEntity
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProcessing(@RequestBody Person person) {

        logger.info("DELETE method started");

        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(pathDBProperties);
        boolean result = personDAO.deleteFromTablePerson(person);

        logger.info("DELETE method finished. result=" + result);

        return new ResponseEntity<>("Person delete result=" + result, HttpStatus.OK);
    }

}
