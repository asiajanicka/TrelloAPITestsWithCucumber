package utils.users;

import propertiesReaders.UsersReader;

public class Utils {
    public static String getPermissionLevel(String type) {
        String permissionLevel;
        switch (type) {
            case "public": {
                permissionLevel = "public";
                break;
            }
            case "private": {
                permissionLevel = "private";
                break;
            }
            case "workspace visible": {
                permissionLevel = "org";
                break;
            }
            default:
                throw new IllegalArgumentException("Illegal value for permission level param for board");
        }
        return permissionLevel;
    }

    public static String getVotingLevel(String type) {
        String votingLevel;
        switch (type.strip()) {
            case "public users": {
                votingLevel = "public";
                break;
            }
            case "only board members": {
                votingLevel = "members";
                break;
            }
            case "workspace members": {
                votingLevel = "org";
                break;
            }
            case "disabled": {
                votingLevel = "disabled";
                break;
            }
            default:
                throw new IllegalArgumentException("Illegal value for voting prefs param for board");
        }
        return votingLevel;
    }

    public static String getCommentingLevel(String type) {
        String commentingLevel;
        switch (type.strip()) {
            case "public users": {
                commentingLevel = "public";
                break;
            }
            case "only board members": {
                commentingLevel = "members";
                break;
            }
            case "workspace members": {
                commentingLevel = "org";
                break;
            }
            case "disabled": {
                commentingLevel = "disabled";
                break;
            }
            default:
                throw new IllegalArgumentException("Illegal value for comment prefs param for board");
        }
        return commentingLevel;
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
