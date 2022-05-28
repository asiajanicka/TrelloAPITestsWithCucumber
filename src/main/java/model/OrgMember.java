package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown=true)
public class OrgMember {
    @JsonProperty("id")
    public String id;
    @JsonProperty("idMember")
    public String idMember;
    @JsonProperty("memberType")
    public String memberType;
    @JsonProperty("idOrganizations")
    public List<String> workspaceIds;
}
