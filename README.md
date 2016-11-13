# vertx-jooq
A [jOOQ](http://www.jooq.org/)-CodeGenerator to create [vertx](http://vertx.io/)-ified DAOs and POJOs that can convert from/to a `io.vertx.core.JsonObject`.
Currently, only Java and only the default 'callbackstyle' with `io.vertx.core.Handler<AsyncResult<T>>` is supported.
See the [VertxGeneratorTest](https://github.com/jklingsporn/vertx-jooq/blob/master/src/test/java/io/github/jklingsporn/vertx/impl/VertxGeneratorTest.java)
of how to setup the generator.

In addition to the `VertxGenerator`, there is also a generator with [Guice](https://github.com/google/guice) support. If you're using the `VertxGuiceGenerator`,
the `setConfiguration(org.jooq.Configuration)` and `setVertx(io.core.Vertx)` methods get `@javax.inject.Inject` annotations added
and a Guice `Module` gets created which binds all created VertxDAOs to their implementation.

# maven
```
<dependency>
  <groupId>io.github.jklingsporn</groupId>
  <artifactId>vertx-jooq</artifactId>
  <version>1.0.0</version>
</dependency>
```

# maven code generator configuration example for mysql
The following code-snippet can be copy-pasted into your pom.xml to generate code from your MySQL database schema.

**Watch out for placeholders beginning with 'YOUR_xyz' though! E.g. you have to define credentials for DB access and specify the target directory where jOOQ
should put the generated code into, otherwise it won't run!**

After you replaced all placeholders with valid values, you should be able to run `mvn generate-sources` which creates all POJOs and DAOs into the target directory you specified.

If you are new to jOOQ, I recommend to read the awesome [jOOQ documentation](http://www.jooq.org/doc/3.8/manual/), especially the chapter about
[code generation](http://www.jooq.org/doc/3.8/manual/code-generation/).

```
<project>
...your project configuration here...
  <build>
    <plugins>
      <plugin>
          <!-- Specify the maven code generator plugin -->
          <groupId>org.jooq</groupId>
          <artifactId>jooq-codegen-maven</artifactId>
          <version>3.8.6</version>

          <!-- The plugin should hook into the generate goal -->
          <executions>
              <execution>
                  <goals>
                      <goal>generate</goal>
                  </goals>
              </execution>
          </executions>

          <dependencies>
              <dependency>
                  <groupId>mysql</groupId>
                  <artifactId>mysql-connector-java</artifactId>
                  <version>5.1.37</version>
              </dependency>
              <dependency>
                  <groupId>io.github.jklingsporn</groupId>
                  <artifactId>vertx-jooq</artifactId>
                  <version>1.0.0</version>
              </dependency>
          </dependencies>

          <!-- Specify the plugin configuration.
               The configuration format is the same as for the standalone code generator -->
          <configuration>
              <!-- JDBC connection parameters -->
              <jdbc>
                  <driver>com.mysql.jdbc.Driver</driver>
                  <url>YOUR_JDBC_URL_HERE</url>
                  <user>YOUR_DB_USER_HERE</user>
                  <password>YOUR_DB_PASSWORD_HERE</password>
              </jdbc>

              <!-- Generator parameters -->
              <generator>
                  <name>io.github.jklingsporn.vertx.impl.VertxGuiceGenerator</name>
                  <database>
                      <name>org.jooq.util.mysql.MySQLDatabase</name>
                      <includes>.*</includes>
                      <inputSchema>YOUR_INPUT_SCHEMA</inputSchema>
                      <outputSchema>YOUR_OUTPUT_SCHEMA</outputSchema>
                      <unsignedTypes>false</unsignedTypes>
                      <forcedTypes>
                          <!-- Convert tinyint to boolean -->
                          <forcedType>
                              <name>BOOLEAN</name>
                              <types>(?i:TINYINT)</types>
                          </forcedType>
                          <!-- Convert varchar column with name 'someJsonObject' to a io.vertx.core.json.JsonObject-->
                          <forcedType>
                              <userType>io.vertx.core.json.JsonObject</userType>
                              <converter>io.github.jklingsporn.vertx.impl.JsonObjectConverter</converter>
                              <expression>someJsonObject</expression>
                              <types>.*</types>
                          </forcedType>
                          <!-- Convert varchar column with name 'someJsonArray' to a io.vertx.core.json.JsonArray-->
                          <forcedType>
                              <userType>io.vertx.core.json.JsonArray</userType>
                              <converter>io.github.jklingsporn.vertx.impl.JsonArrayConverter</converter>
                              <expression>someJsonArray</expression>
                              <types>.*</types>
                          </forcedType>
                      </forcedTypes>
                  </database>
                  <target>
                      <!-- This is where jOOQ will put your files -->
                      <packageName>YOUR_TARGET_PACKAGE_HERE</packageName>
                      <directory>YOUR_TARGET_DIRECTORY_HERE</directory>
                  </target>
                  <generate>
                      <interfaces>true</interfaces>
                      <daos>true</daos>
                      <fluentSetters>true</fluentSetters>
                  </generate>


                  <strategy>
                      <name>io.github.jklingsporn.vertx.impl.VertxGeneratorStrategy</name>
                  </strategy>
              </generator>

          </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```