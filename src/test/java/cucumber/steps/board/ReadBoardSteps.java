package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.ReadRequest;
import api.handlers.RequestHandler;
import api.handlers.ResponseHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Board;
import model.Label;
import model.TrelloList;
import org.apache.http.HttpStatus;
import propertiesReaders.AppPropertiesReader;
import utils.users.Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ReadBoardSteps {

    private final RequestHandler requestHandler;
    private final ResponseHandler responseHandler;
    private final ReadRequest readRequest;
    private final Context context;
    private final AppPropertiesReader appPropertiesReader;
    private final GetLabelsOnBoardSteps getLabelsOnBoardSteps;
    private final GetListsOnBoardSteps getListsOnBoardSteps;


    @Then("Kate sees board {string} in workspace {string} with default params")
    public void kate_sees_board_in_workspace_with_default_params(String boardName, String workspaceName) {
        Board board = kate_sees_board_in_workspace(boardName, workspaceName);
        assertBoardDefaultParams(board);
    }

    @Then("Kate sees board with name of limit length in workspace {string}")
    public void kate_sees_board_with_name_of_length_in_workspace(String workspaceName) {
        String boardName = context.getBoardNameWithGivenLength();
        kate_sees_board_in_workspace(boardName, workspaceName);
    }

    @Then("Kate sees board {string} in workspace {string}")
    public Board kate_sees_board_in_workspace(String boardName, String workspaceName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        String boardId = context.getBoardId(boardName);
        Board board = readBoard(boardId);
        assertBoardNameAndWorkspaceName(boardName, workspaceName, board);
        return board;
    }

    @Then("response is {string} with status code {int}")
    public void response_is_with_status_code(String errorText, Integer statusCode) {
        assertThat(responseHandler.getStatusCode()).isEqualTo(statusCode);
        Allure.step(String.format("Assert status code: %s", statusCode));
        assertThat(responseHandler.getResponse().getBody().asString()).contains(errorText);
        Allure.step(String.format("Assert if error response contain text \"%s\"", errorText));
    }

    @Then("Kate sees two boards with name {string} in workspace {string}")
    public void kate_sees_two_boards_with_name_in_workspace(String boardName, String workspaceName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        String board_1_Id = context.getBoardId(boardName);
        Board board_1 = readBoard(board_1_Id);
        Allure.step(String.format("Kate reads first board \"%s\"", boardName));
        assertBoardNameAndWorkspaceName(boardName, workspaceName, board_1);
        String board_2_Id = context.getBoardId(boardName + "Duplicate");
        Board board_2 = readBoard(board_2_Id);
        Allure.step(String.format("Kate reads second board \"%s\"", boardName));
        assertBoardNameAndWorkspaceName(boardName, workspaceName, board_2);
    }

    @Then("Kate sees board {string} without default labels in workspace {string}")
    public void kate_sees_board_without_default_labels_in_workspace(String boardName, String workspaceName) {
        Board board = kate_sees_board_in_workspace(boardName, workspaceName);
        List<Label> labelsList = getLabelsOnBoardSteps.getLabels(board.getId());
        Allure.step("Get labels on board");
        assertThat(labelsList)
                .withFailMessage("Board has %d labels when it should be have none",
                        labelsList.size())
                .isEmpty();
        Allure.step("Assert if label list is empty");
    }

    @Then("Kate sees board {string} without default lists in workspace {string}")
    public void kate_sees_board_without_default_lists_in_workspace(String boardName, String workspaceName) {
        Board board = kate_sees_board_in_workspace(boardName, workspaceName);
        List<TrelloList> trelloListsList = getListsOnBoardSteps.getLists(board.getId());
        Allure.step("Get lists on board");
        assertThat(trelloListsList)
                .withFailMessage("Board has %d trello lists when it should have none",
                        trelloListsList.size())
                .isEmpty();
        Allure.step("Assert if list of Trello lists is empty");
    }

    @Then("Kate sees board {string} with correct description in workspace {string}")
    public void kate_sees_board_with_correct_description_in_workspace(String boardName, String workspaceName) {
        Board board = kate_sees_board_in_workspace(boardName, workspaceName);
        String boardDesc = context.getBoardDescWithGivenLength();
        String actualBoardDesc = board.getDesc();
        assertThat(actualBoardDesc)
                .withFailMessage("Board has incorrect description. Actual desc is \"s\", when" +
                        "it should be \"s\"",
                        actualBoardDesc,
                        boardDesc)
                .isEqualTo(boardDesc);
        Allure.step(String.format("Assert is board's description is \"%s\"", boardDesc));
    }

    @Then("Kate sees board {string}")
    public void kate_sees_board(String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        String boardId = context.getBoardId(boardName);
        Board board = readBoard(boardId);
        String actualBoardName = board.getName();
        assertThat(board.getName())
                .withFailMessage("Board has incorrect name. Actual name is \"%s\", when " +
                        "it should be \"%s\"",
                        actualBoardName,
                        boardName).isEqualTo(boardName);
        Allure.step(String.format("Assert if board's name is \"%s\"", boardName));
    }

    @Then("Kate sees {string} board {string} in workspace {string}")
    public void kate_sees_board_in_workspace(String boardType, String boardName, String workspaceName) {
        Board board = kate_sees_board_in_workspace(boardName, workspaceName);
        String actualPermissionLevel = board.getPrefs().getPermissionLevel();
        assertThat(actualPermissionLevel)
                .withFailMessage("Board permission level is \"%s\" instead of \"%s\"",
                        actualPermissionLevel,
                        boardType)
                .isEqualTo(Utils.getPermissionLevel(boardType));
        Allure.step(String.format("Assert if board is %s", boardType));
    }

    @Then("Kate sees board {string} in workspace {string} with voting set to {string}")
    public void kate_sees_board_in_workspace_with_voting_set_to(String boardName,
                                                                String workspaceName,
                                                                String votingGroup) {
        Board board = kate_sees_board_in_workspace(boardName, workspaceName);
        String actualVotingLevel = board.getPrefs().getVoting();
        String expectedVotingLevel = Utils.getVotingLevel(votingGroup);
        assertThat(actualVotingLevel)
                .withFailMessage("Board voting group is \"%s\" instead of \"%s\"",
                        actualVotingLevel,
                        expectedVotingLevel)
                .isEqualTo(expectedVotingLevel);
        Allure.step(String.format("Assert if board voting group is \"%s\"", votingGroup));
    }

    @Then("{string} reads board {string}")
    public void reads_board(String personName, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String boardId = context.getBoardId(boardName);
        Board board = readBoard(boardId);
        String actualBoardName = board.getName();
        assertThat(actualBoardName)
                .withFailMessage("Board has incorrect name. Actual name is \"%s\" instead of \"%s\"",
                        actualBoardName,
                        boardName)
                .isEqualTo(boardName);
        Allure.step(String.format("Assert if board's name is \"%s\"", boardName));
    }

    @Then("{string} can not read board {string}")
    public void can_not_read_board(String personName, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String boardId = context.getBoardId(boardName);
        requestHandler.setEndpoint(BoardEndpoint.getBoard(boardId));
        Response response = readRequest.read(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        Allure.step("Assert status code is UNAUTHORIZED");
        String expectedErrorText = "unauthorized permission requested";
        assertThat(response.getBody().asString()).contains(expectedErrorText);
        Allure.step(String.format("Assert if error response contains text \"%s\"", expectedErrorText));
    }

    @Then("Kate sees board {string} in workspace {string} with {string} allowed for commenting")
    public void kate_sees_board_in_workspace_with_allowed_for_commenting(String boardName,
                                                                         String workspaceName,
                                                                         String commentingGroup) {
        Board board = kate_sees_board_in_workspace(boardName, workspaceName);
        String actualCommentingLevel = board.getPrefs().getComments();
        String expectedCommentingLevel = Utils.getCommentingLevel(commentingGroup);
        assertThat(actualCommentingLevel)
                .withFailMessage("Board commenting group is \"%s\" instead of \"%s\"",
                        actualCommentingLevel,
                        expectedCommentingLevel)
                .isEqualTo(expectedCommentingLevel);
        Allure.step(String.format("Assert if board commenting group is \"%s\"", commentingGroup));
    }

    private Board readBoard(String id) {
        requestHandler.setEndpoint(BoardEndpoint.getBoard(id));
        Response response = readRequest.read(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step("Assert if status code is OK");
        return response.as(Board.class);
    }

    @Step("Assert if board called {0} is in workspace {1}")
    public void assertBoardNameAndWorkspaceName(String boardName, String workspaceName, Board board) {
        assertThat(board.getName()).isEqualTo(boardName);
        Allure.step(String.format("Assert if board name is \"%s\"", boardName));
        String workspaceId = context.getWorkspaceId(workspaceName);
        assertThat(board.getIdOrganization()).isEqualTo(workspaceId);
        Allure.step(String.format("Assert if board is created in workspace \"%s\"",
                workspaceName,
                workspaceId));
    }

    @Step("Assert board default params")
    public void assertBoardDefaultParams(Board board) {
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
        assertThat(board.getPrefs().getCardAging())
                .isEqualTo(appPropertiesReader.getBoardProperties().getDefaultParamCardAgingPrefs());
        Allure.step("Assert board default params");
    }
}
