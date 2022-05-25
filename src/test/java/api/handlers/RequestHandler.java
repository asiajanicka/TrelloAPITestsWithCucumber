package api.handlers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import propertiesReaders.UsersReader;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class RequestHandler {

    private final UsersReader usersReader;
    public RequestHandler() {
        usersReader = new UsersReader();
    }
    @Setter
    private String endpoint;
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> pathParams = new HashMap<>();

    public void addQueryParam(String key, String value) {
        queryParams.put(key, value);
    }

    public void addPathParam(String key, String value) {
        pathParams.put(key, value);
    }

    public void clearAll() {
        setEndpoint(null);
        queryParams.clear();
        pathParams.clear();
    }

    public void authenticateKate(){
        this.addQueryParam("key", usersReader.getKate().getApiKey());
        this.addQueryParam("token", usersReader.getKate().getToken());
    }

    public void authenticateTom(){
        this.addQueryParam("key", usersReader.getTom().getApiKey());
        this.addQueryParam("token", usersReader.getTom().getToken());
    }
}
