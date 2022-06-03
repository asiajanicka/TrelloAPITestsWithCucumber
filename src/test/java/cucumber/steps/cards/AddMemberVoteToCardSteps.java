package cucumber.steps.cards;

import TrelloURLs.CardsEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import propertiesReaders.UsersReader;
import utils.UserName;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class AddMemberVoteToCardSteps {
    private final RequestHandler requestHandler;
    private final CreateRequest addVoteRequest;
    private final Context context;
    private final UsersReader users;

    @Then("{name} votes on card {string}")
    public void votes_on_card(UserName personName, String cardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String cardId = context.getCardId(cardName);
        String memberId = users.getUser(personName).getUserId();
        addMemberVoteToCardSetup(cardId, memberId);
        addMemberVote();
    }

    @Then("{name} can not vote on card {string}")
    public void can_not_vote_on_card(UserName personName, String cardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String cardId = context.getCardId(cardName);
        String memberId = users.getUser(personName).getUserId();
        addMemberVoteToCardSetup(cardId, memberId);
        Response response = addVoteRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_UNAUTHORIZED));
        String errorText = "unauthorized card permission requested";
        assertThat(response.getBody().asString())
                .withFailMessage("Response for unauthorized access to card does not contain " +
                        "\"%s\"", errorText)
                .contains(errorText);
        Allure.step(String.format("Assert if response contains text: \"%s\"", errorText));
    }

    public void addMemberVoteToCardSetup(String cardId, String memberId) {
        requestHandler.setEndpoint(CardsEndpoint.addMemberVoteToCard(cardId));
        requestHandler.addQueryParam("value", memberId);
    }

    public void addMemberVote() {
        Response response = addVoteRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
    }
}
