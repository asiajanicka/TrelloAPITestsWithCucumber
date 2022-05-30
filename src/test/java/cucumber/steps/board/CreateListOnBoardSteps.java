package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.TrelloList;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CreateListOnBoardSteps {

    private final RequestHandler requestHandler;
    private final CreateRequest createListRequest;
    private final Context context;

    @Then("{string} adds list {string} on {string}")
    public void adds_list_on(String personName, String listName, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String boardId = context.getBoardId(boardName);
        createListOnBoardSetUp(boardId, listName);
        TrelloList listOnBoard = createListOnBoard();
        String actualListName = listOnBoard.getName();
        assertThat(actualListName)
                .withFailMessage("Name of created list is \"%s\" instead of \"%s\"",
                        actualListName,
                        listName)
                .isEqualTo(listName);
        Allure.step(String.format("Assert is list name is \"%s\"", listName));
    }

    @When("{string} can not add list {string} on {string}")
    public void can_not_add_list_on(String personName, String listName, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String boardId = context.getBoardId(boardName);
        createListOnBoardSetUp(boardId, listName);
        Response response = createListRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_UNAUTHORIZED));
    }

    private void createListOnBoardSetUp(String boardId, String name) {
        requestHandler.setEndpoint(BoardEndpoint.createListOnBoard(boardId));
        requestHandler.addQueryParam("name", name);
    }

    @Step("Create new list on board")
    public TrelloList createListOnBoard() {
        Response response = createListRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return response.as(TrelloList.class);
    }
}
