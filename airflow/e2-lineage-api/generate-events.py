#!/usr/bin/env python3

from openlineage.client.run import RunEvent, RunState, Run, Job, Dataset
from openlineage.client.client import OpenLineageClient
from datetime import datetime
from uuid import uuid4

# Initialize the OpenLineage client
client = OpenLineageClient.from_environment()

# Specify the producer of this lineage metadata
producer = 'https://github.com/OpenLineage/workshops'

# Create some basic Dataset objects for our fictional pipeline
online_orders = Dataset(namespace='workshop', name='online_orders')
mail_orders = Dataset(namespace='workshop', name='mail_orders')
orders = Dataset(namespace='workshop', name='orders')

# Generate a unique ID for this run
runID = str(uuid4())

# Emit a START run event
client.emit(RunEvent(
    RunState.START,
    datetime.now().isoformat(),
    Run(runID),
    Job(namespace='workshop', name='process_orders'),
    producer
))

#
# This is where our application would do the actual work :)
#

# Emit a COMPLETE run event
client.emit(RunEvent(
    RunState.COMPLETE,
    datetime.now().isoformat(),
    Run(runID),
    Job(namespace='workshop', name='process_orders'),
    producer,
    inputs=[online_orders, mail_orders],
    outputs=[orders]
))
