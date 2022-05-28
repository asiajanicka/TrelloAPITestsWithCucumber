package TrelloURLs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static TrelloURLs.Paths.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardEndpoint {

    public static String getBoard(String boardId) {
        return String.format(BOARDS + "/%s", boardId);
    }

    public static String createBoard() {
        return BOARDS;
    }

    public static String deleteBoard(String boardId){
        return String.format(BOARDS + "/%s", boardId);
    }

    public static String getLabelsOnBoard(String boardId){
        return String.format(BOARDS + "/%s" + LABELS, boardId);
    }

    public static String getListsOnBoard(String boardId){
        return String.format(BOARDS + "/%s" + LISTS, boardId);
    }

    public static String addMemberToBoard(String boardId, String memberId){
        return String.format(BOARDS + "/%s" + MEMBERS + "/%s", boardId, memberId);
    }

    public static String createListOnBoard(String boardId){
        return String.format(BOARDS + "/%s" + LISTS, boardId);
    }
}
