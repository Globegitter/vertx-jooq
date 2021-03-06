/*
 * This file is generated by jOOQ.
 */
package generated.cf.jdbc.guice.vertx;


import generated.cf.jdbc.guice.vertx.tables.Something;
import generated.cf.jdbc.guice.vertx.tables.Somethingcomposite;
import generated.cf.jdbc.guice.vertx.tables.records.SomethingRecord;
import generated.cf.jdbc.guice.vertx.tables.records.SomethingcompositeRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>VERTX</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<SomethingRecord, Integer> IDENTITY_SOMETHING = Identities0.IDENTITY_SOMETHING;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<SomethingRecord> SYS_PK_10244 = UniqueKeys0.SYS_PK_10244;
    public static final UniqueKey<SomethingcompositeRecord> SYS_PK_10248 = UniqueKeys0.SYS_PK_10248;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<SomethingRecord, Integer> IDENTITY_SOMETHING = Internal.createIdentity(Something.SOMETHING, Something.SOMETHING.SOMEID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<SomethingRecord> SYS_PK_10244 = Internal.createUniqueKey(Something.SOMETHING, "SYS_PK_10244", Something.SOMETHING.SOMEID);
        public static final UniqueKey<SomethingcompositeRecord> SYS_PK_10248 = Internal.createUniqueKey(Somethingcomposite.SOMETHINGCOMPOSITE, "SYS_PK_10248", Somethingcomposite.SOMETHINGCOMPOSITE.SOMEID, Somethingcomposite.SOMETHINGCOMPOSITE.SOMESECONDID);
    }
}
