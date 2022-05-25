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
public class Board {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("closed")
    private Boolean closed;
    @JsonProperty("idOrganization")
    private String idOrganization;
    @JsonProperty("prefs")
    private Prefs prefs;
    @JsonProperty("organization")
    private Organization organization;
}
