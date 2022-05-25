package api;

import api.handlers.RequestHandler;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateRequest {

    public Response create(RequestHandler requestHandler){
        return given()
                .spec(SpecBuilder.getRequestSpec(requestHandler))
                .when()
                .post(requestHandler.getEndpoint())
                .then()
                .extract()
                .response();
    }
}
