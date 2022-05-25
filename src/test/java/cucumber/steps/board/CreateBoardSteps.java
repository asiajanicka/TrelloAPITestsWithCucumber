package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import api.handlers.ResponseHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Board;
import org.apache.http.HttpStatus;
import propertiesReaders.AppPropertiesReader;
import utils.users.SetupData;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CreateBoardSteps {

    private final RequestHandler requestHandler;
    private final ResponseHandler responseHandler;
    private final CreateRequest createBoardRequest;
    private final Context context;
    private final AppPropertiesReader appPropertiesReader;

    @Given("board {string} and workspace {string}")
    public void board_and_workspace(String boardName, String workspaceName) {
        requestHandler.addQueryParam("name", boardName);
        requestHandler.addQueryParam("idOrganization", SetupData.getWorkspaceId(workspaceName));
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
        requestHandler.addQueryParam("idOrganization", SetupData.getWorkspaceId(workspaceName));
        requestHandler.addQueryParam("name", boardName);
    }

    @When("Kate creates board {string} with default params")
    public void kate_creates_board_with_default_params(String boardName) {
        createBoard(boardName);
    }

    @When("Kate creates board with name of limit length")
    public void kate_creates_board_with_name_of_length() {
        String boardName = context.getBoardNameWithGivenLength();
        createBoard(boardName);
    }

    @When("Kate creates board {string}")
    public void kate_creates_board(String boardName) {
       createBoard(boardName);
    }

    @When("Kate tries to create board")
    public void kate_tries_to_create_board() {
        requestHandler.setEndpoint(BoardEndpoint.createBoard());
        Response response = createBoardRequest.create(requestHandler);
        responseHandler.setResponse(response);
    }

    private Board createBoard(String boardName) {
        requestHandler.setEndpoint(BoardEndpoint.createBoard());
        Response response = createBoardRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Board board = response.as(Board.class);
        assertThat(board.getName()).isEqualTo(boardName);
        context.addBoard(boardName, board);
        return board;
    }
}
