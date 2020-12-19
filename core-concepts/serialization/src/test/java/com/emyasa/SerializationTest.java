package com.emyasa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SerializationTest {

    @InjectMocks
    private PersonSerializationUtil personSerializationUtil;

    @Test
    public void serializeAndDeserializePersonList() throws IOException, URISyntaxException {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("firstname1", "lastname1"));
        personList.add(new Person("firstname2", "lastname2"));
        personList.add(new Person("firstname3", "lastname3"));
        personList.add(new Person("firstname4", "lastname4"));

        personSerializationUtil.serializePersonListToFile(personList);
        List<Person> deserializedList = personSerializationUtil.deserializePersonListFromFile();

        Assert.assertEquals(4, deserializedList.size());
        Assert.assertEquals("firstname2", deserializedList.get(1).getFirstName());
    }

    @Test
    public void deserializePersonList() throws IOException, URISyntaxException {
        personSerializationUtil.deserializePersonListFromFile();
    }

}
