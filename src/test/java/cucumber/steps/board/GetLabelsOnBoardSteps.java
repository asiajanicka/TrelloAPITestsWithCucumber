package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.ReadRequest;
import api.handlers.RequestHandler;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Label;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GetLabelsOnBoardSteps {
    private final RequestHandler requestHandler;
    private final ReadRequest readRequest;


    @Step("Get labels on board")
    public List<Label> getLabels(String boardId){
        requestHandler.setEndpoint(BoardEndpoint.getLabelsOnBoard(boardId));
        Response response = readRequest.read(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return Arrays.asList(response.as(Label[].class));
    }
}
