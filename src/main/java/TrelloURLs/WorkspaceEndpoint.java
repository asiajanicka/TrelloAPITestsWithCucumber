package TrelloURLs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static TrelloURLs.Paths.BOARDS;
import static TrelloURLs.Paths.ORGS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkspaceEndpoint {

    public static String createWorkspace() {
        return ORGS;
    }

    public static  String deleteWorkspace(String workspaceId){
        return String.format(ORGS + "/%s", workspaceId);
    }

}
