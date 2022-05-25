from airflow import DAG
from airflow.operators.dummy_operator import DummyOperator
from airflow.providers.postgres.operators.postgres import PostgresOperator
from airflow.utils.task_group import TaskGroup
from datetime import datetime, timedelta


with DAG(
    "order_analysis",
    start_date=datetime(2022, 5, 1),
    max_active_runs=3,
    schedule_interval="@daily",
    default_args={
        "email_on_failure": False,
        "email_on_retry": False,
        "retries": 1,
        "retry_delay": timedelta(minutes=1),
    },
    catchup=False,
    template_searchpath="/usr/local/airflow/include",
) as dag:

    t0 = DummyOperator(task_id="start")

    import_orders = PostgresOperator(
        task_id="import_orders",
        postgres_conn_id="workshop",
        sql="orders.sql",
    )

    summarize_months = PostgresOperator(
        task_id="summarize_months",
        postgres_conn_id="workshop",
        sql="monthly_summary.sql",
    )

    find_popular_products = PostgresOperator(
        task_id="find_popular_products",
        postgres_conn_id="workshop",
        sql="top_products.sql",
    )

    t0 >> import_orders >> summarize_months >> find_popular_products
