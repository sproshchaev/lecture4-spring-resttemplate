package ru.sbp.compare;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс PersonTests проверяет класс Person c использованием JUnit
 *
 * @version 2.0
 * @autor Sergey Proshchaev
 */
class PersonTests {

    @Test
    void test_valueReceived_getName() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Assert.assertEquals(person.getName(), "E");
    }

    @Test
    void test_valueReceived_getCity() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Assert.assertEquals(person.getCity(), "City1");
    }

    @Test
    void test_valueReceived_getAge() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Assert.assertEquals(person.getAge(), 11);
    }

    @Test
    void test_valueReceived_toString() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Assert.assertEquals(person.toString(), "Person {name=E, city=City1, age=11}");
    }

    @Test
    void test_null_Equals() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Assert.assertFalse(person.equals(null));
    }

    @Test
    void test_true_Equals() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Assert.assertTrue(person.equals(person));
    }

    @Test
    void test_true2_Equals() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Person personClone = new Person("E", "City1", 11);
        Assert.assertTrue(person.equals(personClone));
    }

    @Test
    void test_areEqual_HashCode() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Person personClone = new Person("E", "City1", 11);
        Assert.assertTrue(person.hashCode() == personClone.hashCode());
    }

    @Test
    void test_notEqual_HashCode() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Person person2 = new Person("E2", "City2", 12);
        Assert.assertFalse(person.hashCode() == person2.hashCode());
    }

    @Test
    void test_valueForObject_HashCode() throws PersonExceptions {
        Person person = new Person("E", "City1", 11);
        Assert.assertEquals(person.hashCode(), 2018792489);
    }

    /**
     * Метод test_sorted_compareTo
     * Элементы коллекции до сортировки:
     * listOfPerson before: [Person {name=E, city=City1, age=11}, Person {name=F, city=City1, age=21}, Person {name=D, city=City1, age=31}, Person {name=C, city=City2, age=12}, Person {name=B, city=City2, age=23}, Person {name=A, city=City2, age=32}, Person {name=A, city=City3, age=32}]
     * Элементы коллекции после сортировки:
     * listOfPerson after: [Person {name=D, city=City1, age=31}, Person {name=E, city=City1, age=11}, Person {name=F, city=City1, age=21}, Person {name=A, city=City2, age=32}, Person {name=B, city=City2, age=23}, Person {name=C, city=City2, age=12}, Person {name=A, city=City3, age=32}]
     */
    @Test
    void test_sorted_compareTo() throws PersonExceptions {
        Person person1 = new Person("E", "City1", 11);
        Person person2 = new Person("F", "City1", 21);
        Person person3 = new Person("D", "City1", 31);
        Person person4 = new Person("C", "City2", 12);
        Person person5 = new Person("B", "City2", 23);
        Person person6 = new Person("A", "City2", 32);
        Person person7 = new Person("A", "City3", 32);

        List<Person> listOfPerson = new ArrayList<>();
        listOfPerson.add(person1);
        listOfPerson.add(person2);
        listOfPerson.add(person3);
        listOfPerson.add(person4);
        listOfPerson.add(person5);
        listOfPerson.add(person6);
        listOfPerson.add(person7);

        listOfPerson.sort(Person::compareTo);

        Assert.assertEquals(listOfPerson.get(0).getName(), "D");
        Assert.assertEquals(listOfPerson.get(2).getCity(), "City1");
        Assert.assertEquals(listOfPerson.get(6).getAge(), 32);

    }

    @Test
    void test_nameNull_Person() {
        String cityNotNull = "City";
        int ageNotZero = 20;
        Assertions.assertThrows(PersonExceptions.class, () -> new Person(null, cityNotNull, ageNotZero));
    }

    @Test
    void test_cityNull_Person() {
        String nameNotNull = "Name";
        int ageNotZero = 20;
        Assertions.assertThrows(PersonExceptions.class, () -> new Person(nameNotNull, null, ageNotZero));
    }

    @Test
    void test_ageZero_Person() {
        String cityNotNull = "City";
        String nameNotNull = "Name";
        int ageNotZero = 20;
        Assertions.assertThrows(PersonExceptions.class, () -> new Person(nameNotNull, cityNotNull, 0));
    }

}