package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
        TrelloList listOnBoard = createListOnBoard(boardId, listName);
        assertThat(listOnBoard.getName()).isEqualTo(listName);
    }

    @When("{string} can not add list {string} on {string}")
    public void can_not_add_list_on(String personName, String listName, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String boardId = context.getBoardId(boardName);
        createListOnBoardSetUp(boardId, listName);
        Response response = createListRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
    }

    private void createListOnBoardSetUp(String boardId, String name){
        requestHandler.setEndpoint(BoardEndpoint.createListOnBoard(boardId));
        requestHandler.addQueryParam("name", name);
    }

    public TrelloList createListOnBoard(String boardId, String name){
        createListOnBoardSetUp(boardId, name);
        Response response = createListRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        return response.as(TrelloList.class);
    }
}
