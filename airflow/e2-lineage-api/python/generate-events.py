#!/usr/bin/env python3

from openlineage.client.run import RunEvent, RunState, Run, Job, Dataset
from openlineage.client.client import OpenLineageClient
from datetime import datetime
from uuid import uuid4

# Initialize the OpenLineage client
client = OpenLineageClient.from_environment()

# Specify the producer of this lineage metadata
producer = "https://github.com/OpenLineage/workshops"

# Create some basic Dataset objects for our fictional pipeline
monthly_summary = Dataset(namespace="postgres://workshop-db:None", name="workshop.public.monthly_summary")
commissions = Dataset(namespace="postgres://workshop-db:None", name="workshop.public.commissions")
taxes = Dataset(namespace="postgres://workshop-db:None", name="workshop.public.taxes")


# Create a Job object
job = Job(namespace="workshop", name="monthly_accounting")

# Create a Run object with a unique ID
run = Run(str(uuid4()))

# Emit a START run event
client.emit(
    RunEvent(
        RunState.START,
        datetime.now().isoformat(),
        run, job, producer
    )
)

#
# This is where our application would do the actual work :)
#

# Emit a COMPLETE run event
client.emit(
    RunEvent(
        RunState.COMPLETE,
        datetime.now().isoformat(),
        run, job, producer,
        inputs=[monthly_summary],
        outputs=[commissions, taxes],
    )
)
