package propertiesReaders;

import groovy.lang.Singleton;
import utils.users.User;

@Singleton
public class UsersReader extends PropertiesLoader {

    public UsersReader() {
        super("properties/users.properties");
    }

    public User getUser(String name){
        switch(name){
            case "Tom":{
                return this.getTom();
            }
            case "Lucy":{
                return this.getLucy();
            }
            case "John":
                return this.getJohn();
            default: throw new IllegalArgumentException("Person's name not recognized");
        }
    }

    public User getKate(){
        return new User(properties.getProperty("kate.apiKey"),
                properties.getProperty("kate.token"),
                properties.getProperty("kate.id"),
                properties.getProperty("kate.email"));
    }

    public User getJohn(){
        return new User(properties.getProperty("john.apiKey"),
                properties.getProperty("john.token"),
                properties.getProperty("john.id"),
                properties.getProperty("john.email"));
    }

    public User getTom(){
        return new User(properties.getProperty("tom.apiKey"),
                properties.getProperty("tom.token"),
                properties.getProperty("tom.id"),
                properties.getProperty("tom.email"));
    }

    public User getLucy(){
        return new User(properties.getProperty("lucy.apiKey"),
                properties.getProperty("lucy.token"),
                properties.getProperty("lucy.id"),
                properties.getProperty("lucy.email"));
    }
}
