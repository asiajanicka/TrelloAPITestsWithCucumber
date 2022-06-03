package cucumber.steps;

import io.cucumber.java.ParameterType;
import utils.UserName;

public class ParameterTypes {

    @ParameterType("Kate|Tom|John|Lucy")
    public UserName name(String personName){
        return Enum.valueOf(UserName.class, personName);
    }

}
