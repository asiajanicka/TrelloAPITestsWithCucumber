package TrelloURLs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static TrelloURLs.Paths.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkspaceEndpoint {

    public static String createWorkspace() {
        return ORGS;
    }

    public static  String deleteWorkspace(String id){
        return String.format(ORGS + "/%s", id);
    }

    public static String updateMemberOfOrg(String id, String idMember){
        return String.format(ORGS + "/%s" + MEMBERS + "/%s", id, idMember);
    }

}
