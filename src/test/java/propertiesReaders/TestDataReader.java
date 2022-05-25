package propertiesReaders;

public class TestDataReader extends PropertiesLoader{
    public TestDataReader() {
        super("properties/TestData.properties");
    }

    public String getWorkspaceNameForCreateBoardTest(){
        return properties.getProperty("workspace.name.createBoard_1");
    }
}
