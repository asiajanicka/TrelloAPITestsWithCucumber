package cucumber.steps.cards;

import TrelloURLs.CardsEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Card;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UserName.Kate;

@RequiredArgsConstructor
public class CreateCardSteps {
    private final RequestHandler requestHandler;
    private final Context context;
    private final CreateRequest createCard;

    @When("Kate adds card {string} to list {string}")
    public void kate_adds_card_to_list(String cardName, String listName) {
        requestHandler.clearAll();
        requestHandler.authenticate(Kate);
        String trelloListId = context.getTrelloListId(listName);
        createCardSetup(cardName, trelloListId);
        Card card = createCard();
        assertThat(card.getName()).
                withFailMessage("Added card has incorrect name. It is \"%s\", instead of \"%s\"",
                        card.getName(), cardName)
                .isEqualTo(cardName);
        Allure.step(String.format("Assert if card name is \"%s\"", cardName));
        assertThat(card.getListId())
                .withFailMessage("Card was added to the list with different id")
                .isEqualTo(trelloListId);
        Allure.step(String.format("Assert if card \"%s\" was added to list \"%s\"", cardName, listName));
        context.addCard(cardName, card);
    }

    public void createCardSetup(String cardName, String trelloListId) {
        requestHandler.setEndpoint(CardsEndpoint.createCard());
        requestHandler.addQueryParam("idList", trelloListId);
        requestHandler.addQueryParam("name", cardName);
    }

    public Card createCard() {
        Response response = createCard.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return response.as(Card.class);
    }
}
