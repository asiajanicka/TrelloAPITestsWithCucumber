package api;

import api.handlers.RequestHandler;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UpdateRequest {

    public Response update(RequestHandler requestHandler){
        return given()
                .spec(SpecBuilder.getRequestSpec(requestHandler))
                .when()
                .put(requestHandler.getEndpoint())
                .then()
                .extract()
                .response();
    }
}
