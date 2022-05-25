package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class Prefs {
    @JsonProperty("permissionLevel")
    private String permissionLevel;
    @JsonProperty("voting")
    private String voting;
    @JsonProperty("comments")
    private String comments;
    @JsonProperty("invitations")
    private String invitations;
    @JsonProperty("selfJoin")
    private Boolean selfJoin;
    @JsonProperty("cardAging")
    private String cardAging;
    @JsonProperty("background")
    private String background;
    @JsonProperty("backgroundColor")
    private String backgroundColor;
}
