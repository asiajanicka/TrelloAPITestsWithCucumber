package cucumber.steps.board;

import TrelloURLs.BoardEndpoint;
import api.UpdateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import propertiesReaders.UsersReader;
import utils.UserName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class InviteMemberToBoardViaEmailSteps {

    private final RequestHandler requestHandler;
    private final UpdateRequest updateMember;
    private final Context context;
    private final UsersReader users;

    @Then("{name} can not invite {name} to board {string} via email")
    public void can_not_invite_to_board_via_email(UserName personName, UserName personNameToBeInvited, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String boardId = context.getBoardId(boardName);
        String email = users.getUser(personNameToBeInvited).getEmail();

        inviteMembersToBoardSetup(boardId, email);
        Response response = updateMember.update(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        Allure.step(String.format("Assert if status code is %s", HttpStatus.SC_UNAUTHORIZED));
    }

    @Then("{name} invites {name} to board {string} via email")
    public void invites_to_board_via_email(UserName personName, UserName personNameToBeInvited, String boardName) {
        requestHandler.clearAll();
        requestHandler.authenticate(personName);
        String boardId = context.getBoardId(boardName);
        String emailOfUserToBeInvited = users.getUser(personNameToBeInvited).getEmail();
        String idOfUserToBeInvited = users.getUser(personNameToBeInvited).getUserId();

        inviteMembersToBoardSetup(boardId, emailOfUserToBeInvited);
        Response response = inviteMemberToBoard();
        List<String> listOfBoardMembersIds = response.jsonPath().getList("members.id");
        assertThat(listOfBoardMembersIds).
                withFailMessage("List of board members does not contain %s's id", personNameToBeInvited)
                .contains(idOfUserToBeInvited);
        Allure.step(String.format("Assert if %s was added to board", personName));
    }

    public void inviteMembersToBoardSetup(String boardId, String email, String type) {
        requestHandler.setEndpoint(BoardEndpoint.inviteMemberToBoard(boardId));
        requestHandler.addQueryParam("email", email);
        requestHandler.addQueryParam("type", type);
    }

    public void inviteMembersToBoardSetup(String boardId, String email) {
        requestHandler.setEndpoint(BoardEndpoint.inviteMemberToBoard(boardId));
        requestHandler.addQueryParam("email", email);
    }

    public Response inviteMemberToBoard() {
        Response response = updateMember.update(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert if status code is %s", HttpStatus.SC_OK));
        return response;
    }
}
