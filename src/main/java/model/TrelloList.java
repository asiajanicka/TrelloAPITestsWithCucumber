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
public class TrelloList {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("closed")
    private boolean closed;
    @JsonProperty("pos")
    private String pos;
    @JsonProperty("softLimit")
    private String softLimit;
    @JsonProperty("idBoard")
    private String idBoard;
    @JsonProperty("subscribed")
    private boolean subscribed;
}
