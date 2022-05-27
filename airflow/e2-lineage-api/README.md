# Exploring the OpenLineage Python Client

This session covers how to install, import and use OpenLineage's Python client.

In the environment:

`pip install openlineage-python`

In a Python script:

```
from openlineage.client.run import RunEvent, RunState, Run, Job 
from openlineage.client.client import OpenLineageClient, OpenLineageClientOptions
from openlineage.common.dataset import Dataset, InputDataset, OutputDataset
import os

```
curl -X POST http://localhost:5000/api/v1/lineage \
	-H 'Content-Type: application/json' \
	-d @json/startjob.json
```

```
curl -X POST http://localhost:5000/api/v1/lineage \
	-H 'Content-Type: application/json' \
	-d @json/completejob.json
```

