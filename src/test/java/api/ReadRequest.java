package api;

import api.handlers.RequestHandler;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReadRequest {

    public Response read(RequestHandler requestHandler){
        return given()
                .spec(SpecBuilder.getRequestSpec(requestHandler))
                .when()
                .get(requestHandler.getEndpoint())
                .then()
                .extract()
                .response();
    }
}
