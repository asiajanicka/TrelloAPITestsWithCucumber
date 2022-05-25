package TrelloURLs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static TrelloURLs.Paths.BOARDS;

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
}
