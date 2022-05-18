package es.ieslvareda.server.model;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer <T> implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public T getObjet(String json, Class<T> tClass) {
        return gson.fromJson(json,tClass);
    }
}
