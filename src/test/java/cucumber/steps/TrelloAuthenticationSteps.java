package cucumber.steps;

import api.handlers.RequestHandler;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrelloAuthenticationSteps {

    private final RequestHandler requestHandler;

    @Given("{string} is authenticated to Trello")
    public void is_authenticated_to_trello(String name) {

        switch (name){
            case "Kate":{
                requestHandler.authenticateKate();
                break;
            }
            case "Tom":{
                requestHandler.authenticateTom();
                break;
            }
            default: throw new IllegalArgumentException("User not recognized");
        }
    }
}
