package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.DeleteRequest;
import api.handlers.RequestHandler;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DeleteBoardSteps {
    private final RequestHandler requestHandler;
    private final DeleteRequest deleteBoardRequest;

    @Step("Delete board")
    public void deleteBoard(String boardId){
        requestHandler.setEndpoint(BoardEndpoint.deleteBoard(boardId));
        Response response = deleteBoardRequest.delete(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
    }
}
