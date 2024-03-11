/*
/* Copyright 2018-2024 contributors to the OpenLineage project
/* SPDX-License-Identifier: Apache-2.0
*/

package io.openlineage.kafka;

import static io.openlineage.common.config.ConfigWrapper.fromResource;

import io.openlineage.flink.avro.event.InputEvent;
import io.openlineage.flink.avro.event.OutputEvent;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.SqlTimeTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcInputFormat;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.connector.jdbc.dialect.JdbcDialect;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.KafkaSourceBuilder;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroDeserializationSchema;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.types.Row;

public class SinkAndSourceClientProvider {
  private static final String SCHEMA_REGISTRY_URL = "http://schema-registry:28081";
  public static final String OUTPUT_QUERY = "insert into public.sink_event(id, version, counter) values (?, ?, ?)";
  public static final String INPUT_QUERY = "select * from public.output_table";

  public static final JdbcStatementBuilder<Row> TEST_ENTRY_JDBC_STATEMENT_BUILDER =
      (ps, row) -> {
        if (row.getArity() == 3) {
          ps.setString(1, (String) row.getField(0));
          ps.setLong(2, (Long) row.getField(1));
          ps.setLong(3, (Long) row.getField(2));
        }
      };

  private SinkAndSourceClientProvider() {}

  public static KafkaSource<InputEvent> aKafkaSource(String... topics) {
    KafkaSourceBuilder<InputEvent> builder =
        KafkaSource.<InputEvent>builder()
            .setProperties(fromResource("kafka-consumer.conf").toProperties())
            .setBootstrapServers("kafka:9092")
            .setValueOnlyDeserializer(
                ConfluentRegistryAvroDeserializationSchema.forSpecific(
                    InputEvent.class, SCHEMA_REGISTRY_URL));

    if (topics.length == 1 && topics[0].contains(".*")) {
      // set topic pattern
      builder.setTopicPattern(Pattern.compile(topics[0]));
    } else {
      // set topics
      builder.setTopics(topics);
    }

    return builder.build();
  }

  public static KafkaSink<OutputEvent> aKafkaSink(String topic) {
    return KafkaSink.<OutputEvent>builder()
        .setKafkaProducerConfig(fromResource("kafka-producer.conf").toProperties())
        .setBootstrapServers("kafka:9092")
        .setRecordSerializer(
            KafkaRecordSerializationSchema.builder()
                .setValueSerializationSchema(
                    ConfluentRegistryAvroSerializationSchema.forSpecific(
                        OutputEvent.class, topic, SCHEMA_REGISTRY_URL))
                .setTopic(topic)
                .build())
        .build();
  }

  public static SinkFunction<Row> aPsqlSink() {
    Properties properties = fromResource("postgres.conf").toProperties();
    return  JdbcSink.sink(
        OUTPUT_QUERY,
        TEST_ENTRY_JDBC_STATEMENT_BUILDER,
        new TestJdbcConnectionOptions(
            properties.getProperty("postgres.url"),
            "public.sink_event",
            properties.getProperty("postgres.driver"),
            properties.getProperty("postgres.user"),
            properties.getProperty("postgres.password"),
            null,
            1,
            1000));
  }

  public static JdbcInputFormat aJdbcInputFormat() {
    Properties properties = fromResource("postgres.conf").toProperties();

    TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
        BasicTypeInfo.STRING_TYPE_INFO,
        BasicTypeInfo.STRING_TYPE_INFO,
        SqlTimeTypeInfo.TIMESTAMP
    };

    RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
    return new JdbcInputFormat.JdbcInputFormatBuilder()
        .setDBUrl(properties.getProperty("postgres.url"))
        .setDrivername(properties.getProperty("postgres.driver"))
        .setQuery(INPUT_QUERY)
        .setUsername(properties.getProperty("postgres.user"))
        .setPassword(properties.getProperty("postgres.password"))
        .setRowTypeInfo(rowTypeInfo)
        .finish();
  }

  private static class TestJdbcConnectionOptions extends JdbcConnectionOptions {

    private static final long serialVersionUID = 1L;

    private final String tableName;
    private final JdbcDialect dialect;
    private final @Nullable Integer parallelism;

    TestJdbcConnectionOptions(
        String dbURL,
        String tableName,
        @Nullable String driverName,
        @Nullable String username,
        @Nullable String password,
        JdbcDialect dialect,
        @Nullable Integer parallelism,
        int connectionCheckTimeoutSeconds) {
      super(dbURL, driverName, username, password, connectionCheckTimeoutSeconds);
      this.tableName = tableName;
      this.dialect = dialect;
      this.parallelism = parallelism;
    }
  }
}
