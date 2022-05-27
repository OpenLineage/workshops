# Installing and Exploring Marquez

Marquez is an open-source metadata service implementing the OpenLineage standard.

This session covers how to install Marquez and run it with pre-seeded data.

## Install Marquez

Clone the Marquez repository using Git:

```
git clone git@github.com:marquezproject/marquez.git
```

## Run Marquez

Start Docker Desktop

In the same terminal window, navigate to Marquez and run it (with `--seed` if you want sample data):

```
cd marquez
./docker/up.sh --seed
```

Verify that Marquez is running by opening this address in a browser: http://localhost:3000/

Verify that sample data has been seeded by clicking on the `ns` (namespace) drop-down menu in the top right corner. If you see `food delivery` there, data has been loaded.