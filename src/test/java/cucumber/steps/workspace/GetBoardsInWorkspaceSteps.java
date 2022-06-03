package cucumber.steps.workspace;

import TrelloURLs.WorkspaceEndpoint;
import api.ReadRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Board;
import org.apache.http.HttpStatus;
import propertiesReaders.AppPropertiesReader;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UserName.Kate;

@RequiredArgsConstructor
public class GetBoardsInWorkspaceSteps {

    private final RequestHandler requestHandler;
    private final Context context;
    private final ReadRequest readBoardsRequest;
    private final AppPropertiesReader appProperties;

    @Then("Kate sees max number of boards in workspace {string}")
    public void kate_sees_max_number_of_boards_in_workspace(String workspaceName) {
        requestHandler.clearAll();
        requestHandler.authenticate(Kate);
        List<Board> boards = getBoards(workspaceName);
        int maxNumberOfBoardsInWorkspace = appProperties.getBoardProperties().getLimitPerWorkspace();
        assertThat(boards.size())
                .withFailMessage("There are only %d boards in workspace instead of %d",
                        boards.size(),
                        maxNumberOfBoardsInWorkspace)
                .isEqualTo(maxNumberOfBoardsInWorkspace);
        Allure.step(String.format("Assert if there are %d boards in workspace",maxNumberOfBoardsInWorkspace));
    }

    public List<Board> getBoards(String workspaceName){
        String workspaceId = context.getWorkspaceId(workspaceName);
        requestHandler.setEndpoint(WorkspaceEndpoint.getBoardsInWorkspace(workspaceId));
        Response response = readBoardsRequest.read(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert if status code is %s", HttpStatus.SC_OK));
        return Arrays.asList(response.as(Board[].class));
    }
}
