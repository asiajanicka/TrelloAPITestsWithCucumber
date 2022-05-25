package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.ReadRequest;
import api.handlers.RequestHandler;
import api.handlers.ResponseHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Board;
import org.apache.http.HttpStatus;
import propertiesReaders.AppPropertiesReader;
import utils.users.SetupData;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ReadBoardSteps {

    private final RequestHandler requestHandler;
    private final ResponseHandler responseHandler;
    private final ReadRequest readRequest;
    private final Context context;
    private final AppPropertiesReader appPropertiesReader;


    @Then("Kate sees board {string} in workspace {string} with default params")
    public void kate_sees_board_in_workspace_with_default_params(String boardName, String workspaceName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        String boardId = context.getBoardId(boardName);
        Board board = readBoardById(boardId);
        assertBoardNameAndWorkspaceName(boardName, workspaceName, board);
        assertBoardDefaultParams(board);
    }

    @Then("Kate sees board with name of limit length in workspace {string}")
    public void kate_sees_board_with_name_of_length_in_workspace(String workspaceName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        String boardName = context.getBoardNameWithGivenLength();
        String boardId = context.getBoardId(boardName);
        Board board = readBoardById(boardId);
        assertBoardNameAndWorkspaceName(boardName, workspaceName, board);
    }

    @Then("Kate sees board {string} in workspace {string}")
    public void kate_sees_board_in_workspace(String boardName, String workspaceName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        String boardId = context.getBoardId(boardName);
        Board board = readBoardById(boardId);
        assertBoardNameAndWorkspaceName(boardName, workspaceName, board);
    }

    @Then("response is {string} with status code {int}")
    public void response_is_with_status_code(String errorText, Integer statusCode) {
        assertThat(responseHandler.getStatusCode()).isEqualTo(statusCode);
        assertThat(responseHandler.getResponse().getBody().asString()).contains(errorText);
    }

    private Board readBoardById(String id) {
        requestHandler.setEndpoint(BoardEndpoint.getBoard(id));
        Response response = readRequest.read(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step("Assert if status code is OK");
        return response.as(Board.class);
    }

    public void assertBoardNameAndWorkspaceName(String boardName, String workspaceName, Board board){
        assertThat(board.getName()).isEqualTo(boardName);
        Allure.step(String.format("Assert if board's name is \"%s\"", boardName));
        String workspaceId = SetupData.getWorkspaceId(workspaceName);
        assertThat(board.getIdOrganization()).isEqualTo(workspaceId);
        Allure.step(String.format("Assert if board is created in workspace \"%s\" with id \"%s\"",
                workspaceName,
                workspaceId));
    }

    public void assertBoardDefaultParams(Board board){
        assertThat(board.getPrefs().getPermissionLevel())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultParamPermissionLevel());
        assertThat(board.getPrefs().getVoting())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultParamVotingPrefs());
        assertThat(board.getPrefs().getComments())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultParamCommentsPrefs());
        assertThat(board.getPrefs().getInvitations())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultParamInvitationsPrefs());
        assertThat(board.getPrefs().getSelfJoin())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultParamSelfJoinPrefs());
        assertThat(board.getPrefs().getBackgroundColor())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultPramBackgroundColor());
        assertThat(board.getPrefs().getCardAging())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultParamCardAgingPrefs());
    }

}
