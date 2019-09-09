package io.github.jklingsporn.vertx.jooq.shared;

import io.vertx.core.json.JsonObject;
import org.jooq.Converter;
import org.jooq.JSONB;

/**
 * Created by jensklingsporn on 04.10.16.
 * Use this converter to convert any varchar/String column into a JsonObject.
 */
public class JsonbToJsonObjectConverter implements Converter<JSONB,JsonObject> {

    @Override
    public JsonObject from(JSONB databaseObject) {
        return databaseObject == null?null:new JsonObject(databaseObject.toString());
    }

    @Override
    public JSONB to(JsonObject userObject) {
        return userObject==null?null:JSONB.valueOf(userObject.encode());
    }

    @Override
    public Class<JSONB> fromType() {
        return JSONB.class;
    }

    @Override
    public Class<JsonObject> toType() {
        return JsonObject.class;
    }
}
