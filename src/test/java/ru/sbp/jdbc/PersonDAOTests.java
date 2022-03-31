package ru.sbp.jdbc;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sbp.compare.Person;
import ru.sbp.compare.PersonExceptions;

/**
 * Класс PersonDAOTests выполняет Модульное тестирование JUnit класса PersonDAO
 *
 * @author Sergey Proshchaev
 * @version 1.0
 * @see PersonDAO
 */
class PersonDAOTests {

    /**
     * Метод getPathDBProperties() эмулирует получение из db.properties
     * пути для генерации url подключения к базе данных
     */
    private static String getPathDBProperties() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac OS")) {
            return "Documents/Java/GIT/lecture4_webapp/db/lecture4.db";
        } else {
            return "Documents/Java/GIT/lecture4_webapp/db/lecture4.db";
        }
    }

    /**
     * Метод addToTablePersonData() в таблицу "Test_name" добавляет экземпляр класса Person
     */
    private static void addToTablePersonData() throws PersonExceptions {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Person person1 = new Person("Test_name", "Test_city", 99);
        personDAO.addToTablePerson(person1);
    }

    /**
     * Метод deleteFromTablePerson() удаляет таблицу "Test_name"
     */
    private static void deleteFromTablePerson() throws PersonExceptions {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Person person2 = new Person("Test_name", "Test_city2", 99);
        personDAO.deleteFromTablePerson(person2);
    }

    /**
     * Метод createTablePerson() создает таблицу "Person_test"
     */
    private static void createTablePerson() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        personDAO.createTable("Person_test", "name", "varchar(32)",
                "city", "varchar(32)",
                "age", "integer");
    }

    /**
     * Метод createTable_FailCrNewTable_Test() выполняет Модульное тестирование JUnit
     * метода createTable() класса PersonDAO
     * Неудачное создание таблицы Person_test, таблица Person_test уже существует
     */
    @Test
    void createTable_FailCrNewTable_Test() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        createTablePerson();
        Assertions.assertFalse(personDAO.createTable("Person_test", "name", "varchar(32)",
                "city", "varchar(32)",
                "age", "integer"));
        personDAO.dropTable("Person_test");
    }

    /**
     * Метод dropTable_FailDropTable_Test выполняет Модульное тестирование JUnit
     * метода dropTable класса PersonDAO
     * Метод пытается удалить таблицу с именем TableNotFound, которая не существует
     */
    @Test
    void dropTable_FailDropTable_Test() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Assertions.assertFalse(personDAO.dropTable("TableNotFound"));
    }

    /**
     * Метод addToTablePerson_FailIllegalArgumentException_Test() выполняет Модульное тестирование JUnit
     * метода addToTablePerson класса PersonDAO
     * Метод пытается передать null в addToTablePerson в качестве аргумента
     * и вызывает исключение IllegalArgumentException
     */
    @Test
    void addToTablePerson_FailIllegalArgumentException_Test() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Assertions.assertThrows(IllegalArgumentException.class, () -> personDAO.addToTablePerson(null));
    }

    /**
     * Метод deleteFromTablePerson_FailIllegalArgumentException_Test() выполняет Модульное тестирование JUnit
     * метода deleteFromTablePerson класса PersonDAO
     * Метод пытается передать null в deleteFromTablePerson в качестве аргумента
     * и вызывает исключение IllegalArgumentException
     */
    @Test
    void deleteFromTablePerson_FailIllegalArgumentException_Test() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Assertions.assertThrows(IllegalArgumentException.class, () -> personDAO.deleteFromTablePerson(null));
    }

    /**
     * Метод deleteFromTablePerson_FailPersonNotFound_Test() выполняет Модульное тестирование JUnit
     * метода deleteFromTablePerson класса PersonDAO
     * Метод пытается удалить запись из таблицы Person, которой нет в таблице
     */
    @Test
    void deleteFromTablePerson_FailPersonNotFound_Test() throws PersonExceptions {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Person person1 = new Person("Test_name", "Test_city", 99);
        Assertions.assertFalse(personDAO.deleteFromTablePerson(person1));
    }

    /**
     * Метод updateTablePerson_FailPersonNotFound_Test() выполняет Модульное тестирование JUnit
     * метода updateTablePerson класса PersonDAO
     * Метод успешно удаляет запись из таблицы Person
     */
    @Test
    void updateTablePerson_FailPersonNotFound_Test() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Assertions.assertFalse(personDAO.updateTablePerson("PersonNotFound", 99, "Test_city2"));
    }

    /**
     * Метод updateTablePerson_FailIllegalArgumentException_Test() выполняет Модульное тестирование JUnit
     * метода deleteFromTablePerson класса PersonDAO
     * Метод пытается передать null в updateTablePerson в качестве аргументов name и age
     * и вызывает исключение IllegalArgumentException
     */
    @Test
    void updateTablePerson_FailIllegalArgumentException_Test() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        Assertions.assertThrows(IllegalArgumentException.class, () -> personDAO.updateTablePerson(null, 99, "Test_city2"));
    }

    /**
     * Метод selectFromTablePerson_PersonNotFound_Test() выполняет Модульное тестирование JUnit
     * метода selectFromTablePerson класса PersonDAO
     * Метод передает в качестве аргументов name, age, city
     * и получает коллекцию из элементов класса Person, соответствующих заданным параметрам.
     * В методе selectFromTablePerson_SuccessPersonNotFound_Test формируется запрос на получения коллекции
     * с данными пользователя класса Person, которого нет в таблице Person, в результате метод
     * возвращает пустую коллекцию
     */
    @Test
    void selectFromTablePerson_SuccessPersonNotFound_Test() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setJdbcUrl(getPathDBProperties());
        String resultExpected = "[]";
        Assert.assertEquals(personDAO.selectFromTablePerson("PersonNotFound", 99, "Test_city").toString(), resultExpected);
    }

}