package cucumber.hooks;

import api.handlers.RequestHandler;
import cucumber.context.Context;
import cucumber.steps.board.DeleteBoardSteps;
import cucumber.steps.workspace.CreateWorkspaceSteps;
import cucumber.steps.workspace.DeleteWorkspaceSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Allure;
import lombok.RequiredArgsConstructor;
import model.Organization;

import static utils.UserName.Kate;

@RequiredArgsConstructor
public class Hooks {

    private final Context context;
    private final RequestHandler requestHandler;
    private final CreateWorkspaceSteps createWorkspaceSteps;
    private final DeleteWorkspaceSteps deleteWorkspaceSteps;
    private final DeleteBoardSteps deleteBoardSteps;


    @After(value = "@cleanup")
    public void afterScenario(){
        requestHandler.clearAll();
        requestHandler.authenticate(Kate);
        context.getBoards().values().forEach(board-> {
            Allure.step(String.format("Kate deletes board \"%s\"", board.getName()));
            deleteBoardSteps.deleteBoard(board.getId());
        });
        context.getWorkspaces().values().forEach(workspace-> {
            Allure.step(String.format("Kate deletes workspace \"%s\"", workspace.getDisplayName()));
            deleteWorkspaceSteps.deleteWorkspace(workspace.getId());
        });
    }

    @Before(value = "@with_workspace")
    public void beforeScenario(){
        requestHandler.authenticate(Kate);
        String workspaceName = "WORKSPACE 1";
        createWorkspaceSteps.createWorkspaceSetup(workspaceName);
        Allure.step(String.format("Kate creates new workspace \"%s\"", workspaceName));
        Organization workspace = createWorkspaceSteps.createWorkspace();
        context.addWorkspace(workspaceName, workspace);
        requestHandler.clearAll();
    }
}
