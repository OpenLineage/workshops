# Copyright 2018-2023 contributors to the OpenLineage project
# SPDX-License-Identifier: Apache-2.0

from openlineage.client import set_producer

from airflow import DAG
from airflow.operators.bash import BashOperator
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

flink_dag = DAG(
    'flink_trigger',
    schedule_interval=None,
    default_args=default_args,
    description='Triggers flink second app run.'
)

dummy = BashOperator(
    task_id='flink_trigger',
    bash_command="echo 1",
    dag=flink_dag
)

dummy
