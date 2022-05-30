package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class Card {
    @JsonProperty("id")
    private String id;
    @JsonProperty("idBoard")
    private String boardId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("idList")
    private String ListId;
    @JsonProperty("idMembersVoted")
    private List<String> idMembersVotedList;
}
