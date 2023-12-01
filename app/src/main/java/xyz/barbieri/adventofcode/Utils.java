package xyz.barbieri.adventofcode;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static List<String> readLines(String fileName) {
        ClassLoader classLoader = App.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        try {
            return FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
