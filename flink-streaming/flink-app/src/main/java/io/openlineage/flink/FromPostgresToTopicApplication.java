/*
/* Copyright 2018-2024 contributors to the OpenLineage project
/* SPDX-License-Identifier: Apache-2.0
*/

package io.openlineage.flink;

import static io.openlineage.flink.StreamEnvironment.setupEnv;
import static io.openlineage.kafka.SinkAndSourceClientProvider.aJdbcInputFormat;
import static io.openlineage.kafka.SinkAndSourceClientProvider.aKafkaSink;

import io.openlineage.flink.avro.event.OutputEvent;
import io.openlineage.util.EnvUtils;
import io.openlineage.util.OpenLineageFlinkJobListenerBuilder;
import java.time.Duration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FromPostgresToTopicApplication {
  public static void main(String[] args) throws Exception {
    EnvUtils.waitForSchemaRegistry();
    EnvUtils.waitForFlinkDagActive();

    StreamExecutionEnvironment env = setupEnv(args);
    env
        .createInput(aJdbcInputFormat())
        .map(row -> {
          return new OutputEvent(
              (String)row.getField(0),
              (long)row.getField(1),
              (long)row.getField(2)
          );
        })
        .sinkTo(aKafkaSink("io.openlineage.flink.kafka.output"));
    // OpenLineage specific code
    env.registerJobListener(
        OpenLineageFlinkJobListenerBuilder
            .create()
            .executionEnvironment(env)
            .jobName("from-psql-to-topic")
            .build()
    );
    env.execute("from-psql-to-topic");
  }
}
