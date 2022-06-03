package cucumber.context;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Card;
import model.Organization;
import model.TrelloList;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Context {

    private Map<String, Board> boards = new HashMap<>();
    private Map<String, Organization> workspaces = new HashMap<>();
    private Map<String, TrelloList> trelloLists = new HashMap<>();
    private Map<String, Card> cards = new HashMap<>();
    @Setter
    private String boardNameWithGivenLength;
    @Setter
    private String boardDescWithGivenLength;


    public void addBoard(String name, Board board) {
        boards.put(name, board);
    }

    public Board getBoard(String name) {
        return boards.get(name);
    }

    public String getBoardId(String name) {
        return getBoard(name).getId();
    }

    public void addWorkspace(String name, Organization workspace) {
        workspaces.put(name, workspace);
    }

    public Organization getWorkspace(String name) {
        return workspaces.get(name);}

    public String getWorkspaceId(String name) {
        return getWorkspace(name).getId();
    }

    public void addTrelloList(String name, TrelloList trelloList) {
        trelloLists.put(name, trelloList);
    }

    public TrelloList getTrelloList(String name) {
        return trelloLists.get(name);}

    public String getTrelloListId(String name) {
        return getTrelloList(name).getId();
    }

    public void addCard(String name, Card card) {
        cards.put(name, card);
    }

    public Card getCard(String name) {
        return cards.get(name);}

    public String getCardId(String name) {
        return getCard(name).getId();
    }
}
