package io.github.jklingsporn.vertx.jooq.shared;

import io.vertx.core.json.JsonObject;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

/**
 * @author jensklingsporn
 */
public class JsonbToJsonObjectBinding implements Binding<JSONB, JsonObject> {

    private static final Converter<JSONB, JsonObject> CONVERTER = new Converter<JSONB, JsonObject>() {
        @Override
        public JsonObject from(JSONB t) {
            return t == null ? null : new JsonObject(t.toString());
        }

        @Override
        public JSONB to(JsonObject u) {
            return u == null ? null : JSONB.valueOf(u.encode());
        }

        @Override
        public Class<JSONB> fromType() {
            return JSONB.class;
        }

        @Override
        public Class<JsonObject> toType() {
            return JsonObject.class;
        }
    };

    // The converter does all the work
    @Override
    public Converter<JSONB, JsonObject> converter() {
        return CONVERTER;
    }

    // Rending a bind variable for the binding context's value and casting it to the json type
    @Override
    public void sql(BindingSQLContext<JsonObject> ctx) {
        // Depending on how you generate your SQL, you may need to explicitly distinguish
        // between jOOQ generating bind variables or inlined literals. If so, use this check:
        // ctx.render().paramType() == INLINED
        RenderContext context = ctx.render().visit(DSL.val(ctx.convert(converter()).value()));
        if (SQLDialect.POSTGRES.equals(ctx.configuration().dialect().family()))
        {
            context.sql("");
        }
    }

    // Registering VARCHAR types for JDBC CallableStatement OUT parameters
    @Override
    public void register(BindingRegisterContext<JsonObject> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    // Converting the JsonObject to a String value and setting that on a JDBC PreparedStatement
    @Override
    public void set(BindingSetStatementContext<JsonObject> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    // Getting a String value from a JDBC ResultSet and converting that to a JsonObject
    @Override
    public void get(BindingGetResultSetContext<JsonObject> ctx) throws SQLException {
        ctx.convert(converter()).value(JSONB.valueOf(ctx.resultSet().getString(ctx.index())));
    }

    // Getting a String value from a JDBC CallableStatement and converting that to a JsonObject
    @Override
    public void get(BindingGetStatementContext<JsonObject> ctx) throws SQLException {
        ctx.convert(converter()).value(JSONB.valueOf(ctx.statement().getString(ctx.index())));
    }

    // Setting a value on a JDBC SQLOutput (useful for Oracle OBJECT types)
    @Override
    public void set(BindingSetSQLOutputContext<JsonObject> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    // Getting a value from a JDBC SQLInput (useful for Oracle OBJECT types)
    @Override
    public void get(BindingGetSQLInputContext<JsonObject> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
