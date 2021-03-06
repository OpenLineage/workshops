# Exploring the Lineage API

This session covers how to interact with the OpenLineage API, both using `curl` and the [Python client library](https://pypi.org/project/openlineage-python/).

## Curl

Once Marquez is running on localhost, these two commands will start and complete a sample job run.

Before running them, make sure you have navigated to the `airflow/e2-lineage-api` directory in the `workshops` repository.

```
curl -X POST http://localhost:5000/api/v1/lineage \
	-H 'Content-Type: application/json' \
	-d @curl/startjob.json
```

```
curl -X POST http://localhost:5000/api/v1/lineage \
	-H 'Content-Type: application/json' \
	-d @curl/completejob.json
```

## Python

First, change into the `python` directory:

```
cd python
```

Then, create a Python virtual environment:

```
python3 -m venv .venv
source .venv/bin/activate
```

Then, install the `openlineage-python` package:

```
pip install -r requirements.txt
```

Then, run the Python script:

```
./generate-events.py
```
