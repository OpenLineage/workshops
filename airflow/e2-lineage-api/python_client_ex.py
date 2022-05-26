from openlineage.client.run import RunEvent, RunState, Run, Job 
from openlineage.client.client import OpenLineageClient, OpenLineageClientOptions
import os
import datetime
import uuid

client = OpenLineageClient.from_environment()

runID = 'a0ccded8-dd37-11ec-9d64-0242ac120002'
print(runID)

client.emit(RunEvent(
    RunState.COMPLETE,
    datetime.datetime.now().isoformat(),
    Run(runID),
    Job(namespace='default', name='emit_event.wait-for-me'),
    "https://github.com/OpenLineage/OpenLineage/tree/0.0.1/integration/airflow",
    [],
    []
))



