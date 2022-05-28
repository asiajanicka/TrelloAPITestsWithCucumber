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
public class Label {
    @JsonProperty("id")
    private String id;
    @JsonProperty("idBoard")
    private String idBoard;
    @JsonProperty("name")
    private String name;
    @JsonProperty("color")
    private String color;
}
