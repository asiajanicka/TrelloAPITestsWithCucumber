package TrelloURLs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static TrelloURLs.Paths.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardsEndpoint {

    public static String createCard() {
        return CARDS;
    }

    public static String addMemberVoteToCard(String cardId){
        return String.format(CARDS + "/%s" + MEMBERS_VOTED, cardId);
    }

    public static String addNewCommentToCard(String cardId){
        return String.format(CARDS + "/%s" + ACTIONS + COMMENTS, cardId);
    }
}
