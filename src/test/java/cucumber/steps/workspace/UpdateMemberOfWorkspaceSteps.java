package cucumber.steps.workspace;

import TrelloURLs.WorkspaceEndpoint;
import api.UpdateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.OrgMember;
import org.apache.http.HttpStatus;
import propertiesReaders.UsersReader;
import utils.users.Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class UpdateMemberOfWorkspaceSteps {

    private final RequestHandler requestHandler;
    private final UpdateRequest updateMemberRequest;
    private final UsersReader usersReader;
    private final Context context;

    @Given("Kate adds {string} as {string} to workspace {string}")
    public void kate_adds_as_to_workspace(String personName, String workspaceRole, String workspaceName) {
        String memberId = Utils.getUser(personName).getUserId();
        String workspaceId = context.getWorkspaceId(workspaceName);
        updateMemberSetup(workspaceId, memberId, workspaceRole);
        Response response = updateMember();
        List<String> listOfUserIds = response.jsonPath().getList("members.id");
        assertThat(listOfUserIds).contains(usersReader.getUser(personName).getUserId());
        Allure.step(String.format("Assert if %s was added to board", personName));
    }

    public void updateMemberSetup(String workspaceId, String memberId, String type) {
        requestHandler.setEndpoint(WorkspaceEndpoint.updateMemberOfOrg(workspaceId, memberId));
        requestHandler.addQueryParam("type", type);
    }

    public Response updateMember() {
        Response response = updateMemberRequest.update(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return response;
    }
}
