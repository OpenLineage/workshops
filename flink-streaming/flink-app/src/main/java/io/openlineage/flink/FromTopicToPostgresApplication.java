/*
/* Copyright 2018-2024 contributors to the OpenLineage project
/* SPDX-License-Identifier: Apache-2.0
*/

package io.openlineage.flink;

import static io.openlineage.flink.StreamEnvironment.setupEnv;
import static io.openlineage.kafka.SinkAndSourceClientProvider.aKafkaSource;
import static io.openlineage.kafka.SinkAndSourceClientProvider.aPsqlSink;
import static org.apache.flink.api.common.eventtime.WatermarkStrategy.noWatermarks;

import io.openlineage.flink.avro.event.InputEvent;
import io.openlineage.util.EnvUtils;
import io.openlineage.util.OpenLineageFlinkJobListenerBuilder;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

public class FromTopicToPostgresApplication {


  public static void main(String[] args) throws Exception {
    EnvUtils.waitForSchemaRegistry();
    StreamExecutionEnvironment env = setupEnv(args);

    env.fromSource(
            aKafkaSource("io.openlineage.flink.kafka.input.*"),
            noWatermarks(),
            "kafka-source")
        .uid("kafka-source")
        .keyBy(InputEvent::getId)
        .process(new StatefulCounter())
        .name("process")
        .uid("process")
        .map(outputEvent -> Row.of(outputEvent.id, outputEvent.version, outputEvent.counter))
        .addSink(aPsqlSink())
        .name("kafka-sink")
        .uid("kafka-sink");

    // OpenLineage specific code
    env.registerJobListener(
        OpenLineageFlinkJobListenerBuilder
            .create()
            .executionEnvironment(env)
            .jobName("from-topic-to-psql")
            .build()
    );

    env.execute("from-topic-to-psql");
  }
}
