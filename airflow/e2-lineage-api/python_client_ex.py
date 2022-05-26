from openlineage.client.run import RunEvent, RunState, Run, Job 
from openlineage.client.client import OpenLineageClient, OpenLineageClientOptions
import os
import datetime
import uuid

client = OpenLineageClient.from_environment()

client.emit(RunEvent(
    RunState.COMPLETE,
    datetime.datetime.now().isoformat(),
    Run(runId=uuid.UUID('123456')),
    Job(namespace=os.getenv('OPENLINEAGE_NAMESPACE'), 
    name='emit_event.wait-for-me'),
    "https://github.com/OpenLineage/OpenLineage/tree/0.0.1/integration/airflow",
    [],
    []
))



