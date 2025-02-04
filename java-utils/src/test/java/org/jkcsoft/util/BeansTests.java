package org.jkcsoft.util;

import org.jkcsoft.java.testing.BaseTestCase;
import org.jkcsoft.java.util.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

/**
 * @author Jim Coles
 */
public class BeansTests extends BaseTestCase {
    
    public static final Logger log = LoggerFactory.getLogger(BeansTests.class);
    
    public void testBeanSelect() {
        final Set<Person> persons = Set.of(new Person("jim", 59), new Person("waldo", 5), new Person("morena", 45));
        final Collection<Person> jim = Beans.select(persons, person -> person.getName().equals("jim"));
        log.info("selected bean using select(filter): [{}]", jim.toString());
        assertEquals(1, jim.size());
        assertEquals("jim", jim.iterator().next().getName());
    }
    
    public class Person {
        private String name;
        private int age;
    
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        @Override
        public String toString() {
            return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
        }
    }
}
