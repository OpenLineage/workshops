# Generating and Exploring Lineage Data from Airflow DAGs

This session covers how to use OpenLineage's Airflow integration to generate and explore lineage data about DAGs.

Check your available memory:

```
docker run --rm "debian:bullseye-slim" bash -c 'numfmt --to iec $(echo $(($(getconf _PHYS_PAGES) * $(getconf PAGE_SIZE))))'
```

Verify that it's at least 4G.

On Linux:

```
echo -e "AIRFLOW_UID=$(id -u)" > .env
```

Run Airflow using `docker-compose`:

```
docker-compose up airflow-init
docker-compose up
```