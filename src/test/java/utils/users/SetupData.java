package utils.users;

import groovy.lang.Singleton;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class SetupData {
    @Getter
    private static Map<String,String> workspaces = new HashMap<>();

    public static  void addWorkspace(String name, String workspaceId){
        workspaces.put(name,workspaceId);
    }
    public static String getWorkspaceId(String name){
        return workspaces.get(name);
    }
}
