# Password Generator

## Modules

### Core

The core password generator library. Could be distributed as a library in its own right.

### Web API

A simple Micronaut Java Web Application that exposes the core password generation abilities.

## Extras

The project has a docker-compose file which spins up the following:
 * Web API
 * Prometheus provisioned to scrape metrics from the Web API
 * Grafana provisions with a basic dashboard of how the Web API is performing
 * Locust master and worker to simulate some simple load on the Web API
