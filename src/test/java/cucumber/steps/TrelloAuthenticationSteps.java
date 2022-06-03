package cucumber.steps;

import api.handlers.RequestHandler;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import utils.UserName;

@RequiredArgsConstructor
public class TrelloAuthenticationSteps {

    private final RequestHandler requestHandler;

    @Given("{name} is authenticated to Trello")
    public void is_authenticated_to_trello(UserName name) {
        requestHandler.authenticate(name);
    }
}
