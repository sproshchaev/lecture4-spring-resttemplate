package ru.sbp.compare;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Person
 *
 * @version 1.0
 * @autor Sergey Proshchaev
 * <p>
 * Реализовать класс Person{name, city, age}, определить метод toString.
 * Класс Person реализует интерфейс Comparable<Person>, который обеспечивает
 * следующий порядок: - Сортировка сначала по полю city, а затем по полю name;
 * - Поля name, city отличны от null;
 */
public class Person implements Comparable<Person>, Serializable {

    private String name;
    private String city;
    private int age;

    /**
     * Конструктор класса Person
     * выполняет проверку name not null, city not null, age not 0
     *
     * @throw PersonExceptions if name null or city null or age not 0
     */
    public Person(String name, String city, int age) throws PersonExceptions {
        if ((name == null) || (city == null) || (age == 0)) {
            throw new PersonExceptions("Name and City can not be null. Age can not be 0!");
        }
        this.name = name;
        this.city = city;
        this.age = age;
    }

    /**
     * Getter-методы класса Person
     * getName(), getCity(), getAge
     */
    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    /**
     * Класс Person реализует интерфейс Comparable<Person>, который обеспечивает
     * следующий порядок: - Сортировка сначала по полю city, а затем по полю name;
     * Метод compareTo возвращает -1 (объект имеет меньший приоритет), 0 (объекты совпадают),
     * 1 (объект имеет больший приоритет)
     */
    @Override
    public int compareTo(Person person) {

        String stringCity1 = this.city;
        String stringCity2 = person.city;
        int compareStringCity = stringCity1.compareTo(stringCity2);

        if (compareStringCity == 0) {
            String stringName1 = this.name;
            String stringName2 = person.name;
            int compareStringName = stringName1.compareTo(stringName2);
            return compareStringName;
        } else {
            return compareStringCity;
        }
    }


    @Override
    public String toString() {
        return "Person {name=" + name + ", city=" + city + ", age=" + age + "}";
    }

    /**
     * Переопределение метода equals() класса Person
     *
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Person objPerson = (Person) obj;
        return this.name == objPerson.name && this.city == objPerson.city && this.age == objPerson.age;
    }

    /**
     * Переопределение метода hashCode() класса Person
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.city, this.age);
    }

}
