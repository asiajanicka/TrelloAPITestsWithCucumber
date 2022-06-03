package api;

import api.handlers.RequestHandler;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteRequest {

    public Response delete(RequestHandler requestHandler){
        return given()
                .spec(SpecBuilder.getRequestSpec(requestHandler))
                .when()
                .delete(requestHandler.getEndpoint())
                .then()
                .extract()
                .response();
    }
}
