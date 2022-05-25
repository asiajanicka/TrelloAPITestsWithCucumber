package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Setter
@Getter
public class Organization {
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("displayName")
    public String displayName;
    @JsonProperty("memberships")
    public ArrayList<Member> memberships;

}
