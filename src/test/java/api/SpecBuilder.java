package api;

import TrelloURLs.Paths;
import api.handlers.RequestHandler;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec(RequestHandler requestHandler){
        return new RequestSpecBuilder()
                .setBaseUri(Paths.BASE_PATH)
                .setContentType(ContentType.JSON)
                .addQueryParams(requestHandler.getQueryParams())
                .addPathParams(requestHandler.getPathParams())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
