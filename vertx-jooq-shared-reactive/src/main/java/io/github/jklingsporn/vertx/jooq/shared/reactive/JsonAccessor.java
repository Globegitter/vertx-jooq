package io.github.jklingsporn.vertx.jooq.shared.reactive;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;

/**
 * Created by jensklingsporn on 07.08.18.
 */
public class JsonAccessor {

    private JsonAccessor(){}

    public static JsonObject getJsonObject(Row row, String field){
        return (JsonObject) getJson(row,field);
    }

    public static JsonArray getJsonArray(Row row, String field){
        return (JsonArray) getJson(row,field);
    }

    public static Object getJson(Row row, String field) {
        Object val = row.getValue(field);
        if (val instanceof JsonObject) {
            return val;
        } else if (val instanceof JsonArray) {
            return val;
        } else {
            return null;
        }
//        return row.getJson(field) == null ? JsonImpl.NULL : row.getJson(field);
//        return null;
    }

}
