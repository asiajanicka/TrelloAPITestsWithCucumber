package propertiesReaders;

import groovy.lang.Singleton;
import utils.users.BoardProperties;

@Singleton
public class AppPropertiesReader extends PropertiesLoader{

    public AppPropertiesReader() {
        super("properties/app.properties");
    }

    public BoardProperties getBoardProperties(){
        return new BoardProperties(properties);
    }
}
