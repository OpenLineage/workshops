# Copyright 2018-2023 contributors to the OpenLineage project
# SPDX-License-Identifier: Apache-2.0

from openlineage.client import set_producer

from airflow import DAG
from airflow.providers.postgres.operators.postgres import PostgresOperator
from airflow.utils.dates import days_ago

set_producer("https://github.com/OpenLineage/OpenLineage/tree/0.0.1/integration/airflow")


default_args = {
    'owner': 'datascience',
    'depends_on_past': False,
    'start_date': days_ago(7),
    'email_on_failure': False,
    'email_on_retry': False,
    'email': ['datascience@example.com']
}

dag = DAG(
    'postgres',
    schedule_interval=None,
    default_args=default_args,
    description='Determines the popular day of week orders are placed.'
)

CONNECTION = "postgres_conn"

drop = PostgresOperator(
    task_id='postgres_drop_if_not_exists',
    postgres_conn_id=CONNECTION,
    sql='''
        DROP TABLE IF EXISTS sink_event_intermediate_1, sink_event_intermediate_2
    ''',
    dag=dag
)

sink_event_intermediate_1 = PostgresOperator(
    task_id='sink_event_intermediate_1',
    postgres_conn_id=CONNECTION,
    sql='''
    CREATE TABLE sink_event_intermediate_1 AS SELECT * FROM sink_event
    ''',
    dag=dag
)

sink_event_intermediate_2 = PostgresOperator(
    task_id='sink_event_intermediate_2',
    postgres_conn_id=CONNECTION,
    sql='''
    CREATE TABLE sink_event_intermediate_2 AS SELECT * FROM sink_event
    ''',
    dag=dag
)

output_table = PostgresOperator(
    task_id='output_table',
    postgres_conn_id=CONNECTION,
    sql='''
    CREATE TABLE output_table
    AS
      (
        SELECT * FROM sink_event_intermediate_1
          UNION ALL
        SELECT * FROM sink_event_intermediate_2
      )
    ''',
    dag=dag
)

drop >> [
    sink_event_intermediate_1,
    sink_event_intermediate_2,
] >> output_table
