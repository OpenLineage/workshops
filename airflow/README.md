# OpenLineage with Apache Airflow

[Slide Presentation](https://docs.google.com/presentation/d/1qo3ebLfxBlcJmzULzoNMpl9OZZf8fnVb5nmxXh6IJ5Q/edit?usp=sharing)

## An Introduction to Data Lineage with Airflow and Marquez

Data lineage might seem like a complicated and unapproachable topic, but that’s only because data pipelines are complicated. The core concept is straightforward: trace and record the journey of datasets as they travel through a data pipeline.

Marquez, a lineage metadata server, is a simple thing designed to watch complex things. It tracks the movement of data through complex pipelines using a straightforward, clear object model of Jobs, Datasets, and Runs. The information it gathers can be used to help you more effectively understand, communicate, and solve problems. The interactive UI allows you to see exactly where any inefficiencies have developed or datasets have become compromised.

In this workshop, you will learn how to collect and visualize lineage from a basic Airflow pipeline using Marquez. You will need to understand the basics of Airflow, but no experience with lineage is required.

The workshop contains three exercises:
* Installing Marquez
* Exploring the Lineage API
* OpenLineage and Airflow

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

To navigate to the Airflow workshop:

`cd airflow`