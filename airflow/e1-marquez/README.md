# Installing and Exploring Marquez

Marquez is an open-source metadata service implementing the OpenLineage standard.

This session covers how to install Marquez and run it with pre-seeded data.

## Installing Marquez

Clone the Marquez repository using Git:

```
git clone git@github.com:marquezproject/marquez.git
```

Navigate to Marquez and run it (with `--seed` if you want sample data):

```
cd marquez
./docker/up.sh --seed
```

Verify that Marquez is running by opening this address in a browser: http://localhost:3000/