package ru.sbp.compare;

/**
 * Класс PersonExceptions содержит обработку исключений класса Person
 *
 * @version 1.0
 * @autor Sergey Proshchaev
 */
public class PersonExceptions extends Exception {

    public PersonExceptions(String message) {
        super("PersonExceptions: " + message);
    }
}
