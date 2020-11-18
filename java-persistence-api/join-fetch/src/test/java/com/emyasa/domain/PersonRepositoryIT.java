package com.emyasa.domain;

import com.emyasa.Main;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Main.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@TestPropertySource(locations = "classpath:application-test.properties")
@DatabaseSetup("classpath:PersonDataSet.xml")
public class PersonRepositoryIT {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @Transactional
    public void withJoinFetch() {
        List<Person> personList = personRepository.retrieveAll();
        for (Person person : personList) {
            person.getAddresses().forEach(address -> System.out.println(address.getAddressLine()));
        }

        Assert.assertEquals(50, personList.size());
    }

    @Test
    @Transactional
    public void defaultFindAll() {
        List<Person> personList = personRepository.findAll();
        for (Person person : personList) {
            person.getAddresses().forEach(address -> System.out.println(address.getAddressLine()));
        }

        Assert.assertEquals(50, personList.size());
    }

}
