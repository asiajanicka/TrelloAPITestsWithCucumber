package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import api.handlers.ResponseHandler;
import cucumber.context.Context;
import cucumber.steps.workspace.CreateWorkspaceSteps;
import cucumber.steps.workspace.DeleteWorkspaceSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Board;
import org.apache.http.HttpStatus;
import propertiesReaders.AppPropertiesReader;
import utils.users.Utils;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CreateBoardSteps {

    private final RequestHandler requestHandler;
    private final ResponseHandler responseHandler;
    private final CreateRequest createBoardRequest;
    private final Context context;
    private final AppPropertiesReader appPropertiesReader;
    private final CreateWorkspaceSteps createWorkspaceSteps;
    private final DeleteWorkspaceSteps deleteWorkspaceSteps;

    @Given("board name {string} and workspace {string}")
    public void board_name_and_workspace(String boardName, String workspaceName) {
        createBoardSetup(boardName, workspaceName);
    }

    @Given("board name of {string} length and workspace {string}")
    public void board_name_of_length_and_workspace(String length, String workspaceName) {
        String boardName;
        switch (length.trim().toLowerCase()) {
            case "min": {
                boardName = randomAlphabetic(appPropertiesReader.getBoardProperties().getNameMinLength());
                break;
            }
            case "max": {
                boardName = randomAlphabetic(appPropertiesReader.getBoardProperties().getNameMaxLength());
                break;
            }
            case "over max": {
                boardName = randomAlphabetic(appPropertiesReader.getBoardProperties().getNameMaxLength() + 1);
                break;
            }
            default:
                throw new IllegalArgumentException("Board name length not recognized");
        }
        context.setBoardNameWithGivenLength(boardName);
        createBoardSetup(boardName, workspaceName);
    }

    @Given("workspace {string}")
    public void workspace(String workspaceName) {
        requestHandler.addQueryParam("idOrganization", context.getWorkspaceId(workspaceName));
    }

    @Given("Kate already created board {string} in workspace {string}")
    public void kate_already_created_board_in_workspace(String boardName, String workspaceName) {
        createBoardSetup(boardName, workspaceName);
        Board board = createBoard();
        context.addBoard(boardName + "Duplicate", board);
    }

    @Given("Kate wants board without {string}")
    public void kate_wants_board_without(String param) {
        requestHandler.addQueryParam(param, "false");
    }

    @Given("Kate wants board with {string} set to {string} value")
    public void kate_wants_board_with_set_to_value(String param, String value) {
        switch (value) {
            case "blank": {
                requestHandler.addQueryParam(param, "");
                break;
            }
            case "invalid": {
                requestHandler.addQueryParam(param, "invalid");
                break;
            }
            default:
                throw new IllegalArgumentException(String.format("Unrecognized value for %s", param));
        }
    }

    @Given("Kate wants board with description of {string} length")
    public void kate_wants_board_with_description_of_length(String length) {
        String desc;
        switch (length.trim().toLowerCase()) {
            case "min": {
                desc = randomAlphabetic(appPropertiesReader.getBoardProperties().getDescMinLength());
                break;
            }
            case "middle": {
                desc = randomAlphabetic(appPropertiesReader.getBoardProperties().getDescMiddleLength());
                break;
            }
            case "max": {
                desc = randomAlphabetic(appPropertiesReader.getBoardProperties().getDescMaxLength());
                break;
            }
            case "over max": {
                desc = randomAlphabetic(appPropertiesReader.getBoardProperties().getDescMaxLength() + 1);
                break;
            }
            default:
                throw new IllegalArgumentException("Description length not recognized");
        }
        context.setBoardDescWithGivenLength(desc);
        requestHandler.addQueryParam("desc", desc);
    }

    @Given("board name {string} and workspace with {string} id")
    public void board_name_and_workspace_with_id(String boardName, String invalidId) {
        String workspaceId;
        switch (invalidId) {
            case "invalid": {
                workspaceId = "invalid";
                break;
            }
            case "blank": {
                workspaceId = "";
                break;
            }
            default:
                throw new IllegalArgumentException("Value for workspace not recognized");
        }
        requestHandler.addQueryParam("name", boardName);
        requestHandler.addQueryParam("idOrganization", workspaceId);
    }

    @Given("board name {string} and id of workspace that was deleted")
    public void board_name_and_id_of_workspace_that_was_deleted(String boardName) {
//        create workspace that will be deleted and get ID
        createWorkspaceSteps.createWorkspaceSetup("Workspace to delete");
        Allure.step("Create new workspace");
        String workspaceId = createWorkspaceSteps.createWorkspace().getId();
//        delete the workspace
        deleteWorkspaceSteps.deleteWorkspace(workspaceId);
        Allure.step("Delete that workspace and use its id");
//        use the id of previously deleted workspace
        requestHandler.addQueryParam("name", boardName);
        requestHandler.addQueryParam("idOrganization", workspaceId);
    }

    @Given("board name {string}")
    public void board_name(String boardName) {
        requestHandler.addQueryParam("name", boardName);
    }

    @Given("Kate wants {string} board {string} in {string}")
    public void kate_wants_board_in(String boardType, String boardName, String workspaceName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        createBoardSetup(boardName, workspaceName);
        requestHandler.addQueryParam("prefs_permissionLevel", Utils.getPermissionLevel(boardType));
    }

    @Given("permission level set to {string} value")
    public void set_to_value(String type) {
        if (type.equalsIgnoreCase("blank")) {
            requestHandler.addQueryParam("prefs_permissionLevel", "");
        } else if (type.equalsIgnoreCase("invalid")) {
            requestHandler.addQueryParam("prefs_permissionLevel", "invalid");
        } else {
            throw new IllegalArgumentException("Value for permission level not recognized");
        }
    }

    @Given("where {string} can vote on cards")
    public void where_can_vote_on_cards(String votingGroup) {
        String votingPrefs;
        switch (votingGroup) {
            case "only board members": {
                votingPrefs = "members";
                break;
            }
            case "workspace members": {
                votingPrefs = "org";
                break;
            }
            case "public users": {
                votingPrefs = "public";
                break;
            }
            default:
                throw new IllegalArgumentException("Voting group not recognized");
        }
        requestHandler.addQueryParam("prefs_voting", votingPrefs);
    }

    @Given("where voting on cards is disabled")
    public void where_voting_on_cards_is_disabled() {
        requestHandler.addQueryParam("prefs_voting", "disabled");
    }

    @Given("voting group set to {string} value")
    public void voting_group_set_to_value(String type) {
        if (type.equalsIgnoreCase("blank")) {
            requestHandler.addQueryParam("prefs_voting", "");
        } else if (type.equalsIgnoreCase("invalid")) {
            requestHandler.addQueryParam("prefs_voting", "invalid");
        } else {
            throw new IllegalArgumentException("Value for voting prefs not recognized");
        }
    }

    @When("Kate creates board {string} with default params")
    public void kate_creates_board_with_default_params(String boardName) {
        kate_creates_board(boardName);
    }

    @When("Kate creates board with name of (.*) length")
    public void kate_creates_board_with_name_of_length() {
        String boardName = context.getBoardNameWithGivenLength();
        kate_creates_board(boardName);
    }

    @When("Kate creates board {string}")
    public Board kate_creates_board(String expectedBoardName) {
        requestHandler.setEndpoint(BoardEndpoint.createBoard());
        Board board = createBoard();
        String actualBoardName = board.getName();
        assertThat(actualBoardName)
                .withFailMessage("Board name is \"%s\" instead of \"%s\"",
                        actualBoardName,
                        expectedBoardName )
                .isEqualTo(expectedBoardName);
        Allure.step(String.format("Assert if board name is \"%s\"", expectedBoardName));
        context.addBoard(expectedBoardName, board);
        return board;
    }

    @When("Kate creates {string} board {string}")
    public void kate_creates_board(String expectedBoardType, String expectedBoardName) {
        Board board = kate_creates_board(expectedBoardName);
        String actualPermissionLevel = board.getPrefs().getPermissionLevel();
        assertThat(actualPermissionLevel)
                .withFailMessage("Board permission level is \"%s\" instead of \"%s\"",
                        actualPermissionLevel,
                        expectedBoardType)
                .isEqualTo(Utils.getPermissionLevel(expectedBoardType));
        Allure.step(String.format("Assert if board is %s", expectedBoardType));
    }

    @When("Kate tries to create board")
    public void kate_tries_to_create_board() {
        requestHandler.setEndpoint(BoardEndpoint.createBoard());
        Response response = createBoardRequest.create(requestHandler);
        responseHandler.setResponse(response);
    }

    @When("Kate creates board {string} with voting set to {string}")
    public void kate_creates_board_with_voting_set_to(String expectedBoardName, String votingGroup) {
        Board board = kate_creates_board(expectedBoardName);
        String actualVotingLevel = board.getPrefs().getVoting();
        String expectedVotingLevel = Utils.getVotingLevel(votingGroup);
        assertThat(actualVotingLevel)
                .withFailMessage("Board voting group is \"%s\" instead of \"%s\"",
                        actualVotingLevel,
                        expectedVotingLevel)
                .isEqualTo(expectedVotingLevel);
        Allure.step(String.format("Assert if board voting group is \"%s\"", votingGroup));
    }

    private void createBoardSetup(String boardName, String workspaceName) {
        requestHandler.setEndpoint(BoardEndpoint.createBoard());
        requestHandler.addQueryParam("idOrganization", context.getWorkspaceId(workspaceName));
        requestHandler.addQueryParam("name", boardName);
    }

    private Board createBoard() {
        Response response = createBoardRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return response.as(Board.class);
    }
}
