package propertiesReaders;

import lombok.SneakyThrows;

import java.util.Properties;

public abstract class PropertiesLoader {
    protected Properties properties;

    @SneakyThrows
    public PropertiesLoader(String path) {
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream(path));
    }

}
