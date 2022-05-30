package cucumber.steps.workspace;

import TrelloURLs.WorkspaceEndpoint;
import api.DeleteRequest;
import api.handlers.RequestHandler;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DeleteWorkspaceSteps {

    private final RequestHandler requestHandler;
    private final DeleteRequest deleteWorkspaceRequest;

    @Step("Delete workspace")
    public void deleteWorkspace(String id){
        requestHandler.setEndpoint(WorkspaceEndpoint.deleteWorkspace(id));
        Response response = deleteWorkspaceRequest.delete(requestHandler);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
        Allure.step(String.format("Assert status code %s", HttpStatus.SC_OK));
    }
}
