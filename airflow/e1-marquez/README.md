# Installing and Exploring Marquez

[Marquez](https://marquezproject.ai/) is an open-source metadata service implementing the [OpenLineage](https://openlineage.io/) standard.

This session covers how to install Marquez and run it with pre-seeded data.

## Installing Marquez

This repository has been set up with Marquez as a submodule.

To clone the Marquez repository with Git, use:

`git submodule update --init`

## Running Marquez

Marquez requires that Docker be running, so start the Docker Desktop application before attempting to run Marquez.

Then, in the directory where you cloned the Marquez repository, navigate to Marquez and run it (with `--seed` if you want sample data):

```
cd marquez
./docker/up.sh --seed
```

Now you can access Marquez! To do so, point a browser to http://localhost:3000/.

To view sample lineage data, select the `food delivery` namespace (in the `ns` drop-down).