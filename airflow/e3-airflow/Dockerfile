FROM apache/airflow:2.3.0 AS builder

USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
            gcc libc6-dev pkg-config g++ \
    && apt-get autoremove -yqq --purge \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

USER airflow
RUN pip install --no-cache-dir openlineage-airflow openlineage-sql
