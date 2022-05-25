package cucumber.hooks;

import TrelloURLs.BoardEndpoint;
import api.DeleteRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.After;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class Hooks {

    private final Context context;
    private final RequestHandler requestHandler;
    private final DeleteRequest deleteBoardRequest;

    @After(value = "@cleanUp")
    public void afterScenario(){
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        context.getBoards().values().forEach(board-> {
            requestHandler.setEndpoint(BoardEndpoint.deleteBoard(board.getId()));
            Response response = deleteBoardRequest.delete(requestHandler);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        });
    }
}
