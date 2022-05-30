package TrelloURLs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static TrelloURLs.Paths.CARDS;
import static TrelloURLs.Paths.MEMBERS_VOTED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardsEndpoint {

    public static String createCard() {
        return CARDS;
    }

    public static String addMemberVoteToCard(String cardId){
        return String.format(CARDS + "/%s" + MEMBERS_VOTED, cardId);
    }
}
