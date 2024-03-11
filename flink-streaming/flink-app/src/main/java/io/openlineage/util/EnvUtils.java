package io.openlineage.util;

import static org.awaitility.Awaitility.await;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Base64;

public class EnvUtils {

  public static void waitForSchemaRegistry() {
    await()
        .atMost(Duration.ofSeconds(60))
        .pollInterval(Duration.ofSeconds(1))
        .until(() -> {
          try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://schema-registry:28081/subjects"))
                .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            return response.body().contains("io.openlineage.flink.kafka.input1-value");
          } catch (Exception e) {
           return false;
          }
        });
  }

  public static void waitForFlinkDagActive() {
    await()
        .atMost(Duration.ofSeconds(1800))
        .pollInterval(Duration.ofSeconds(3))
        .until(() -> {
          try {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header(
                    "Authorization",
                    "Basic " + Base64.getEncoder().encodeToString("airflow:airflow".getBytes())
                )
                .uri(new URI("http://airflow-webserver:8080/api/v1/dags/flink_trigger"))
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
            return response.body().contains("\"is_paused\": false");
          } catch (Exception e) {
            return false;
          }
        });
  }
}
