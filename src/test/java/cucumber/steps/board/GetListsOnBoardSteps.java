package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.ReadRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.TrelloList;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UserName.Kate;

@RequiredArgsConstructor
public class GetListsOnBoardSteps {
    private final RequestHandler requestHandler;
    private final ReadRequest readRequest;
    private final Context context;

    @When("Kate sees default list {string} on board {string}")
    public void kate_sees_default_list_on_board(String listName, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(Kate);
        String boardId = context.getBoardId(boardName);
        List<TrelloList> listsOnBoard = getLists(boardId);
        assertThat(listsOnBoard).extracting(trelloList -> trelloList.getName())
                .withFailMessage("Board does not have list called \"%s\"", listName)
                .contains(listName);
        Allure.step(String.format("Assert if list \"%s\" is on board", listName));
        TrelloList trelloList = listsOnBoard.stream()
                .filter(list -> list.getName().equals(listName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List with given name does not exist on board"));
        context.addTrelloList(listName, trelloList);
    }

    @Step("Get Trello lists on board")
    public List<TrelloList> getLists(String boardId){
        requestHandler.setEndpoint(BoardEndpoint.getListsOnBoard(boardId));
        Response response = readRequest.read(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return Arrays.asList(response.as(TrelloList[].class));
    }
}
