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
public class Action {
    @JsonProperty("id")
    private String id;
    @JsonProperty("idMemberCreator")
    private String idMemberCreator;
    @JsonProperty("type")
    private String type;
}
