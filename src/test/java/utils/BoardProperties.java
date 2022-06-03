package utils;

import lombok.Getter;

import java.util.Properties;

@Getter
public class BoardProperties {

    private int nameMinLength;
    private int nameMaxLength;
    private int descMinLength;
    private int descMaxLength;
    private int descMiddleLength;
    private int limitPerWorkspace;
    private int boardIdLength;

    private String defaultParamPermissionLevel;
    private String defaultParamVotingPrefs;
    private String defaultParamCommentsPrefs;
    private String defaultParamInvitationsPrefs;
    private Boolean defaultParamSelfJoinPrefs;
    private String defaultPramBackgroundColor;
    private String defaultParamCardAgingPrefs;

    private String nonDefaultBackgroundColor;

    public BoardProperties(Properties properties){
        nameMinLength = Integer.parseInt(properties.getProperty("board.name.minLength"));
        nameMaxLength = Integer.parseInt(properties.getProperty("board.name.maxLength"));
        descMinLength = Integer.parseInt(properties.getProperty("board.desc.minLength"));
        descMaxLength = Integer.parseInt(properties.getProperty("board.desc.maxLength"));
        descMiddleLength = Integer.parseInt(properties.getProperty("board.desc.middleLength"));
        limitPerWorkspace = Integer.parseInt(properties.getProperty("board.limitPerWorkspace"));
        boardIdLength = Integer.parseInt(properties.getProperty("board.boardId.length"));

        defaultParamPermissionLevel = properties.getProperty("board.defaultParam.permissionLevel");
        defaultParamVotingPrefs = properties.getProperty("board.defaultParam.votingPrefs");
        defaultParamCommentsPrefs = properties.getProperty("board.defaultParam.commentsPrefs");
        defaultParamInvitationsPrefs = properties.getProperty("board.defaultParam.invitationsPrefs");
        defaultParamSelfJoinPrefs = Boolean.parseBoolean(properties.getProperty("board.defaultParam.selfJoinPrefs"));
        defaultPramBackgroundColor = properties.getProperty("board.defaultPram.backgroundColor");
        defaultParamCardAgingPrefs = properties.getProperty("board.defaultParam.cardAgingPrefs");

        nonDefaultBackgroundColor = properties.getProperty("board.nonDefault.backgroundColor");
    }
}
