from openlineage.client.run import RunEvent, RunState, Run, Job
from openlineage.client.client import OpenLineageClient, OpenLineageClientOptions
from airflow.decorators import dag, task
from datetime import datetime, timedelta
import os

@dag(
    "python_client",
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
    template_searchpath="/usr/local/airflow/include",)
def python_client_dag():

    def get_env():
        env_vars = {}
        url = os.getenv("OPENLINEAGE_URL")
        api_key = os.getenv("OPENLINEAGE_API_KEY")
        env_vars['OPENLINEAGE_URL'] = url
        env_vars['OPENLINEAGE_API_KEY'] = api_key
        return env_vars

    @task
    def setup_client():
        env_vars = get_env()
        url = env_vars['OPENLINEAGE_URL'] 
        if not url:
            return None
        return OpenLineageClient(
            url=url,
            options=OpenLineageClientOptions(
                api_key=os.getenv("OPENLINEAGE_API_KEY", None)
            )
        )

    @task
    def get_parent_id():
        parent_id = os.getenv("OPENLINEAGE_PARENT_ID")
        return {'OPENLINEAGE_PARENT_ID': parent_id}

    @task    
    def get_namespace():
        job_namespace = os.environ.get('OPENLINEAGE_NAMESPACE')
        return {'OPENLINEAGE_NAMESPACE', job_namespace}        

    setup_client()
    get_parent_id()
    get_namespace()

python_client_dag = python_client_dag()

