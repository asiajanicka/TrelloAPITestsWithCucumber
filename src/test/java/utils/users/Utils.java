package utils.users;

import propertiesReaders.UsersReader;

public class Utils {
    public static String getPermissionLevel(String type){
        String permissionLevel;
        switch(type){
            case "public":{
                permissionLevel = "public";
                break;
            }
            case "private":{
                permissionLevel = "private";
                break;
            } case "workspace visible":{
                permissionLevel = "org";
                break;
            }
            default: throw new IllegalArgumentException("Illegal value for permission level param for board");
        }
        return permissionLevel;
    }

    public static User getUser(String name) {
        switch (name) {
            case "Kate": {
                return new UsersReader().getKate();
            }
            case "Lucy": {
                return new UsersReader().getLucy();
            }
            case "Tom": {
                return new UsersReader().getTom();
            }
            case "John": {
                return new UsersReader().getJohn();
            }
            default:
                throw new IllegalArgumentException("Name not recognized");
        }
    }
}
