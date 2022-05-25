package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown=true)
public class Member {
    @JsonProperty("id")
    public String id;
    @JsonProperty("idMember")
    public String idMember;
    @JsonProperty("memberType")
    public String memberType;
    @JsonProperty("unconfirmed")
    public boolean unconfirmed;
    @JsonProperty("deactivated")
    public boolean deactivated;
}
