package TrelloURLs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Paths {
    public static final String BASE_PATH = "https://api.trello.com/1";
    public static final String BOARDS = "/boards";
    public static final String LABELS = "/labels";
    public static final String CHECKLISTS = "/checklists";
    public static final String LISTS = "/lists";
    public static final String ORGS = "/organizations";
    public static final String MEMBERS = "/members";
    public static final String MEMBER = "/member";
    public static final String MEMBERSHIPS = "/memberships";
    public static final String TOKENS = "/tokens";
    public static final String CARDS = "/cards";
    public static final String MEMBERS_VOTED = "/membersVoted";
    public static final String COMMENTS = "/comments";
    public static final String ACTIONS = "/actions";
}
