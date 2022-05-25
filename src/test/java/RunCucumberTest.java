import TrelloURLs.WorkspaceEndpoint;
import api.CreateRequest;
import api.DeleteRequest;
import api.handlers.RequestHandler;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import propertiesReaders.TestDataReader;
import utils.users.SetupData;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"},
        glue = {"cucumber/steps"},
        features = "src/test/resources/features/withAuth/create_board.feature"
)
public class RunCucumberTest {

    @BeforeClass
    public static void setUp() {
        RequestHandler requestHandler = new RequestHandler();

//        create Workspace dedicated to Create Board tests
        CreateRequest createWorkspace = new CreateRequest();
//        authenticate Kate
        requestHandler.authenticateKate();
//        set endpoint for the request
        requestHandler.setEndpoint(WorkspaceEndpoint.createWorkspace());
//        set name for workspace
        TestDataReader testData = new TestDataReader();
        String workspaceName = testData.getWorkspaceNameForCreateBoardTest();
        requestHandler.addQueryParam("displayName", workspaceName);

        Response response = createWorkspace.create(requestHandler);
        String workspaceId = response.jsonPath().getString("id");
//        add workspace name and id to set up data map
        SetupData.addWorkspace(workspaceName, workspaceId);
    }

    @AfterClass
    public static void tearDown() {
//        delete workspaces used in tests
        RequestHandler requestHandler = new RequestHandler();
//        authenticate Kate
        requestHandler.authenticateKate();
//        delete all workspaces that are in set up data workspace map
        DeleteRequest deleteRequest = new DeleteRequest();
        SetupData.getWorkspaces().values().forEach(workspaceId -> {
            requestHandler.setEndpoint(WorkspaceEndpoint.deleteWorkspace(workspaceId));
            Response response = deleteRequest.delete(requestHandler);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        });
    }
}
