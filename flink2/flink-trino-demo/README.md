# Flink + Trino Lineage Demo

## Pre-requisites

Run the following command to start the demo:
```bash
docker-compose up
```

Pre-requisites:
 * Check marquez is running under http://localhost:3000/
 * Flink job manager should be available at: http://localhost:8081/#/overview
 * Jupyter notebook should be available as well. Please check docker logs for the URL with a token.

## Demo steps

### Trino table creation

1. Go to Jupyter and open "demo" notebook. 
2. Connect to trino and create a table within the trino. Don't run other queries that read from the newly created table. We'll run them later. 

### Starting Flink job

* Connect flink container via docker. 
* Make sure the current director is `/opt/flink`. 
* Run `bin/sql-client.sh`
* Create trino output table for Flink with:
```sql
     CREATE TABLE trino_output (
      user_id integer,
      ts_interval string,
      max_log_date string,
      max_log_time string,
      max_ts string,
      counter bigint,
      max_price decimal(38, 18)
    ) with (
      'connector' = 'jdbc',
      'url' = 'jdbc:trino://trino:8080',
      'username' = 'trino',
      'password' = '',
      'table-name' = 'memory.default.trino_output'
    );
```

* Create some table from kafka topic:
```sql
CREATE TABLE kafka_input (
      user_id integer,
      `computed-price` as price + 1.0,
      price decimal(38, 18),
      currency string,
      log_date date,
      log_time time(3),
      log_ts timestamp(3),
      ts as log_ts + INTERVAL '1' SECOND,
      watermark for ts as ts
    ) with (
      'connector' = 'kafka',
      'topic' = 'input-topic',
      'properties.bootstrap.servers' = 'kafka:9092',
      'properties.group.id' = 'flink-examples',
      'scan.startup.mode' = 'earliest-offset',
      'format' = 'json'
    );
```

* Fill some data:
```sql
SET 'execution.runtime-mode'='BATCH';

INSERT INTO kafka_input
SELECT user_id, CAST(price AS DECIMAL(10, 2)), currency, CAST(d AS DATE), CAST(t AS TIME(0)), CAST(ts AS TIMESTAMP(3))
FROM (VALUES (101, 2.02,'Euro','2019-12-12', '00:00:01', '2019-12-12 00:00:01.001001'),
  (102, 1.11,'US Dollar','2019-12-12', '00:00:02', '2019-12-12 00:00:02.002001'),
  (103, 50,'Yen','2019-12-12', '00:00:03', '2019-12-12 00:00:03.004001'),
  (104, 3.1,'Euro','2019-12-12', '00:00:04', '2019-12-12 00:00:04.005001'),
  (105, 5.33,'US Dollar','2019-12-12', '00:00:05', '2019-12-12 00:00:05.006001'),
  (106, 0,'DUMMY','2019-12-12', '00:00:10', '2019-12-12 00:00:10')
) AS orders (user_id, price, currency, d, t, ts);
```

* Run the streaming query:
```sql
SET 'execution.runtime-mode'='STREAMING';

    
INSERT INTO trino_output SELECT
   user_id,
   CAST(TUMBLE_END(ts, INTERVAL '5' SECOND) AS VARCHAR),
   CAST(MAX(log_date) AS VARCHAR),
   CAST(MAX(log_time) AS VARCHAR),
   CAST(MAX(ts) AS VARCHAR),
   COUNT(*),
   CAST(MAX(price) AS DECIMAL(10, 2))
 FROM kafka_input
 GROUP BY user_id, TUMBLE(ts, INTERVAL '5' SECOND);
```

We can go now back to the Jupyter notebook and run other Trino queries. 

Meanwhile, we can observe how the lineage graph evolves in Marquez UI.

In case of problems, please increase Flink SQL verbosity with:
```sql
SET 'sql-client.verbose' = 'true';
```
or check the flink job manager logs. 

Have fun!

## Known issues

 * `openlineage-flink` jar is built from the main branch unreleased version. Changes required will be released in 1.34.
 * Native lineage support for JDBC is based on https://github.com/apache/flink-connector-jdbc/pull/149 
 * Trino dataset naming does not follow the naming convertion. An issues for this https://github.com/trinodb/trino/issues/25728 has been created. The problem is mitigated by using dataset namespace resolver.