package cucumber.steps.workspace;

import TrelloURLs.WorkspaceEndpoint;
import api.CreateRequest;
import api.handlers.RequestHandler;
import cucumber.context.Context;
import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import model.Organization;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CreateWorkspaceSteps {
    private final CreateRequest createWorkspaceRequest;
    private final RequestHandler requestHandler;
    private final Context context;

    @Given("Kate creates workspace {string}")
    public void kate_creates_workspace(String workspaceName) {
        createWorkspaceSetup(workspaceName);
        Organization workspace = createWorkspace();
        assertThat(workspace.getDisplayName()).isEqualTo(workspaceName);
        Allure.step("Assert workspace name");
        context.addWorkspace(workspaceName, workspace);
    }

    public void createWorkspaceSetup(String workspaceName){
        requestHandler.setEndpoint(WorkspaceEndpoint.createWorkspace());
        requestHandler.addQueryParam("displayName", workspaceName);
    }
    public Organization createWorkspace(){
        Response response = createWorkspaceRequest.create(requestHandler);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
        return response.as(Organization.class);
    }
}
