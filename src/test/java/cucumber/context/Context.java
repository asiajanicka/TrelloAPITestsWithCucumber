package cucumber.context;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Organization;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Context {

    private Map<String, Board> boards = new HashMap<>();
    private Map<String, Organization> workspaces = new HashMap<>();
    @Setter
    private String boardNameWithGivenLength;
    @Setter
    private String boardDescWithGivenLength;


    public void addBoard(String name, Board board) {
        boards.put(name, board);
    }

    public Board getBoard(String name) {
        return boards.get(name);}

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

}
