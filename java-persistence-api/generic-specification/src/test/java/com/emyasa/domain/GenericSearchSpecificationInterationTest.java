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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Main.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@TestPropertySource(locations = "classpath:application-test.properties")
@DatabaseSetup("classpath:GenericSearchData.xml")
public class GenericSearchSpecificationInterationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void searchSpringAsKeyword_thenReturn2Books() {
        List<String> bookColumns = Arrays.asList("title", "genre", "author.name");

        GenericSearchSpecification<Book> searchSpecification = new GenericSearchSpecification<>(bookColumns, "Spring");
        List<Book> books = bookRepository.findAll(searchSpecification);
        Assert.assertEquals(2, books.size());
    }

}
