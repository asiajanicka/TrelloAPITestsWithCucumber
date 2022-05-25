package propertiesReaders;

import groovy.lang.Singleton;
import utils.users.User;

@Singleton
public class UsersReader extends PropertiesLoader {

    public UsersReader() {
        super("properties/users.properties");
    }

    public User getKate(){
        return new User(properties.getProperty("kate.apiKey"),
                properties.getProperty("kate.token"),
                properties.getProperty("kate.id")
                );
    }

    public User getJohn(){
        return new User(properties.getProperty("john.apiKey"),
                properties.getProperty("john.token"),
                properties.getProperty("john.id"));
    }

    public User getTom(){
        return new User(properties.getProperty("tom.apiKey"),
                properties.getProperty("tom.token"),
                properties.getProperty("tom.id"));
    }

    public User getLucy(){
        return new User(properties.getProperty("lucy.apiKey"),
                properties.getProperty("lucy.token"),
                properties.getProperty("lucy.id"));
    }
}
