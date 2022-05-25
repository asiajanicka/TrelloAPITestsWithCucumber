package cucumber.context;

import lombok.Getter;
import lombok.Setter;
import model.Board;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Context {

    private Map<String, Board> boards = new HashMap<>();
    @Setter
    private String boardNameWithGivenLength;

    public void addBoard(String name, Board board) {
        boards.put(name, board);
    }

    public Board getBoard(String name) {
        return boards.get(name);}

    public String getBoardId(String name) {
        return getBoard(name).getId();
    }

}
