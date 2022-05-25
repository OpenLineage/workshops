```
docker run --rm "debian:bullseye-slim" bash -c 'numfmt --to iec $(echo $(($(getconf _PHYS_PAGES) * $(getconf PAGE_SIZE))))'
```

Verify that it's at least 4G.

On Linux:

```
echo -e "AIRFLOW_UID=$(id -u)" > .env
```

```
docker-compose up airflow-init
docker-compose up
```