package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.ReadRequest;
import api.handlers.RequestHandler;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Label;
import model.TrelloList;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GetListsOnBoardSteps {
    private final RequestHandler requestHandler;
    private final ReadRequest readRequest;

    @Step("Get Trello lists on board")
    public List<TrelloList> getLists(String boardId){
        requestHandler.setEndpoint(BoardEndpoint.getListsOnBoard(boardId));
        Response response = readRequest.read(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return Arrays.asList(response.as(TrelloList[].class));
    }
}
