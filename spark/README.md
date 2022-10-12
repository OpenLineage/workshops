# OpenLineage with Jupyter and Spark

[PDF doc](./column-lineage.pdf)

## An Introduction to Data Lineage with Jupyter and Spark

Data lineage might seem like a complicated and unapproachable topic, but that’s only because data pipelines are complicated. The core concept is straightforward: trace and record the journey of datasets as they travel through a data pipeline.

Marquez, a lineage metadata server, is a simple thing designed to watch complex things. It tracks the movement of data through complex pipelines using a straightforward, clear object model of Jobs, Datasets, and Runs. The information it gathers can be used to help you more effectively understand, communicate, and solve problems. The interactive UI allows you to see exactly where any inefficiencies have developed or datasets have become compromised.

In this workshop, you will learn how to collect and visualize lineage from a simple Spark dataset generated in Jupyter  notebook with Marquez. You will need to understand the basics of Jupyter and Spark, but no experience with lineage is required.

The workshop contains the following:
* Installing Marquez and Jupyter
* Installing and running column level lineage Jupyter notebook

## Prerequisites

* Docker 17.05+
* Docker Compose 1.29.1+
* Git (preinstalled on most versions of MacOS; verify with `git version`)
* 4 GB of available memory (the minimum for Docker — more is strongly recommended)

## Installation

### Docker

To install the version of Docker for your OS, go to https://docs.docker.com/engine/install/.
You can verify installation with `docker version` and `docker-compose -v`.

### Git

You can verify installation of Git with `git version`. 
To install Git on Linux: `sudo apt install git-all` (Ubuntu) or `sudo dnf install git-all` (Fedora).

### Workshop Files

To clone the workshop repository:

`git clone git@github.com:OpenLineage/workshops.git` (using SSH)

or

`git clone https://github.com/OpenLineage/workshops.git` (using HTTPS)

To navigate to the Spark workshop:

`cd spark`

Please locate the jupyter notebook file `column-lineage-oct-2022.ipynb` which will be used to perform the spark workshop.

### Marquez and Jupyter

Please refer to the [Quickstart with Jupyter](https://openlineage.io/docs/integrations/spark/quickstart_local/) in OpenLineage documentation to setup and run docker compose of:

* Marquez
* Jupyter

### Installing the Jupyter notebook

When the docker compose is up and running, login to Jupyter notebook using your browser, as mentioned [here](https://openlineage.io/docs/integrations/spark/quickstart_local). Then, in the Jupyter notebook's `openlinage` folder, upload the file `column-lineage-oct-2022.ipnb` by using upload button or simply dragging and dropping the file into the folder.

Open the notebook file once it's uploaded, and follow the instructions.
