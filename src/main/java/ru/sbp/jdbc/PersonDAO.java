package ru.sbp.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteException;
import ru.sbp.compare.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Класс PersonDAO содержит методы работы с базой данных SQLite
 * без использования класса DBService
 *
 * @version 1.0
 * <p>
 * Домашнее задание к занятию № 11, 12 (ДЗ №6)
 * Создать таблицу для хранения данных об Person (из ДЗ 7,8)
 * и реализовать основные CRUD* операции для этой таблицы с использованием JDBC;
 * <p>
 * *CRUD - создание (create), чтение (read), модификация (update), удаление (delete)
 * @autor Sergey Proshchaev
 */
public class PersonDAO {

    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    private String osName;

    private String jdbcUrl;

    /**
     * Конструктор PersonDAO
     * Инициализация переменных класса PersonDAO для JDBC перенесена
     * в setter setJdbcUrl
     */
    public PersonDAO() {
    }

    /**
     * Setter-метод setJdbcUrl инициализирует переменные класса для JDBC
     * в зависимости от версии операционной системы
     * Значение свойств подключения в переменной pathDBProperties
     * файла src/main/resources/db.properties
     *
     * @param - String путь к файлу базы данных от каталога Documents
     */
    public void setJdbcUrl(String pathDBProperties) {

        this.osName = System.getProperty("os.name");

        if (this.osName.contains("Mac OS")) {
            this.jdbcUrl = "jdbc:sqlite:/Users/sproshchaev/" + pathDBProperties;
        } else {
            this.jdbcUrl = "jdbc:sqlite:/Users/Сергей/" + pathDBProperties;
        }

        logger.info("os.name: " + this.osName + ", jdbcUrl: " + this.jdbcUrl);
    }

    /**
     * Метод checkPersonIsNotNull проверяет аргумент person на null
     * и вызывает исключение IllegalArgumentException, если person is null
     *
     * @param - Person person
     * @throws IllegalArgumentException - если person == null
     */
    private static void checkPersonIsNotNull(Person person) {
        if (person == null) {
            logger.error("Argument person is null ");
            throw new IllegalArgumentException("Argument person is null");
        }
    }

    /**
     * Метод checkStringParamIsNotNull проверяет аргумент типа String
     * и вызывает исключение IllegalArgumentException, если аргумент is null
     *
     * @param - String
     * @throws IllegalArgumentException - если string == null
     */
    private static void checkStringParamIsNotNull(String string) {
        if (string == null) {
            logger.error("Argument is null ");
            throw new IllegalArgumentException("Argument is null");
        }
    }

    /**
     * Метод checkIntParamIsNotNull проверяет аргумент типа int
     * и вызывает исключение IllegalArgumentException, если аргумент == 0
     *
     * @param - int intArg
     * @throws IllegalArgumentException - если intArg == 0
     */
    private static void checkIntParamIsNotNull(int intArg) {
        if (intArg == 0) {
            logger.error("Argument is null ");
            throw new IllegalArgumentException("Argument is null");
        }
    }

    /**
     * Метод createTable создает новую таблицу в базе данных Lecture4.db
     * с заданным именем и тремя полями
     *
     * @param tableName  - имя таблицы
     * @param fieldName1 - имя поля 1
     * @param fieldType1 - тип поля 1
     * @param fieldName2 - имя поля 2
     * @param fieldType2 - тип поля 2
     * @param fieldName3 - имя поля 3
     * @param fieldType3 - тип поля 3
     * @return - true, если таблица была создана
     */
    public boolean createTable(String tableName, String fieldName1, String fieldType1,
                               String fieldName2, String fieldType2,
                               String fieldName3, String fieldType3) {

        logger.info("Start createTable");

        boolean resultCreateTable = false;

        final String sqlString = "create table " + tableName + " (" +
                fieldName1 + " " + fieldType1 + ", " +
                fieldName2 + " " + fieldType2 + ", " +
                fieldName3 + " " + fieldType3 + ");";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sqlString);

            resultCreateTable = true;

        } catch (SQLiteException ex) {
            ex.printStackTrace();
            logger.info("SQLiteException " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Exception " + ex);
        }

        logger.info("End createTable return: " + resultCreateTable);

        return resultCreateTable;
    }

    /**
     * Метод dropTable удаляет таблицу в базе данных с заданным именем
     *
     * @param tableName - имя таблицы
     * @return - true, если таблица была удалена
     */
    public boolean dropTable(String tableName) {

        logger.info("Start dropTable");

        boolean resultDropTable = false;

        final String sqlString = "drop table " + tableName + ";";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sqlString);

            resultDropTable = true;

        } catch (SQLiteException ex) {
            ex.printStackTrace();
            logger.info("SQLiteException " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Exception " + ex);
        }

        logger.info("End dropTable return: " + resultDropTable);

        return resultDropTable;
    }

    /**
     * Метод addToTablePerson добавляет нового пользователя в таблицу Person
     * В методе используется try-with-resources, параметризированный SQL-запроса в PreparedStatement
     *
     * @param - экземпляр класса Person
     * @return - true если запись добавлена, false если возникла ошибка при добавлении
     */
    public boolean addToTablePerson(Person person) {

        logger.info("Start addToTablePerson");

        boolean resultAddToTablePerson = false;

        int resultExecuteUpdate = 0;

        checkPersonIsNotNull(person);

        final String createPersonQuery = "insert into Person(name, city, age) values(?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(createPersonQuery)) {

            statement.setString(1, person.getName());
            statement.setString(2, person.getCity());
            statement.setInt(3, person.getAge());
            resultExecuteUpdate = statement.executeUpdate();

            resultAddToTablePerson = true;

        } catch (SQLiteException ex) {
            ex.printStackTrace();
            logger.info("SQLiteException " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Exception " + ex);
        }

        logger.info("End addToTablePerson return: " + resultAddToTablePerson + " Inserted: " + resultExecuteUpdate);

        return resultAddToTablePerson;
    }

    /**
     * Метод deleteFromTablePerson удаляет пользователя из таблицы Person
     * В методе используется try-with-resources, параметризированный SQL-запроса в PreparedStatement
     *
     * @param - экземпляр класса Person
     * @return - true если запись удалена, false если не была удалена
     */
    public boolean deleteFromTablePerson(Person person) {

        logger.info("Start deleteFromTablePerson");

        boolean resultDeleteFromTablePerson = false;

        int resultExecuteUpdate = 0;

        checkPersonIsNotNull(person);

        final String createPersonQuery = "delete from Person where (name = ?) and (city = ?) and (age = ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(createPersonQuery)) {

            statement.setString(1, person.getName());
            statement.setString(2, person.getCity());
            statement.setInt(3, person.getAge());
            resultExecuteUpdate = statement.executeUpdate();

            if (resultExecuteUpdate > 0) resultDeleteFromTablePerson = true;

        } catch (SQLiteException ex) {
            ex.printStackTrace();
            logger.info("SQLiteException " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Exception " + ex);
        }

        logger.info("End deleteFromTablePerson return: " + resultDeleteFromTablePerson + " Deleted: " + resultExecuteUpdate);

        return resultDeleteFromTablePerson;
    }

    /**
     * Метод updateTablePerson изменяет в таблице Person поля City для пользователя с Name и Age
     * В методе используется try-with-resources, параметризированный SQL-запроса в PreparedStatement
     *
     * @param - name, Имя пользователя, для которого будут изменяться поле city
     * @param - age, Возраст пользователя, для которого будут изменяться поле city
     * @param - city, город
     * @return - true если запись изменена, false если не была изменена
     */
    public boolean updateTablePerson(String name, int age, String city) {

        logger.info("Start updateTablePerson");

        boolean resultUpdateTablePerson = false;

        int resultExecuteUpdate = 0;

        checkStringParamIsNotNull(name);

        checkIntParamIsNotNull(age);

        final String createPersonQuery = "update Person set city = ? where (name = ?) and (age = ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(createPersonQuery)) {

            statement.setString(1, city);
            statement.setString(2, name);
            statement.setInt(3, age);

            resultExecuteUpdate = statement.executeUpdate();

            if (resultExecuteUpdate > 0) resultUpdateTablePerson = true;

        } catch (SQLiteException ex) {
            ex.printStackTrace();
            logger.info("SQLiteException " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Exception " + ex);
        }

        logger.info("End updateTablePerson return: " + resultUpdateTablePerson + " Updated: " + resultExecuteUpdate);

        return resultUpdateTablePerson;
    }

    /**
     * Метод selectFromTablePerson использует параметрический запрос и возвращает Коллекцию,
     * содержащую данные из SQL-запроса
     *
     * @param - String name - Имя
     * @param - int    age - Возраст
     * @param - String city - город
     * @return - Collection<Person>
     * @throws IllegalArgumentException - если name == null, age == 0 или city == null
     */
    public Collection<Person> selectFromTablePerson(String name, int age, String city) {

        logger.info("Start selectFromTablePerson name=" + name + ", age=" + age + ", city=" + city + ".");

        checkStringParamIsNotNull(name);

        checkStringParamIsNotNull(city);

        checkIntParamIsNotNull(age);

        final String createPersonQuery = "select * from Person where (name = ?) and (age = ?) and (city = ?)";

        List<Person> personList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(createPersonQuery)) {

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, city);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person localPerson = new Person(resultSet.getString("name"), resultSet.getString("city"), resultSet.getInt("age"));
                personList.add(localPerson);
            }

        } catch (SQLiteException ex) {
            ex.printStackTrace();
            logger.info("SQLiteException " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Exception " + ex);
        }

        logger.info("End selectFromTablePerson return: " + personList.toString());

        return personList;
    }

    /**
     * Метод selectFromTableAllPerson возвращает Коллекцию из всех записей
     *
     * @return - Collection<Person>
     * @throws IllegalArgumentException - если name == null, age == 0 или city == null
     */
    public Collection<Person> selectFromTableAllPerson() {

        logger.info("Start selectFromTableAllPerson");

        final String createPersonQuery = "select * from Person";

        List<Person> personList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(createPersonQuery)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person localPerson = new Person(resultSet.getString("name"), resultSet.getString("city"), resultSet.getInt("age"));
                personList.add(localPerson);
            }

        } catch (SQLiteException ex) {
            ex.printStackTrace();
            logger.info("SQLiteException " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Exception " + ex);
        }

        logger.info("End selectFromTableAllPerson return: " + personList.toString());

        return personList;
    }

}
