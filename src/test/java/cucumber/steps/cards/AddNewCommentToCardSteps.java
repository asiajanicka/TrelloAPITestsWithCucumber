package cucumber.steps.cards;

import TrelloURLs.CardsEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Action;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class AddNewCommentToCardSteps {

    private final RequestHandler requestHandler;
    private final Context context;
    private final CreateRequest createComment;

    @Then("{string} adds comment {string} on card {string}")
    public void adds_comment_on_card(String personName, String commentText, String cardName) {
       requestHandler.clearAll();
       requestHandler.authenticate(personName);
       String cardId = context.getCardId(cardName);
       addNewCommentToCardSetup(cardId, commentText);
       addNewCommentToCard();
    }

    @Then("{string} can not add comment {string} on card {string}")
    public void can_not_add_comment_on_card(String personName, String commentText, String cardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String cardId = context.getCardId(cardName);
        addNewCommentToCardSetup(cardId, commentText);
        Response response = createComment.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_UNAUTHORIZED));
        String errorText = "unauthorized card permission requested";
        assertThat(response.getBody().asString())
                .withFailMessage("Response for unauthorized access to card does not contain " +
                        "\"%s\"", errorText)
                .contains(errorText);
        Allure.step(String.format("Assert if response contains text: \"%s\"", errorText));
    }

    public void addNewCommentToCardSetup(String cardId, String text){
        requestHandler.setEndpoint(CardsEndpoint.addNewCommentToCard(cardId));
        requestHandler.addQueryParam("text", text);
    }

    public Action addNewCommentToCard(){
        Response response = createComment.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step("Assert if status code is OK");
        return response.as(Action.class);
    }
}
