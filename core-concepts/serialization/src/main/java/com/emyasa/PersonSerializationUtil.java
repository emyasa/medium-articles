package com.emyasa;

import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

public class PersonSerializationUtil {

    private static final String FILE_NAME = "/file.ser";

    public void serializePersonListToFile(List<Person> personList) throws IOException {
        File file = new File(getClass().getResource(FILE_NAME).getPath());
        try (FileOutputStream fos = new FileOutputStream(file);
          ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(personList);
        }
    }

    public List<Person> deserializePersonListFromFile() throws URISyntaxException, IOException {
        File file = new File(getClass().getResource(FILE_NAME).toURI());
        byte[] contents = Files.readAllBytes(file.toPath());
        return SerializationUtils.deserialize(contents);
    }
}
