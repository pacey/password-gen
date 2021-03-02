[<img src="https://github.com/pacey/password-gen/actions/workflows/build_and_test.yml/badge.svg" />](https://github.com/pacey/password-gen/actions/workflows/build_and_test.yml)

# Password Generator

## Modules

### Core

The core password generator library. Could be distributed as a library in its own right.

### Web API

A simple Micronaut Java Web Application that exposes the core password generation abilities.

## Extras

The project has a docker-compose file which spins up the following:
 * [Web API](http://localhost:8080/health)
 * [Prometheus](http://localhost:9090) provisioned to scrape metrics from the Web API
 * [Grafana](http://localhost:3000) provisions with a basic dashboard of how the Web API is performing
 * [Locust](http://localhost:8089) master and worker to simulate some simple load on the Web API
