package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.UpdateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import propertiesReaders.UsersReader;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RequiredArgsConstructor
public class AddMemberToBoardSteps {

    private final RequestHandler requestHandler;
    private final Context context;
    private final UsersReader usersReader;
    private final UpdateRequest updateRequest;

    @Then("Kate adds {string} as {string} to board {string}")
    public void kate_adds_tom_as_to_board(String personName, String role, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticateKate();
        String boardId = context.getBoardId(boardName);
        String memberId = usersReader.getTom().getUserId();
        addMemberToBoardSetUp(boardId, memberId, role);
        Response response = addMember();
        List<String> listOfUserIds = response.jsonPath().getList("members.id");
        Assertions.assertThat(listOfUserIds)
                .withFailMessage("List of board members' ids does not contain %s' id", personName)
                .contains(usersReader.getUser(personName).getUserId());
        Allure.step(String.format("Assert if %s was added to board", personName));
    }

    private void addMemberToBoardSetUp(String boardId, String memberId, String type){
        requestHandler.setEndpoint(BoardEndpoint.addMemberToBoard(boardId, memberId));
        requestHandler.addQueryParam("type", type);
    }

    @Step("Add/Update member to board")
    private Response addMember(){
        Response response = updateRequest.update(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return response;
    }
}
