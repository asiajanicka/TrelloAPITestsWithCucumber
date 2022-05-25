package api.handlers;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class ResponseHandler {
    private Response response;

    public int getStatusCode(){
        return response.getStatusCode();
    }
}
