# Installing and Exploring Marquez

Marquez is an open-source metadata service implementing the OpenLineage standard.

This session covers how to install Marquez and run it with pre-seeded data.

## Install Marquez

To clone the Marquez repository using Git use:

```
git clone git@github.com:marquezproject/marquez.git
```

or

```
git clone https://github.com/MarquezProject/marquez.git
```

## Run Marquez

Starting Docker Desktop

In the same terminal window, navigate to Marquez and run it (with `--seed` if you want sample data):

```
cd marquez
./docker/up.sh --seed
```

To verify that Marquez is running, open this address in a browser: http://localhost:3000/

To verify that sample data has been seeded, you can click on the `ns` (namespace) drop-down menu in the top right corner. If you see `food delivery` there, data has been loaded.